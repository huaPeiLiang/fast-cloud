package com.fast.controller;

import com.fast.model.response.PhoneAvgPriceByBrandResponse;
import com.fast.service.ElasticsearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/elasticsearch")
public class ElasticsearchController {

    @Autowired
    private ElasticsearchServiceImpl elasticsearchService;

    @GetMapping(value = "/avg/price-by-brand")
    public List<PhoneAvgPriceByBrandResponse> avgPriceByBrand(){
        return elasticsearchService.avgPriceByBrand();
    }

}
