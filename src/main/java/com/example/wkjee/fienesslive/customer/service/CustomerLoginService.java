package com.example.wkjee.fienesslive.customer.service;

import com.alibaba.fastjson.JSON;
import com.example.wkjee.fienesslive.customer.dao.ICustomerDao;
import com.example.wkjee.fienesslive.manager.domain.User;
import com.example.wkjee.fienesslive.tools.Base64DecoderTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * Created by wkj_pc on 2017/6/16.
 */
@Service
public class CustomerLoginService {
    @Autowired
    private ICustomerDao customerDao;
    @Autowired
    private Environment env;

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

        if ((!customerDao.checkUserExistByMobileNum(loginUser.getPhonenum()))){
            return "none";
        }
        if(!customerDao.getUserByMobileAndPwd(loginUser)){
            return "error";
        }
        verifyUser(loginUser,request);
        User user = customerDao.getUserInfoByMobile(loginUser.getPhonenum());
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

    public String updateUserPassword(String mobilenum, String password) {
        return customerDao.updateUserPassword(mobilenum,password);
    }

    public String registerUser(String mobilenum, String password) {
        if (customerDao.checkUserExistByMobileNum(mobilenum)){
            return ":false";
        }
        return customerDao.registerUser(mobilenum,password);
    }

    public String updateUserAmatar(String account,String content) {
        String amatarUrl=env.getProperty("update_user_amatart_url");
        //localhost:8080/fitnesslive/   get_img_url
        try {
            File amatarfile = new File(amatarUrl+"/amatar/logo");
            if (!amatarfile.exists()) amatarfile.mkdirs();
            File amatarWritefile=new File(amatarUrl+"/amatar/logo/"+account+".jpg");
            byte[] bytes = Base64DecoderTools.stringToBytes(content);
            FileOutputStream outputStream = new FileOutputStream(amatarWritefile);
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
            outputStream.close();
            String getImageUrl=env.getProperty("get_img_url")+"/amatar/logo/"+account+".jpg";
            boolean b = customerDao.updateUserAmatarByAccount(account, getImageUrl);
            return (b)?"true:"+getImageUrl:"updatefailed";
        }catch (Exception e){
            e.printStackTrace();
            return "updatefailed";
        }
    }

    public String  updateUserSexByAccount(String account, String content) {
        return (customerDao.updateUserSexByAccount(account,content))?"true:":"failed";
    }

    public String updateUserNicknameByAccount(String account, String content) {
       return (customerDao.updateUserNicknameByAccount(account,content))?"true:":"failed";
    }
    public String updateUserPersonalSignByAccount(String account, String content) {
        return (customerDao.updateUserPersonalSignByAccount(account,content))?"true:":"failed";
    }
}
