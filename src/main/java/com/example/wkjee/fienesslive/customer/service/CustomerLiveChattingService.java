package com.example.wkjee.fienesslive.customer.service;

import com.example.wkjee.fienesslive.customer.dao.CustomerDaoImp;
import com.example.wkjee.fienesslive.customer.dao.ICustomerDao;
import com.example.wkjee.fienesslive.manager.domain.LiveTheme;
import com.example.wkjee.fienesslive.manager.domain.User;
import com.example.wkjee.fienesslive.tools.FileSaveTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by wkj_pc on 2017/8/15.
 */
@Service
public class CustomerLiveChattingService {

    private ICustomerDao wsCustomerDao=new CustomerDaoImp();    //websocket无法注入service

    @Autowired
    ICustomerDao customerDao;
    @Autowired
    private  Environment env;
    public int wsGetFansNumberByAccount(String account) {
        return wsCustomerDao.getFansNumberByAccount(account);
    }

    public List<User> getAllLiveUserInfo() {
        return customerDao.getAllLiveUserInfos();
    }

    public List<LiveTheme> getUserLiveThemes() {
        return customerDao.getAllLiveThemes();
    }
    /**用户直播风格上传*/
    public String updateLiveUserStyle(int uid, List<String> liveThemes) {
        return (customerDao.updateUserLiveThemes(uid,liveThemes))?"true":"failed";
    }
    /**用户视频上传*/
    public String uploadUserVideos(MultipartFile file, String title, int uid, String thumbnail) {

        /** 用户缩略图上传*/
        UUID uuid = UUID.randomUUID();
        String amatarUrl=env.getProperty("fitnesslive_img_save_url")+"/img/media/pic/"+uid+uuid.toString()+".jpg";
        if (!FileSaveTools.setLocalPicSave(thumbnail,amatarUrl)) return "failed";

        /** 视频上传 */
        String videoUrl=env.getProperty("fitnesslive_img_save_url")+"/img/media/video/"+uid+uuid.toString()+".mp4";
        if (!FileSaveTools.uploadVideo(file, videoUrl)) return "failed";

        /** 修改数据库 */
        return (customerDao.uploadUserVideo(title,
                env.getProperty("get_img_url")+"/img/media/video/"+uid+uuid.toString()+".mp4",
                env.getProperty("get_img_url")+"/img/media/pic/"+uid+uuid.toString()+".jpg",
                uid))?"true":"failed";
    }

}
