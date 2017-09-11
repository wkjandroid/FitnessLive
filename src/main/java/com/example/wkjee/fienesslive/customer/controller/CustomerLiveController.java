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
    @RequestMapping(value = "/uploadRecoderVideo")
    @ResponseBody
    public String uploadRecoderVideo(@RequestParam(value = "file")MultipartFile file ){
        try{
            File writefile = new File("F://a.mp4");
            FileOutputStream outputStream = new FileOutputStream(writefile);
            System.out.print(file.getBytes().length);
            outputStream.write(file.getBytes());
            outputStream.flush();
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

}