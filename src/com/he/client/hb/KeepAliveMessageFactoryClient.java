package com.he.client.hb;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**
 * Created by Administrator on 2017/7/11.
 */
public class KeepAliveMessageFactoryClient implements KeepAliveMessageFactory {
    public boolean isRequest(IoSession session, Object message) {
        return false;
    }

    public boolean isResponse(IoSession session, Object message) {
        if (message.equals("HEART_BEAT_RESP")) {
            System.out.println("收到心跳响应: HEART_BEAT_RESP");
            return true;
        }
        return false;
    }

    public Object getRequest(IoSession session) {
        System.out.println("发送心跳:HEART_BEAT_REQ");
        return "HEART_BEAT_REQ";
    }

    public Object getResponse(IoSession session, Object request) {
        return null;
    }
}
