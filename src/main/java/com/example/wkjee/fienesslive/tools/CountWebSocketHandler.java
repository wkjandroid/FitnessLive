package com.example.wkjee.fienesslive.tools;

import com.alibaba.fastjson.JSON;
import com.example.wkjee.fienesslive.conf.LiveChattingMessage;
import com.example.wkjee.fienesslive.customer.service.CustomerLiveChattingService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.*;
/**
 * 该websocket主要是用来控制直播消息的连接
 */
@Component
public class CountWebSocketHandler extends TextWebSocketHandler {
    //存放当前全部在线人数
    private static long count = 0;
    //存放直播用户和观众会话
    private static Map<String,Map<String,WebSocketSession>> sessionMap = new HashMap();
    //处理分发直播间的评论信息
    private CustomerLiveChattingService customerLiveChattingService=new CustomerLiveChattingService();  //用户直播聊天服务

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String str = message.getPayload();
        System.out.println("------"+str);
        if (null!=str || ""!=str){
            String[] split = session.getUri().getPath().split("/");
            if (split[4].contentEquals("live")){    //该会话是直播员会话，将信息分发给直播间
                //获取该直播间的用户
//                LiveChattingMessage liveChattingMessage = JSON.parseObject(str, LiveChattingMessage.class);
//                liveChattingMessage.setIntent(1);
                transSendMessage(str, split[2]);
                //解析获取到的直播信息
                // LiveChattingMessage chattingMessage = JSON.parseObject(str,LiveChattingMessage.class);
            }else{  //该用户为观看者,将收到的信息转发到直播间
                transSendMessage(str, split[2]);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        //获取连接用户名和连接目的（直播或观看直播）
        ///websocket/liveaccount/watchaccount/live|watchlive
        System.out.println("连接已经建立");
        String [] livePersonInfo=session.getUri().getPath().split("/");
        if (livePersonInfo[4].contentEquals("live")){   //直播
            Map<String,WebSocketSession> createMap=new HashMap<>();
            createMap.put(livePersonInfo[2],session);
            sessionMap.put(livePersonInfo[2],createMap);
            session.sendMessage(new TextMessage("success"));
            sendFansMsg(session, livePersonInfo[2]);

        }else if (livePersonInfo[4].contentEquals("watchlive")){    //观看直播
            Map<String,WebSocketSession> createMap=sessionMap.get(livePersonInfo[2]);   //获取直播用户的全部观看session
            if (null==createMap)
                createMap=new HashMap<>();
            createMap.put(livePersonInfo[3],session);
            session.sendMessage(new TextMessage("success"));
            sendFansMsg(session, livePersonInfo[2]);
        }
    }

    public void sendFansMsg(WebSocketSession session, String account) throws IOException {
        LiveChattingMessage fansNumMsg=new LiveChattingMessage();
        try{
            int number=customerLiveChattingService.getFansNumberByAccount(account);
            fansNumMsg.setFansnumber(number);
            fansNumMsg.setIntent(2);
            fansNumMsg.setFrom("server");
            fansNumMsg.setMid(0);
            fansNumMsg.setTo("terminal");
            session.sendMessage(new TextMessage(JSON.toJSONString(fansNumMsg)));
            System.out.println("粉丝数量发送成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String[] dirs = session.getUri().getPath().split("/");
        if (null!=sessionMap.get(dirs[2]))
        {
            if (dirs[2].contentEquals(dirs[3])){    //直播员连接关闭
                sessionMap.get(dirs[2]).clear();
            }else { //观众连接关闭
                sessionMap.get(dirs[2]).remove(dirs[3]);
            }
        }
        System.out.println("连接关闭"+status);
    }
    //将信息遍历发送到直播间
    private void transSendMessage(String str, String name) throws IOException {
        Map<String, WebSocketSession> createMap = sessionMap.get(name);
        Collection<WebSocketSession> values = createMap.values();
        for (WebSocketSession session:values){
            session.sendMessage(new TextMessage(str));
        }
    }
}