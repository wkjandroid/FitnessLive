package com.example.wkjee.fienesslive.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.wkjee.fienesslive.customer.service.CustomerLiveChattingService;
import com.example.wkjee.fienesslive.manager.domain.LiveTheme;
import com.example.wkjee.fienesslive.manager.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wkj_pc on 2017/8/22.
 */
@Controller
@RequestMapping("/customer/login")
public class CustomerLiveController {

    @Autowired
    CustomerLiveChattingService liveService;

    /**
     * 获取全部直播用户的信息，并返回
     */
    @RequestMapping(value = "/getHomeLiveUserInfos")
    @ResponseBody
    public String getHomeLiveUserInfos() {
        List<User> liveUsers = liveService.getAllLiveUserInfo();
        return JSON.toJSONString(liveUsers);
    }

    /**
     * 获取全部直播用户的直播风格，并返回
     */
    @RequestMapping(value = "/getHomeUserLiveThemes")
    @ResponseBody
    public String getHomeUserLiveThemes() {
        List<LiveTheme> liveThemes = liveService.getUserLiveThemes();
        return JSON.toJSONString(liveThemes);
    }

    /**
     * 添加直播用户的直播风格
     */
    @RequestMapping(value = "/addLiveUserStyle")
    @ResponseBody
    public String addLiveUserStyle(@RequestParam(value = "uid", defaultValue = "") String uid,
                                   @RequestParam(value = "livethemes", defaultValue = "") String livethemes) {
        List<LiveTheme> liveThemes = JSON.parseObject(livethemes, new TypeReference<List<LiveTheme>>(){});
        return liveService.addLiveUserStyle(Integer.parseInt(uid),liveThemes);
    }
}