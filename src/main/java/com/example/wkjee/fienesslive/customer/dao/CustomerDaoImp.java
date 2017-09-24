package com.example.wkjee.fienesslive.customer.dao;

import com.example.wkjee.fienesslive.manager.domain.LiveTheme;
import com.example.wkjee.fienesslive.manager.domain.UploadVideo;
import com.example.wkjee.fienesslive.manager.domain.User;
import com.example.wkjee.fienesslive.tools.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private UploadVideoMapper uploadVideoMapper;

    private JdbcTemplate wbTemplate=new JdbcTemplate(DataSourceTools.getDataSource());
    private FansRowMapper fansRowMapper=new FansRowMapper();

    @Override
    public User getLiveUserInfoByAccount(String account) {
        String sql="select * from users where account=?";
        List<User> query = template.query(sql, new String[]{account}, userRowMapper);
        return (query.size()>0)?query.get(0):null;
    }

    @Override
    public boolean setUserLiveStatusTagByAccount(int status,String account) {
        String sql="update users set islive=? where account=?";
        return (wbTemplate.update(sql,status,account)>0)?true:false;
    }

    @Override
    public List<UploadVideo> getUserUploadVideoByUid(int uid) {
        String sql="select uv_id,uv_title,uv_videourl,uv_thumbnailurl,uv_uploadtime,uid from uploadvideos where uid=?";
        List <UploadVideo>query = template.query(sql,new Integer[]{uid} , uploadVideoMapper);
        return (query.size()>0)?query:null;
    }

    @Override
    public boolean uploadUserVideo(String title, String videourl, String thumbnail, int uid) {
        String sql="insert into uploadvideos(uv_title,uv_videourl,uv_thumbnailurl,uv_uploadtime,uid) " +
                "values(?,?,?,?,?)";
        int update = template.update(sql, title, videourl, thumbnail, setDbTime(), uid);
        return (update>0)?true:false;
    }
    @Override
    public boolean updateUserLiveThemes(int uid, List<String> liveThemes) {
        String delUserLiveTheme = "delete from livethemes where uid=?";
        template.update(delUserLiveTheme,uid);
        try{
            BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1,liveThemes.get(i));
                    ps.setBoolean(2,false);
                    ps.setInt(3,uid);
                }
                @Override
                public int getBatchSize() {
                    return liveThemes.size();
                }
            };
            String sql="INSERT INTO livethemes(lt_name,lt_islive,uid) values(?,?,?)";
            template.batchUpdate(sql, setter);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updateUserSexByAccount(String account, String content) {
        String sql="update users set gender=? where account=?";
        int update = template.update(sql,content, account);
        return (update>0)?true:false;
    }

    @Override
    public boolean updateUserLiveBigPicByAccount(String account, String getImageUrl) {
        String sql="update users set livebigpic=? where account=?";
        int update = template.update(sql,getImageUrl, account);
        return (update>0)?true:false;
    }

    @Override
    public boolean updateUserAmatarByAccount(String account, String amatarUrl) {
        String sql="update users set amatar=? where account=?";
        int update = template.update(sql,amatarUrl, account);
        return (update>0)?true:false;
    }

    @Override
    public boolean updateUserNicknameByAccount(String account, String content) {
        String sql="update users set nickname=? where account=?";
        int update = template.update(sql,content, account);
        return (update>0)?true:false;
    }

    @Override
    public boolean updateUserPersonalSignByAccount(String account, String content) {
        String sql="update users set personalsign=? where account=?";
        int update = template.update(sql,content, account);
        return (update>0)?true:false;
    }

    @Override
    public List<LiveTheme> getAllLiveThemes() {

        String sql="select * from livethemes where lt_islive=0";
        List query = template.query(sql, liveThemeMapper);
        return (query.size()>0) ? query : null;

    }

    @Override
    public List<User> getAllLiveUserInfos() {
        String sql = "select * from users where islive=1";
        List<User> query = template.query(sql, userRowMapper);
        return (query.size()>0)?query:null;
    }

    @Override
    public int getFansNumberByAccount(String account) {
        String sql="SELECT * FROM fans WHERE fs_account=?";
        List query = wbTemplate.query(sql,new String []{account}, fansRowMapper);
        return (query.size()>0)?query.size():0;
    }

    @Override
    public boolean registerWeiboUser(User loginUser) {
        String sql="insert into users (account,name,gender,nickname,phonenum,amatar,createtime) values(?,?,?,?,?,?,?)";
        template.update(sql, loginUser.getName(), loginUser.getName(),
                loginUser.getGender(),
                loginUser.getNickname(), loginUser.getPhonenum(),loginUser.getAmatar(),setDbTime());
        String sql1="UPDATE users SET account=? WHERE account=?";
        int updateRows = template.update(sql1,getAccount(),loginUser.getName());
        return (updateRows>0)?true:false;
    }

    private String setDbTime() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd:HH/mm/ss");
        return dateFormat.format(new Date());
    }

    @Override
    public User getUserInfoByAccount(String account) {
        String sql="select * from users where account=?";
        List query = template.query(sql, new String[]{account}, userRowMapper);
        return (query.size()>0)?((User)query.get(0)):null;
    }
    @Override
    public User getUserInfoByName(String name) {
        String sql="select * from users where name=?";
        List query = template.query(sql, new String[]{name}, userRowMapper);
        return (query.size()>0)?((User)query.get(0)):null;
    }
    @Override
    public User getUserInfoByMobile(String phonenum) {
        String sql="select * from users where phonenum=?";
        List query = template.query(sql, new String[]{phonenum}, userRowMapper);
        return (query.size()>0)?((User)query.get(0)):null;
    }

    @Override
    public boolean getUserByAccountAndPwd(User loginUser) {
        String sql="select * from users where account=? and password=?";
        List query = template.query(sql, new String[]{loginUser.getAccount(), loginUser.getPassword()}, userRowMapper);
        return (query.size()>0)?true:false;
    }

    @Override
    public boolean getUserByMobileAndPwd(User loginUser) {
        String sql="select * from users where phonenum=? and password=?";
        List query = template.query(sql, new String[]{loginUser.getPhonenum(), loginUser.getPassword()}, userRowMapper);
        return (query.size()>0)?true:false;

    }

    @Override
    public boolean registerQQUser(User loginUser) {
        /* user.setAccount(openid); user.setToken("qq:"+token);user.setNickname
        user.setGender user.setAmatar*/
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd:HH/mm/ss");
        String sql="insert into users (account,name,gender,nickname,amatar,createtime) values(?,?,?,?,?,?)";
        template.update(sql, loginUser.getAccount(), loginUser.getAccount(),loginUser.getGender(),
                loginUser.getNickname(), loginUser.getAmatar(),dateFormat.format(new Date()));
        String sql1="UPDATE users SET account=? WHERE account=?";
        int updateRows = template.update(sql1,getAccount(),loginUser.getAccount());
        return (updateRows>0)?true:false;
    }

    @Override
    public boolean checkUserByNameExist(String name) {
        String sql="select * from users where name=?";
        List query = template.query(sql,new String[]{name}, userRowMapper);
        return (query.size()>0)?true:false;
    }

    @Override
    public boolean checkUserExistByMobileNum(String phonenum) {
        String sql="select * from users where phonenum=?";
        List query = template.query(sql,new String[]{phonenum}, userRowMapper);
        return (query.size()>0)?true:false;
    }

    @Override
    public String updateUserPassword(String mobilenum, String password) {
        String sql="UPDATE users SET password=? where phonenum=?";
        int update = template.update(sql, new String[]{ password , mobilenum });
        return (update>0)? "true" : "false";
    }

    @Override
    public String registerUser(String mobilenum, String password) {
        String sql="insert into users (account,password,nickname,phonenum,createtime) " +
                "values(?,?,?,?,?)";
        String sql1="UPDATE users SET account=? WHERE account=?";
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd:HH/mm/ss");
        template.update(sql,mobilenum, password,"小灰灰",
                mobilenum,dateFormat.format(new Date()));
        int updateRows = template.update(sql1,getAccount(),mobilenum);
        return (updateRows>0)?":true":":false";
    }
    public String  getAccount(){
        String sql="select * from users ORDER BY uid DESC limit 1";
        List<User> query = template.query(sql, userRowMapper);
        User user = query.get(0);
        long num=user.getUid()+100000;
        return String.valueOf(num);
    }
}
