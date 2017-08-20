package com.example.wkjee.fienesslive.customer.service;

import com.alibaba.fastjson.JSON;
import com.example.wkjee.fienesslive.customer.dao.ICustomerDao;
import com.example.wkjee.fienesslive.manager.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by wkj_pc on 2017/6/16.
 */
@Service
public class CustomerLoginService {
    @Autowired
    private ICustomerDao customerDao;

    public String qqLogin(User loginUser,HttpServletRequest request) {
        /* user.setAccount(openid); user.setToken("qq:"+token);user.setNickname
        user.setGender user.setAmatar*/
        if (!customerDao.checkUserExist(loginUser.getAccount())){
            customerDao.registerQQUser(loginUser);
        }
        verifyUser(loginUser,request);
        User user = customerDao.getUserInfoByAccount(loginUser.getAccount());
        return JSON.toJSONString(user);
    }
    public String  customerLogin(User loginUser,HttpServletRequest request) {
        if ((!customerDao.checkUserExist(loginUser.getAccount()))){
            return "none";
        }
        if(!customerDao.getUserByAccountAndPwd(loginUser)){
            return "error";
        }
        verifyUser(loginUser,request);
        User user = customerDao.getUserInfoByAccount(loginUser.getAccount());
        return JSON.toJSONString(user);
    }
    public String weiboLogin(User loginUser, HttpServletRequest request) {
        if (!customerDao.checkUserExist(loginUser.getAccount())){
            customerDao.registerWeiboUser(loginUser);
        }
        User user = customerDao.getUserInfoByAccount(loginUser.getAccount());
        return JSON.toJSONString(user);
    }
    public boolean wechatLogin(User loginUser,HttpServletRequest request) {
        return false;
    }


    public void  verifyUser(User loginUser,HttpServletRequest request){
        //现将之前的登录销毁
        Map<User,HttpSession> userMap= (Map<User, HttpSession>) request.
                getServletContext().getAttribute("userMap");
        if (userMap.containsKey(loginUser)){
            HttpSession httpSession = userMap.get(loginUser);
            if (null!=httpSession.getAttribute("loginUser"))
            {
                httpSession.invalidate();
            }
        }
        request.getSession().setAttribute("loginUser",loginUser);
        userMap.put(loginUser,request.getSession());
    }
    public void quitLogin(User loginUser, HttpServletRequest request) {
        Map<User,HttpSession> userMap = (Map<User, HttpSession>) request.getServletContext()
                .getAttribute("userMap");
        if (userMap.containsKey(loginUser)){
            HttpSession httpSession = userMap.get(loginUser);
            httpSession.invalidate();
            userMap.remove(loginUser);
        }
    }

    public User getUserInfoByAccount(String account) {
        return customerDao.getUserInfoByAccount(account);
    }
}
