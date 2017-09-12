package com.example.wkjee.fienesslive.manager.controller;

import com.example.wkjee.fienesslive.manager.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wkj_pc on 2017/5/20.
 */
@Controller
@RequestMapping("/manager")
public class IndexController {

    @Autowired
    private LoginService loginService;

    /** 跳转到首页*/
    @RequestMapping("/")
    public String index(){
        loginService.getAllUploadVideos();
        return "index";
    }
    /** 首页表单测试*/
    @RequestMapping(value = "/test")
    @ResponseBody
    public void test(@RequestParam(value = "test")MultipartFile file) throws IOException, ServletException {
        File file1=new File("F://a.mp4");
        FileOutputStream fileOutputStream = new FileOutputStream(file1);
        byte[] bytes = file.getBytes();
            fileOutputStream.write(bytes);
        fileOutputStream.close();
    }

}
