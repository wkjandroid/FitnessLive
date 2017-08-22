package com.example.wkjee.fienesslive.customer.service;

import com.example.wkjee.fienesslive.customer.dao.CustomerDaoImp;
import com.example.wkjee.fienesslive.customer.dao.ICustomerDao;
import com.example.wkjee.fienesslive.manager.domain.LiveTheme;
import com.example.wkjee.fienesslive.manager.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wkj_pc on 2017/8/15.
 */
@Service
public class CustomerLiveChattingService {

    private ICustomerDao customerDao=new CustomerDaoImp();

    public int getFansNumberByAccount(String account) {
        return customerDao.getFansNumberByAccount(account);
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
