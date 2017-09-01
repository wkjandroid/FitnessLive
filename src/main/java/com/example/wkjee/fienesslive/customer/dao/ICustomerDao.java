package com.example.wkjee.fienesslive.customer.dao;


import com.example.wkjee.fienesslive.manager.domain.LiveTheme;
import com.example.wkjee.fienesslive.manager.domain.User;

import java.util.List;

/**
 * Created by wkj_pc on 2017/6/17.
 */

public interface ICustomerDao {

    boolean checkUserExist(String account);

    boolean registerQQUser(User loginUser);

    boolean getUserByAccountAndPwd(User loginUser);

    User getUserInfoByAccount(String account);

    boolean registerWeiboUser(User loginUser);

    int getFansNumberByAccount(String account);

    List<User> getAllLiveUserInfos();

    List<LiveTheme> getAllLiveThemes();

    String addLiveUserStyle(int uid, List<LiveTheme> liveThemes);

    boolean checkUserExistByMobileNum(String phonenum);

    boolean getUserByMobileAndPwd(User loginUser);

    User getUserInfoByMobile(String phonenum);

    String updateUserPassword(String mobilenum, String password);

    String registerUser(String mobilenum, String password);

    boolean updateUserAmatarByAccount(String account, String amatarUrl);

    boolean updateUserSexByAccount(String account, String content);

    boolean updateUserNicknameByAccount(String account, String content);

    boolean updateUserPersonalSignByAccount(String account, String content);
}
