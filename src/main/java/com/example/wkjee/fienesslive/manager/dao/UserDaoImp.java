package com.example.wkjee.fienesslive.manager.dao;

import com.example.wkjee.fienesslive.manager.domain.User;
import com.example.wkjee.fienesslive.tools.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wkj_pc on 2017/6/5.
 */
@Repository
public class UserDaoImp implements IUserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
   @Resource
    private UserRowMapper userRowMapper;
    @Override
    public User queryUserByAccountAndPassword(String account, String password) {
        String sql="select uid,account,name,password,gender,nickname,email,idcard,phonenum," +
                "role,amatar,age,islive,grade,fansnum,attentionnum from user where account=? and password=?";
        List query = jdbcTemplate.query(sql, new String[]{account, password}, userRowMapper);
        return (query.size()>0)?(User)query.get(0):null;
    }

    @Override
    public User queryUserByEmail(String email) {
        String sql="select uid,account,name,password,gender,nickname,email,idcard,phonenum," +
                "role,amatar,age,islive,grade,fansnum,attentionnum  from user where email=?";
        List<User> query=jdbcTemplate.query(sql,new String[]{email}, userRowMapper);
        return (query.size()>0)?(User)query.get(0):null;
    }

    @Override
    public User queryUserByIdcard(String idcard) {
        String sql="SELECT uid,account,name,password,gender,nickname,email,idcard,phonenum," +
                "role,amatar,age,islive,grade,fansnum,attentionnum  FROM user where idcard=?";
        List query = jdbcTemplate.query(sql, new String[]{idcard}, userRowMapper);
        return (query.size()>0)?(User)query.get(0):null;
    }

    @Override
    public User queryUserByPhonenum(String phonenum) {
        String sql="select uid,account,name,password,gender,nickname,email,idcard,phonenum," +
                "role,amatar,age,islive,grade,fansnum,attentionnum  from user where phonenum=?";
        List query = jdbcTemplate.query(sql, new String[]{phonenum}, userRowMapper);
        return (query.size()>0)?(User)query.get(0):null;
    }

    @Override
    public User queryUserByAccount(String account) {
        String sql="select uid,account,name,password,gender,nickname,email,idcard,phonenum," +
                "role,amatar,age ,islive,grade,fansnum,attentionnum from user where account=?";
        List queryList = jdbcTemplate.query(sql, new String[]{account}, userRowMapper);
        return (queryList.size()>0)?(User)queryList.get(0):null;
    }

    @Override
    public boolean deleteUserByAccount(String account) {
        String sql="delete from user where account=?";
        int deleteRows = jdbcTemplate.update(sql, account);
        return (deleteRows>0)?true:false;
    }

    @Override
    public User queryUserById(int id) {
        String sql="select uid,account,name,password,gender,nickname,email,idcard,phonenum," +
                "role,amatar,age,islive,grade,fansnum,attentionnum  from user where uid=?";
        List query = jdbcTemplate.query(sql,new Integer[]{id}, userRowMapper);
        return (query.size()>0)?(User)query.get(0):null;
    }

    @Override
    public List<User> queryAdminAll() {
        String sql="select uid,account,name,password,gender,nickname,email,idcard,phonenum," +
                "role,amatar,age,islive,grade,fansnum,attentionnum  from Admin where role<3";
        List<User> users = jdbcTemplate.queryForList(sql, User.class);
        return users;
    }
    @Override
    public boolean saveUser(User user) {
        String sql="insert into user (account,name,password,gender,nickname,email,idcard,phonenum,role) " +
                "values(?,?,?,?,?,?,?,?,?)";
        int updateRows = jdbcTemplate.update(sql,
                new String[]{user.getAccount(), user.getName(), user.getPassword(), user.getGender()
                , user.getNickname(), user.getEmail(), user.getIdcard(), user.getPhonenum()},
                new int[]{3});
        return (updateRows>0)?true:false;
    }

    @Override
    public boolean deleteUserById(int id) {
        String sql="DELETE FROM USER WHERE uid=?";
        int deleteRows = jdbcTemplate.update(sql, id);
        return (deleteRows>0)?true:false;
    }

    @Override
    public boolean updateUser(User user) {
        String sql="update user set account=?,name=?,password=?,gender=?," +
                "nickname=?,email=?,idcard=?,phonenum=?,role=?,age=? WHERE uid=?";
        int updateRows = jdbcTemplate.update(sql, new String[]{user.getAccount(), user.getName(),
                user.getPassword(), user.getGender(), user.getNickname(), user.getEmail(),
                user.getIdcard(), user.getPhonenum()}, new Integer[]{user.getRole(),
                user.getAge(), user.getUid()});
        return (updateRows>0)?true:false;
    }

    @Override
    public boolean deleteUser() {
        int deleteRows = jdbcTemplate.update("delete * from user");
        return (deleteRows>0)?true:false;
    }

    @Override
    public List<User> queryUserAll() {
        String sql="select uid,account,name,password,gender,nickname,email,idcard,phonenum," +
                "role,amatar,age,islive,grade,fansnum,attentionnum  from user";
        List<User> users = jdbcTemplate.queryForList(sql, User.class);
        return (users.size()>0)?users:null;
    }

}
