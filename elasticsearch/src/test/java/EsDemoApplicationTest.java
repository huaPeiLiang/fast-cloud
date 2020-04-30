import com.fast.ElasticsearchApplication;
import com.fast.model.root.Phone;
import com.fast.repository.PhoneRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticsearchApplication.class)
public class EsDemoApplicationTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private PhoneRepository phoneRepository;

    /**
     * 创建索引，会根据Phone类的@Document注解信息来创建
     */
    @Test
    public void createIndex() {

        // 不忽略@Field设置
        elasticsearchTemplate.putMapping(Phone.class);

        // 会忽略@Field设置
//        elasticsearchTemplate.createIndex(Phone.class);
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex() {
        elasticsearchTemplate.deleteIndex(Phone.class);
    }

    /**
     * 新增方法
     */
    @Test
    public void insert() {
        Phone phone = new Phone(1L, "小米手机7", "手机",
                "小米", 3499.00, "http://xxxxxxx.jpg");
        phoneRepository.save(phone);
    }

    /**
     * 批量新增方法
     */
    @Test
    public void insertList() {
        List<Phone> list = new ArrayList<>();
        list.add(new Phone(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://xxxxxxx.jpg"));
        list.add(new Phone(3L, "华为META10", "手机", "华为", 4499.00, "http://xxxxxxx.jpg"));
        list.add(new Phone(4L, "苹果8", "手机", "苹果", 3000.00, "http://xxxxxxx.jpg"));
        list.add(new Phone(5L, "苹果XS", "手机", "苹果", 8999.00, "http://xxxxxxx.jpg"));
        list.add(new Phone(6L, "华为META11", "手机", "华为", 9999.00, "http://xxxxxxx.jpg"));
        list.add(new Phone(7L, "OPPO R11", "手机", "OPPO", 3599.00, "http://xxxxxxx.jpg"));
        list.add(new Phone(8L, "华为META9", "手机", "华为", 3999.00, "http://xxxxxxx.jpg"));
        list.add(new Phone(9L, "华为META8", "手机", "华为", 3599.00, "http://xxxxxxx.jpg"));
        list.add(new Phone(10L, "华为META7", "手机", "华为", 3099.00, "http://xxxxxxx.jpg"));
        list.add(new Phone(11L, "华为META6", "手机", "华为", 2399.00, "http://xxxxxxx.jpg"));
        list.add(new Phone(12L, "华为META5", "手机", "华为", 1599.00, "http://xxxxxxx.jpg"));
        phoneRepository.saveAll(list);
    }

    /**
     * 查询方法,对价格升序
     */
    @Test
    public void testQueryAll(){
        Iterable<Phone> list = this.phoneRepository.findAll(Sort.by("price").ascending());
        for (Phone phone:list){
            System.out.println(phone);
        }
    }

    /**
     * matchQuery底层采用的是词条匹配查询
     */
    @Test
    public void testMatchQuery(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        /*
        基本分词查询（matchQuery:关键字命中   matchPhraseQuery：精准命中）
        如果映射为keyword类型，matchQuery搜索时无法分词命中
         */
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "手机"));
//        queryBuilder.withQuery(QueryBuilders.matchPhraseQuery("brand", "小米"));

        // 过滤
//        queryBuilder.withFilter(QueryBuilders.rangeQuery("price").gt(3500));

        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));

        Page<Phone> phones = this.phoneRepository.search(queryBuilder.build());
        long total = phones.getTotalElements();
        System.out.println("total = " + total);
        for (Phone phone : phones) {
            System.out.println(phone);
        }
    }

    @Test
    public void test(){
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchPhraseQuery("category", "手机"))
                .filter(QueryBuilders.matchPhraseQuery("brand", "为"));

        TermsAggregationBuilder brand = AggregationBuilders.terms("brands").field("brand");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("elasticsearch").withTypes("phone")
                .withQuery(queryBuilder)
                .addAggregation(brand)
                .build();

        List<Phone> phones = elasticsearchTemplate.queryForList(searchQuery, Phone.class);
    }

    /**
     * 聚合
     * （1）统计某个字段的数量
     *   ValueCountBuilder vcb=  AggregationBuilders.count("count_uid").field("uid");
     * （2）去重统计某个字段的数量（有少量误差）
     *  CardinalityBuilder cb= AggregationBuilders.cardinality("distinct_count_uid").field("uid");
     * （3）聚合过滤
     * FilterAggregationBuilder fab= AggregationBuilders.filter("uid_filter").filter(QueryBuilders.queryStringQuery("uid:001"));
     * （4）按某个字段分组
     * TermsBuilder tb=  AggregationBuilders.terms("group_name").field("name");
     * （5）求和
     * SumBuilder  sumBuilder=	AggregationBuilders.sum("sum_price").field("price");
     * （6）求平均
     * AvgBuilder ab= AggregationBuilders.avg("avg_price").field("price");
     * （7）求最大值
     * MaxBuilder mb= AggregationBuilders.max("max_price").field("price");
     * （8）求最小值
     * MinBuilder min=	AggregationBuilders.min("min_price").field("price");
     * （9）按日期间隔分组
     * DateHistogramBuilder dhb= AggregationBuilders.dateHistogram("dh").field("date");
     * （10）获取聚合里面的结果
     * TopHitsBuilder thb=  AggregationBuilders.topHits("top_result");
     * （11）嵌套的聚合
     * NestedBuilder nb= AggregationBuilders.nested("negsted_path").path("quests");
     * （12）反转嵌套
     * AggregationBuilders.reverseNested("res_negsted").path("kps ");
     */
    /**
     * 如果遇到如下“Set fielddata=true on [brand]”,请手动调用下面这个接口
     * method:post  http://127.0.0.1:9200/elasticsearch/_mapping/phone/?pretty
     * {
     * 	"phone":{
     * 		"properties":{
     * 			"brand":{
     * 				"type":"text",
     * 				"fielddata":true
     *          }
     *       }
     *    }
     * }
     * */
    @Test
    public void testAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(AggregationBuilders.terms("brand").field("brand"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Phone> aggPage = (AggregatedPage<Phone>) this.phoneRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brand");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }
    }

    /**
     * boolQuery
     *
     * must
     * 文档必须满足must子句的条件，并且参与计算分值
     * filter
     * 文档必须满足filter子句的条件。不会参与计算分值
     * should
     * 文档可能满足should子句的条件。在一个Bool查询中，如果没有must或者filter，有一个或者多个should子句，那么只要满足一个就可以返回。minimum_should_match参数定义了至少满足几个子句。
     * must_not
     * 文档必须不满足must_not定义的条件。
     * */
    @Test
    public void testTemplateQuery(){
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
        for(StringTerms.Bucket bucket : buckets) {
            // 获取聚合结果
            String key = bucket.getKeyAsString();
            System.out.println(key);
            long docCount = bucket.getDocCount();
            System.out.println(docCount);

            // 获取子聚合结果
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }
    }

    /**
     * @Description:嵌套聚合，求平均值
     */
    @Test
    public void testSubAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Phone> aggPage = (AggregatedPage<Phone>) this.phoneRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");
            // 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }
    }



}
