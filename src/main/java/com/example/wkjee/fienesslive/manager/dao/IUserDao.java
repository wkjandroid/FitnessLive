package com.example.wkjee.fienesslive.manager.dao;

import com.example.wkjee.fienesslive.manager.domain.User;

import java.util.List;

/**
 * Created by wkj_pc on 2017/6/5.
 */

public interface IUserDao {
    boolean saveUser(User user);
    boolean updateUser(User user);
    boolean deleteUser();
    boolean deleteUserById(int id);
    boolean deleteUserByAccount(String account);
    User queryUserByAccount(String account);
    User queryUserById(int id);
    List<User> queryAdminAll();
    List<User> queryUserAll();
    User queryUserByPhonenum(String phonenum);
    User queryUserByIdcard(String idcard);
    User queryUserByEmail(String email);
    User queryUserByAccountAndPassword(String account, String password);
}
