package com.example.wkjee.fienesslive.customer.dao;


import com.example.wkjee.fienesslive.manager.domain.User;

/**
 * Created by wkj_pc on 2017/6/17.
 */

public interface ICustomerDao {

    boolean checkUserExist(String account);

    boolean registerQQUser(User loginUser);

    boolean getUserByAccountAndPwd(User loginUser);

    User getUserInfoByAccount(String account);

    boolean registerWeiboUser(User loginUser);
}
