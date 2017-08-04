package com.example.wkjee.fienesslive.tools;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class CountWebSocketHandler extends TextWebSocketHandler {
    private static long count = 0;
    private static Map<String,WebSocketSession> sessionMap = new HashMap();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        Object parse = JSONUtils.parse(message.getPayload());
//        Collection<WebSocketSession> sessions = sessionMap.values();
//        for (WebSocketSession ws : sessions) {//广播
//            ws.sendMessage(message);
//        }
        System.out.println("收到消息"+"localaddress---------"+"id==="+session.getId()
                +" ---"+"remoteaddress=="+session.getRemoteAddress()
                +session.getUri()+"---------");
        session.sendMessage(new TextMessage(session.getUri()+",你是第" + (session.getId()+1) + "位访客")); //p2p
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessionMap.put(session.getUri().getPath(),session);
        System.out.println("连接已经建立");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (null!=sessionMap.get(session.getUri().getPath()))
        {
            sessionMap.remove(session.getUri().getPath());
        }
        System.out.println("连接关闭");
    }
    /**
     * 发送消息
     * @author xiaojf 2017/3/2 11:43
     */
    public static void sendMessage(String username,String message) throws IOException {
        sendMessage(Arrays.asList(username),Arrays.asList(message));
    }
    /**
     * 发送消息
     * @author xiaojf 2017/3/2 11:43
     */
    public static void sendMessage(Collection<String> acceptorList,String message) throws IOException {
        sendMessage(acceptorList,Arrays.asList(message));
    }
    /**
     * 发送消息，p2p 群发都支持
     * @author xiaojf 2017/3/2 11:43
     */
    public static void sendMessage(Collection<String> acceptorList, Collection<String> msgList) throws IOException {
        if (acceptorList != null && msgList != null) {
            for (String acceptor : acceptorList) {
                WebSocketSession session = sessionMap.get(acceptor);
                if (session != null) {
                    for (String msg : msgList) {
                        session.sendMessage(new TextMessage(msg.getBytes()));
                    }
                }
            }
        }
    }
}