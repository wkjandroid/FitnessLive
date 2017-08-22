package com.example.wkjee.fienesslive.customer.dao;

import com.example.wkjee.fienesslive.manager.domain.LiveTheme;
import com.example.wkjee.fienesslive.manager.domain.User;
import com.example.wkjee.fienesslive.tools.DataSourceTools;
import com.example.wkjee.fienesslive.tools.FansRowMapper;
import com.example.wkjee.fienesslive.tools.LiveThemeRowMapper;
import com.example.wkjee.fienesslive.tools.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by wkj_pc on 2017/6/17.
 */
@Repository
public class CustomerDaoImp implements ICustomerDao {

    @Autowired
    private JdbcTemplate template;
    @Autowired
    private UserRowMapper userRowMapper;
    @Autowired
    private LiveThemeRowMapper liveThemeMapper;

    private JdbcTemplate fansTemplate=new JdbcTemplate(DataSourceTools.getDataSource());
    private FansRowMapper fansRowMapper=new FansRowMapper();

    @Override
    public String addLIveUserStyle(int uid, List<LiveTheme> liveThemes) {
        String delUserStyle = "delete from livethemes where u_id=?";
        template.update(delUserStyle,new int[]{uid});
        template.batchUpdate();
        return null;
    }

    @Override
    public List<LiveTheme> getAllLiveThemes() {
        String sql="select * from livethemes where islive=1";
        List query = template.query(sql, liveThemeMapper);
        return (query.size()>0) ? query : null;
    }

    @Override
    public List<User> getAllLiveUserInfos() {
        String sql = "select * from user where islive=1";
        List query = template.query(sql, userRowMapper);
        return (query.size()>0)?query:null;
    }

    @Override
    public int getFansNumberByAccount(String account) {
        String sql="SELECT * FROM fans WHERE fs_account=?";
        List query = fansTemplate.query(sql,new String []{account}, fansRowMapper);
        return (query.size()>0)?query.size():0;
    }

    @Override
    public boolean registerWeiboUser(User loginUser) {
        String sql="insert into user (account,name,gender,nickname,phonenum,amatar) values(?,?,?,?,?,?)";
        int updateRows = template.update(sql, new String[]{loginUser.getAccount(), loginUser.getName(),
                loginUser.getGender(),
                loginUser.getNickname(), loginUser.getPhonenum(),loginUser.getAmatar()});
        return (updateRows>0)?true:false;

    }

    @Override
    public User getUserInfoByAccount(String account) {
        String sql="select * from user where account=?";
        List query = template.query(sql, new String[]{account}, userRowMapper);
        return (query.size()>0)?((User)query.get(0)):null;
    }

    @Override
    public boolean getUserByAccountAndPwd(User loginUser) {
        String sql="select * from user where account=? and password=?";
        List query = template.query(sql, new String[]{loginUser.getAccount(), loginUser.getPassword()}, userRowMapper);
        return (query.size()>0)?true:false;
    }

    @Override
    public boolean registerQQUser(User loginUser) {
        /* user.setAccount(openid); user.setToken("qq:"+token);user.setNickname
        user.setGender user.setAmatar*/
        String sql="insert into user (account,gender,nickname,amatar) values(?,?,?,?)";
        int updateRows = template.update(sql, new String[]{loginUser.getAccount(), loginUser.getGender(),
                loginUser.getNickname(), loginUser.getAmatar()});
        return (updateRows>0)?true:false;
    }

    @Override
    public boolean checkUserExist(String account) {
        String sql="select * from user where account=?";
        List query = template.query(sql, new String[]{account}, userRowMapper);
        return (query.size()>0)?true:false;
    }

}
