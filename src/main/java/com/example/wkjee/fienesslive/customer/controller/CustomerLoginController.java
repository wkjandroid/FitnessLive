package com.example.wkjee.fienesslive.customer.controller;

import com.alibaba.fastjson.JSON;
import com.example.wkjee.fienesslive.customer.dao.ICustomerDao;
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
           result =customerLoginService.qqLogin(loginUser,request);
        }else if (loginUser.getToken().substring(0,3).contains("wx:")){
           result=String.valueOf(customerLoginService.wechatLogin(loginUser,request));
        }else if (loginUser.getToken().substring(0,3).contains("wb:")){
            result=customerLoginService.weiboLogin(loginUser,request);
        }else {
            result=customerLoginService.customerLogin(loginUser,request);
        }
        return result;
    }
    /** 获取用户信息 */
    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public String getUserInfo(@RequestParam(value = "account",defaultValue = "")String account){
        User loginUser = customerLoginService.getUserInfoByAccount(account);
        return JSON.toJSONString(loginUser);
    }
    @RequestMapping(value = "/quitLogin")
    @ResponseBody
    public String quitLogin(@RequestParam(value = "user", defaultValue = "") String user, HttpServletRequest request){
        System.out.println("你开始退出了！");
        User loginUser = JSON.parseObject(user, User.class);
        if (null==loginUser)
            return "";
        customerLoginService.quitLogin(loginUser,request);
        return "";
    }
    /** 返回客户端请求的验证码 */
    @RequestMapping(value = "/getVerifycode")
    @ResponseBody
    public String getVerifycode(){
        return "true:1234";
    }
    /** 修改用户密码*/
    @ResponseBody
    @RequestMapping(value = "/updateUserPassword")
    public String updateUserPassword(@RequestParam(value = "mobilenum",defaultValue = "")String mobilenum ,
                                     @RequestParam(value = "password",defaultValue = "")String password ){
        return customerLoginService.updateUserPassword(mobilenum,password);
    }
    @RequestMapping(value = "/registerUser")
    @ResponseBody
    public String registerUser(@RequestParam(value = "mobilenum",defaultValue = "")String mobilenum ,
                               @RequestParam(value = "password",defaultValue = "")String password ){
        return customerLoginService.registerUser(mobilenum,password);
    }
}
