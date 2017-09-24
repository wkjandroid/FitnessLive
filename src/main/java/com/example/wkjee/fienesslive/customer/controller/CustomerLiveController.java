package com.example.wkjee.fienesslive.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.wkjee.fienesslive.customer.service.CustomerLiveChattingService;
import com.example.wkjee.fienesslive.manager.domain.LiveTheme;
import com.example.wkjee.fienesslive.manager.domain.User;
import com.example.wkjee.fienesslive.tools.Base64DecoderTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by wkj_pc on 2017/8/22.
 */
@Controller
@RequestMapping("/customer/live")
public class CustomerLiveController {

    @Autowired
    CustomerLiveChattingService liveService;
    /**
     * 获取全部直播用户的信息，并返回
     */
    @RequestMapping(value = "/getHomeLiveUserInfos")
    @ResponseBody
    public String getHomeLiveUserInfos() {
        List<User> liveUsers = liveService.getAllLiveUserInfo();
        return JSON.toJSONString(liveUsers);
    }
    /**
     * 获取直播用户的信息，并返回
     */
    @RequestMapping(value = "/getLiveUserInfo")
    @ResponseBody
    public String getLiveUserInfo(@RequestParam(value = "account")String account) {
        User liveUser = liveService.getLiveUserByAccount(account);
        return JSON.toJSONString(liveUser);
    }

    /**
     * 获取全部直播用户的直播风格，并返回
     */
    @RequestMapping(value = "/getHomeUserLiveThemes")
    @ResponseBody
    public String getHomeUserLiveThemes() {
        List<LiveTheme> liveThemes = liveService.getUserLiveThemes();
        return JSON.toJSONString(liveThemes);
    }

    /**
     * 添加直播用户的直播风格
     */
    @RequestMapping(value = "/updateLiveUserStyle")
    @ResponseBody
    public String addLiveUserStyle(@RequestParam(value = "uid", defaultValue = "") String uid,
                                   @RequestParam(value = "livethemes", defaultValue = "") String livethemes) {
        List<String> themes = JSON.parseObject(livethemes, new TypeReference<List<String>>(){});
        return liveService.updateLiveUserStyle(Integer.parseInt(uid),themes);
    }

    /**用户视频上传*/
    @RequestMapping(value = "/uploadRecoderVideo")
    @ResponseBody
    public String uploadRecoderVideo(@RequestParam(value = "file")MultipartFile file,@RequestParam(value = "title")String title,
                                     @RequestParam(value = "uid")String uid,@RequestParam(value = "thumbnail")String thumbnail){

        return liveService.uploadUserVideos(file,title,Integer.parseInt(uid),thumbnail);
    }

    @RequestMapping(value = "/getUserUploadVideo")
    @ResponseBody
    public String getUserUploadVideo(@RequestParam(value = "uid")String uid){
        return liveService.getUserUploadVideoByUid(Integer.parseInt(uid));
    }
    @RequestMapping(value = "/closeLiveStatus")
    @ResponseBody
    public void closeLiveStatus(@RequestParam(value = "account")String account){
       liveService.setLiveStatusClosed(account);
    }

}