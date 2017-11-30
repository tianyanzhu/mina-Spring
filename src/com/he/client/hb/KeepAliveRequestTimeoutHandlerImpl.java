package com.he.client.hb;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

/**
 * Created by Administrator on 2017/7/11.
 */
public class KeepAliveRequestTimeoutHandlerImpl  implements KeepAliveRequestTimeoutHandler {

    private String TAG = KeepAliveRequestTimeoutHandlerImpl.class.getSimpleName();
    private int count = 0;

    public void keepAliveRequestTimedOut(KeepAliveFilter paramKeepAliveFilter,  IoSession paramIoSession) throws Exception {
		/* 心跳超时时处理 */
        //重新连接服务器
       System.out.println("keepAliveRequestTimedOut" + count);
        if(count > 2){
            count = 0;
            paramIoSession.close(true);
        }
        count++;
    }

}

