package com.he.client.hb;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/*import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.general.net.MsgType;
import com.general.tools.Logs*/;

public class MinaIoHandler extends IoHandlerAdapter {
	private String TAG = MinaIoHandler.class.getSimpleName();
	//public Handler cbHandler = null;
	public String addr = "";

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
		System.out.println(addr + "sessionOpened");

	}

	/* */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
		System.out.println(addr + "sessionCreated");
	}

	/* */
	/*private boolean sendMessage(Handler mHandler, int type, Object obj) {
		Message msg = new Message();
		msg.what = type;
		msg.obj = obj;
		return mHandler.sendMessage(msg);
	}*/

	//@SuppressLint("NewApi")
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println(addr + "messageReceived");
		//if (null != cbHandler) {
			System.out.println("=========================================");
			System.out.println(message.toString());
			/*RecvMsg recvMsg = new RecvMsg();
			recvMsg.session = session;
			recvMsg.msg = message;
			sendMessage(cbHandler, MsgType.MSG_TYPE_MINA_RECEIVE, recvMsg);*/
			System.out.println("=======MSG_TYPE_MINA_RECEIVE=============");
		/*} else {
			System.out.println("UnRegistered IMinaCallback method!");
		}*/
		if (message.toString().equals("HEART_BEAT_REQ")) {
			//收到心跳包
			System.out.println("收到心跳包");
			session.write("HEART_BEAT_RESP");
		}
		//super.messageReceived(session, message);
	}

	/* */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message); //发送消息
		System.out.println(addr + "messageSent");
	}

	/* */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
		System.out.println(addr + "sessionIdle");
	}

	/* */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		//System.out.println(addr + "sessionClosed");
		//MinaManager.setLogged(false);
		/*if (null != cbHandler) {*/
			System.out.println(addr + "会话已经断开，重新开始建立长连接");
			// 发送回执
			System.out.println("终端连接端开");
			//sendMessage(cbHandler, MsgType.MSG_TYPE_MINA_DISCONNECT, null);
		/*}*/
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
		System.out.println(addr + "exceptionCaught");
	}

	/* */
	@Override
	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.inputClosed(session);
		System.out.println(addr + "inputClosed");
	}

}
