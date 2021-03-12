package com.fast.controller;

import com.fast.service.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private TestServiceImpl testService;

    @RequestMapping(value = "/ribbon-test")
    public String ribbonTest(){
        return testService.ribbonTest();
    }

    @RequestMapping("/get-dynamic-configuration-name")
    public String getDynamicConfigurationName(){
        return testService.getDynamicConfigurationName();
    }

}
