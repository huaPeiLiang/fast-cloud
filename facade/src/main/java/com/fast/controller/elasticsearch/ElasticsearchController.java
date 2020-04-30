package com.fast.controller.elasticsearch;

import com.fast.api.ElasticsearchApi;
import com.fast.model.ReturnData;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/elasticsearch")
public class ElasticsearchController {

    @Autowired
    private ElasticsearchApi elasticsearchApi;

    @GetMapping(value = "/avg/price-by-brand")
    public ReturnData avgPriceByBrand(){
        try{
            return ReturnData.success(elasticsearchApi.avgPriceByBrand());
        }catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

}
