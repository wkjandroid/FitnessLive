package com.example.wkjee.fienesslive.listener;

import com.example.wkjee.fienesslive.manager.domain.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wkj_pc on 2017/5/20.
 */
@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Map<User,HttpSession> userMap=new HashMap<>();
        sce.getServletContext().setAttribute("userMap",userMap);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
