package com.example.wkjee.fienesslive.manager.service;


import com.example.wkjee.fienesslive.manager.dao.IUserDao;
import com.example.wkjee.fienesslive.manager.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wkj_pc on 2017/5/20.
 */
@Service
public class LoginService {

    private Map<String, Object> verifyMap=null;
    private Map<String, Object> loginMap=null;
    @Resource
    private IUserDao userDao;

    public Map<String, Object> checkLogin(HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginMap){
            loginMap=new HashMap<>();
        }
        if (null == loginUser){
            loginMap.put("result","0");
            return loginMap;
        }else{
            loginMap.put("result","1");
            return loginMap;
        }
    }

    public Map<String, Object> doLogin(HttpServletRequest request,User loginUser) {
        if (null == loginMap){
            loginMap=new HashMap<>();
        }
        if (null==loginUser.getAccount()){
            loginMap.put("result","账户不能为空！");
            return loginMap;
        }else if (null==loginUser.getPassword()){
            loginMap.put("result","密码不能为空！");
            return loginMap;
        }else{
            //shujukuyanzheng
            if (null!=userDao.queryUserByAccountAndPassword(
                    loginUser.getAccount(),loginUser.getPassword())){
                //现将之前的登录销毁
                request.getSession().invalidate();
                Map<User,HttpSession> userMap= (Map<User, HttpSession>) request.
                        getServletContext().getAttribute("userMap");
                if (userMap.containsKey(loginUser)){
                    HttpSession httpSession = userMap.get(loginUser);
                    httpSession.invalidate();
                }
                request.getSession().setAttribute("loginUser",loginUser);
                loginMap.put("result","1");
            }else {
                loginMap.put("result",2);//输入不正确
            }
            return loginMap;
        }
    }
    public Map<String, Object> toVerifyCode(HttpServletRequest request) {
        String toVerifyCode = request.getParameter("verifyCode");
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        if (null!=toVerifyCode && null!=verifyCode && verifyCode.equalsIgnoreCase(toVerifyCode)) {
            if (null==verifyMap){
                verifyMap = new HashMap<>();
            }else if (!verifyMap.isEmpty()){
                verifyMap.clear();
            }
            verifyMap.put("result","1");
        }
        //System.out.println(verifyCode+"---"+toVerifyCode+verifyCode.equalsIgnoreCase(toVerifyCode));
        return verifyMap;
    }
}
