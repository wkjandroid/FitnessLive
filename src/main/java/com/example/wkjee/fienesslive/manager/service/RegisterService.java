package com.example.wkjee.fienesslive.manager.service;

import com.example.wkjee.fienesslive.manager.dao.IUserDao;
import com.example.wkjee.fienesslive.manager.domain.User;
import com.example.wkjee.fienesslive.tools.MessageSenderUtils;
import com.example.wkjee.fienesslive.tools.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wkj_pc on 2017/6/6.
 */
@Service
public class RegisterService {
    @Autowired
    private IUserDao userDao;
    private Map<String,Object> resultMap=new HashMap<>();
    public Map<String, Object> doRegister(HttpServletRequest request) {
        User user=new User();
        user.setAccount(request.getParameter("account"));
        user.setPassword(request.getParameter("password"));
        user.setNickname(request.getParameter("nickname"));
        user.setGender(request.getParameter("sex"));
        user.setEmail(request.getParameter("email"));
        user.setIdcard(request.getParameter("idcard"));
        user.setPhonenum(request.getParameter("phonenum"));
       /* user.setAge(Integer.parseInt(request.getParameter("age")));*/
        if (userDao.saveUser(user)){
            resultMap.put("result",1);
        }else {
            resultMap.put("result",0);
        }
        return resultMap;
    }

    public Map<String,Object> checkAccount(String account) {
        if (null==account){
            resultMap.put("result",2);  //不正确
        }
        if (null==userDao.queryUserByAccount(account)){
            resultMap.put("result",1);  //合格
        }else {
            resultMap.put("result",2);
        }
        return resultMap;
    }

    public Map<String,Object> checkPhonenum(String phonenum) {
        if (!VerifyUtils.verifyPhonenum(phonenum))
        {
            resultMap.put("result",2);  //不合格
        }
        if (null==userDao.queryUserByPhonenum(phonenum)){
            resultMap.put("result",1);
        }else {
            resultMap.put("result",3);//已存在
        }
        return resultMap;
    }

    public Map<String,Object> checkIdcard(String idcard) {
        if (VerifyUtils.verifyIdcard(idcard)){
            resultMap.put("result",2);//不合格
        }
        if (null==userDao.queryUserByIdcard(idcard)){
            resultMap.put("result",1);
        }else {
            resultMap.put("result",3);//已经存在
        }
        return resultMap;
    }

    public Map<String,Object> checkEmail(String email) {
        if (VerifyUtils.verifyEmail(email)){
            resultMap.put("result",2);//不合格
        }
        if (null==userDao.queryUserByEmail(email)){
            resultMap.put("result",1);
        }else {
            resultMap.put("result",3);//已经存在
        }
        return resultMap;
    }

    public Map<String,Object> checkValidation(HttpServletRequest request,String validation) {
        String sendValidation = (String) request.getSession().getAttribute("validation");
        if (null==sendValidation){
            resultMap.put("result",0);
        }else if (sendValidation.equals(validation)){
            resultMap.put("result",1);
        }else {
            resultMap.put("result",0);
        }
        return resultMap;
    }
    public Map<String,Object> sendMessage(HttpServletRequest request,String number) {
        int code=(int)(Math.random()*(9999-1000))+1000;
        boolean result=false;
        try {
            result=MessageSenderUtils.sendMessage("你的手机正在注册我们健身直播系统，你的验证码是：" +
                    "" + code+" 非本人请忽视！", number);
        }catch (Exception e){
            resultMap.put("result",0);//失败
            return resultMap;
        }
        if (result){
            request.getSession().setAttribute("validation",code);
            resultMap.put("result",1);
        }
        return resultMap;
    }
}
