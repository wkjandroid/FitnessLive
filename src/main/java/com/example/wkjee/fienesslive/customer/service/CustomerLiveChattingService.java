package com.example.wkjee.fienesslive.customer.service;

import com.example.wkjee.fienesslive.customer.dao.CustomerDaoImp;
import com.example.wkjee.fienesslive.customer.dao.ICustomerDao;
import com.example.wkjee.fienesslive.manager.domain.LiveTheme;
import com.example.wkjee.fienesslive.manager.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wkj_pc on 2017/8/15.
 */
@Service
public class CustomerLiveChattingService {

    private ICustomerDao wsCustomerDao=new CustomerDaoImp();    //websocket无法注入service

    @Autowired
    ICustomerDao customerDao;

    public int wsGetFansNumberByAccount(String account) {
        return wsCustomerDao.getFansNumberByAccount(account);
    }

    public List<User> getAllLiveUserInfo() {
        return customerDao.getAllLiveUserInfos();
    }

    public List<LiveTheme> getUserLiveThemes() {
        return customerDao.getAllLiveThemes();
    }

    public String addLiveUserStyle(int uid, List<LiveTheme> liveThemes) {
        return customerDao.addLIveUserStyle(uid,liveThemes);
    }
}
