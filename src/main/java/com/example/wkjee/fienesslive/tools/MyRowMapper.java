package com.example.wkjee.fienesslive.tools;

import com.example.wkjee.fienesslive.manager.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wkj_pc on 2017/6/17.
 */
@Repository
public  class MyRowMapper implements RowMapper {
    /** sql的行映射*/
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user=new User();
        user.setUid(rs.getInt("uid"));
        user.setAccount(rs.getString("account"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setGender(rs.getString("gender"));
        user.setNickname(rs.getString("nickname"));
        user.setEmail(rs.getString("email"));
        user.setIdcard(rs.getString("idcard"));
        user.setPhonenum(rs.getString("phonenum"));
        user.setRole(rs.getInt("role"));
        user.setAmatar(rs.getString("amatar"));
        user.setAge(rs.getInt("age"));
        return user;
    }
}