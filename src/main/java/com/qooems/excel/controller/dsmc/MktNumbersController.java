package com.qooems.excel.controller.dsmc;

import cn.hutool.json.JSONUtil;
import com.qooems.excel.model.dsmc.MktNumbers;
import com.qooems.excel.service.dsmc.MktNumbersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("mktNumbers")
@RestController
@Slf4j
public class MktNumbersController {

    @Autowired
    MktNumbersService mktNumbersService;


    @GetMapping("list")
    public String list(Integer pageNum,Integer pageSize){
        log.info("开始查询MktNumbers列表");
        MktNumbers mktNumbers = new MktNumbers();
//        mktNumbers.setAccNbr("19978623825");
//        mktNumbers.setOperrtype("20210421");
        List<MktNumbers> list = mktNumbersService.list(mktNumbers,pageNum,pageSize);
        String listStr = JSONUtil.toJsonStr(list);
        log.info("查询MktNumbers列表得到结果【{}】", listStr);
        return listStr;
    }
}
