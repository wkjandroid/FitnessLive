package com.example.wkjee.fienesslive.customer.service;

import com.example.wkjee.fienesslive.customer.dao.CustomerDaoImp;
import com.example.wkjee.fienesslive.customer.dao.ICustomerDao;

/**
 * Created by wkj_pc on 2017/8/15.
 */

public class CustomerLiveChattingService {

    private ICustomerDao customerDao=new CustomerDaoImp();

    public int getFansNumberByAccount(String account) {
        return customerDao.getFansNumberByAccount(account);
    }
}
