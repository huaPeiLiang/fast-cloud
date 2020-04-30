package com.fast.api;

import com.fast.configuration.FeignRequest;
import com.fast.configuration.StashErrorDecoder;
import com.fast.model.response.PhoneAvgPriceByBrandResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ELASTICSEARCH",configuration = {StashErrorDecoder.class, FeignRequest.class})
@Component
public interface ElasticsearchApi {

    //-----------------------------------------------------------------------------------------------------------------
    // ElasticsearchController
    //-----------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/elasticsearch/avg/price-by-brand")
    List<PhoneAvgPriceByBrandResponse> avgPriceByBrand();

}
