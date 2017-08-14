package com.example.wkjee.fienesslive.customer.controller;

import com.alibaba.fastjson.JSON;
import com.example.wkjee.fienesslive.customer.service.CustomerLoginService;
import com.example.wkjee.fienesslive.manager.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wkj_pc on 2017/6/10.
 */
@Controller
@RequestMapping("/customer/login")
public class CustomerLoginController {
    @Autowired
    private CustomerLoginService customerLoginService;

    @ResponseBody
    @RequestMapping(value = "/toLogin",method = RequestMethod.POST)
    public String login(@RequestParam(value = "user", defaultValue = "")String user , HttpServletRequest request){
        String  result="";
        User loginUser = JSON.parseObject(user, User.class);
        if (loginUser.getToken().substring(0,3).contains("qq:")){
           result =String.valueOf(customerLoginService.qqLogin(loginUser,request));
        }else if (loginUser.getToken().substring(0,3).contains("wx:")){
           result=String.valueOf(customerLoginService.wechatLogin(loginUser,request));
        }else if (loginUser.getToken().substring(0,3).contains("wb:")){
            result=String.valueOf(customerLoginService.weiboLogin(loginUser,request));
        }else {
            result=customerLoginService.customerLogin(loginUser,request);
        }
        return result;
    }

    @RequestMapping(value = "/quitLogin")
    @ResponseBody
    public String quitLogin(@RequestParam String user, HttpServletRequest request){
        System.out.println("你开始退出了！");
        User loginUser = JSON.parseObject(user, User.class);
        if (null==loginUser)
            return "";
        customerLoginService.quitLogin(loginUser,request);
        return "";
    }
}
