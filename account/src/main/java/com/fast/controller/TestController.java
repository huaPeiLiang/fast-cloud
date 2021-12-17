package com.fast.controller;

import com.fast.service.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private TestServiceImpl testService;

    @PostMapping("/throw-fast-exception")
    public void throwFastException(){
        testService.throwFastException();
    }

}
