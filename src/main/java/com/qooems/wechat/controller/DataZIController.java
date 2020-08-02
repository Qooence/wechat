package com.qooems.wechat.controller;

import com.qooems.wechat.service.DataZIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dataZI")
public class DataZIController {

    @Autowired
    DataZIService dataZIService;

    @GetMapping("init")
    public void readDataZX(){
        dataZIService.init();
    }
}
