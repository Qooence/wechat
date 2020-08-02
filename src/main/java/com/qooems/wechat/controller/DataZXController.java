package com.qooems.wechat.controller;

import com.qooems.wechat.service.DataZXService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dataZX")
@Slf4j
public class DataZXController {

    @Autowired
    DataZXService dataZXService;

    @GetMapping("init")
    public void readDataZX(){
        dataZXService.init();
    }
}
