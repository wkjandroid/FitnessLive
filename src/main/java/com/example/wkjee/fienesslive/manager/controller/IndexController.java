package com.example.wkjee.fienesslive.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wkj_pc on 2017/5/20.
 */
@Controller
@RequestMapping("/manager")
public class IndexController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
