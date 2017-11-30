package com.he.client.hb;

import com.he.client.minaclient.ClientHandler;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/7/11.
 * 模拟终端连接
 */
public class HbMinaClientTest {
    private  static String addr="127.0.0.1";
    private  static int PORT=8888;
    public static void main(String[] args) throws InterruptedException {
// 创建一个非阻塞的客户端程序
        NioSocketConnector connector = new NioSocketConnector();
        connector = new NioSocketConnector();
        // 设置链接超时时间
        connector.setConnectTimeoutMillis(30);
        // 设置IdleTimeOut
        connector.getSessionConfig().setBothIdleTime(15);
        // 添加编码过滤器
        TextLineCodecFactory codecFactory = new TextLineCodecFactory(
                Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
                LineDelimiter.WINDOWS.getValue());
        codecFactory.setDecoderMaxLineLength(10000000);
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(codecFactory));

        // 添加日志过滤器
        LoggingFilter logfilter = new LoggingFilter();
        connector.getFilterChain().addLast("logging", logfilter);
        // 添加心跳Filter
        connector.getSessionConfig().setKeepAlive(true);
        KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryClient()  ;
        KeepAliveRequestTimeoutHandler heartBeatHandler = new KeepAliveRequestTimeoutHandlerImpl();
        KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,IdleStatus.BOTH_IDLE, heartBeatHandler);
        heartBeat.setRequestInterval(5);
        heartBeat.setRequestTimeout(10);
        // 心跳间隔后是否继续其他sessionIdle回调，测试阶段设置为true可以打印日志根据状态，release版不需要显示设置为false。
        heartBeat.setForwardEvent(true);
        connector.getFilterChain().addLast("heartbeat", heartBeat);

        // 添加业务逻辑处理器类
        MinaIoHandler minaIoHandler = new MinaIoHandler();
        minaIoHandler.addr = addr + ":" + PORT;
       // minaIoHandler.cbHandler = cbHandler;
        connector.setHandler(minaIoHandler);//设置事件处理器
        ConnectFuture cf = connector.connect(new InetSocketAddress(addr, PORT));//建立连接
        cf.awaitUninterruptibly();//等待连接创建完成
        cf.getSession().write("hello,测试！");//发送消息


    }
}
