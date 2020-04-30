package com.fast.service;

import com.fast.model.response.PhoneAvgPriceByBrandResponse;
import com.fast.repository.PhoneRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticsearchServiceImpl {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private PhoneRepository phoneRepository;

    public List<PhoneAvgPriceByBrandResponse> avgPriceByBrand(){
        List<PhoneAvgPriceByBrandResponse> phoneAvgPriceByBrandResponses = new ArrayList<>();
        TermsAggregationBuilder brand = AggregationBuilders.terms("brands").field("brand").subAggregation(
                AggregationBuilders.avg("priceAvg").field("price")
        );

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("elasticsearch").withTypes("phone")
                .addAggregation(brand)
                .build();

        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse searchResponse) {
                return searchResponse.getAggregations();
            }
        });

        StringTerms teamAgg = (StringTerms) aggregations.asMap().get("brands");
        List<StringTerms.Bucket> buckets = teamAgg.getBuckets();
        buckets.forEach(bucket ->{
            // 获取聚合结果
            PhoneAvgPriceByBrandResponse phoneAvgPriceByBrandResponse = new PhoneAvgPriceByBrandResponse();
            phoneAvgPriceByBrandResponse.setBrand(bucket.getKeyAsString());
            phoneAvgPriceByBrandResponse.setNumber(bucket.getDocCount());
            // 获取子聚合结果
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            phoneAvgPriceByBrandResponse.setAvgPrice(avg.getValue());
            phoneAvgPriceByBrandResponses.add(phoneAvgPriceByBrandResponse);
        });
        return phoneAvgPriceByBrandResponses;
    }

}
