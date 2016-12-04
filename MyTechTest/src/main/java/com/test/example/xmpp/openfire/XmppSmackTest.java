package com.test.example.xmpp.openfire;

import java.util.Collection;
import java.util.Iterator;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Session;

public class XmppSmackTest {

	private final static String server = "192.168.56.77";

	private final static int port = 5222;

	public static void main(String[] args) throws XMPPException {
		ConnectionConfiguration config = new ConnectionConfiguration(server, port);
		config.setCompressionEnabled(true);

		Connection connection = new XMPPConnection(config);
		/** 建立连接 */
		connection.connect();

		/** 用户管理 */
		AccountManager accountManager = connection.getAccountManager();
		for (String attr : accountManager.getAccountAttributes()) {
			System.out.println("AccountAttribute: " + attr);
		}

		/** 用户登陆，用户名、密码 */
		connection.login("yinsl", "abcd_1234");
		System.out.println(connection.getUser());

		/** 所有用户组 */
		Roster roster = connection.getRoster();

		/** 好友用户组，你可以用Spark添加用户好友，这样这里就可以查询到相关的数据 */
		Collection<RosterEntry> rosterEntiry = roster.getEntries();
		Iterator<RosterEntry> iter = rosterEntiry.iterator();
		while (iter.hasNext()) {
			RosterEntry entry = iter.next();
			System.out.println("Groups: " + entry.getGroups() + ", Type: " + entry.getType() + ", Name: "
					+ entry.getName() + ", Status: " + entry.getStatus() + ", User: " + entry);
		}

		/** 获取当前登陆用户的聊天管理器 */
		ChatManager chatManager = connection.getChatManager();
		/** 为指定用户创建一个chat，MyMessageListeners用于监听对方发过来的消息 */
		Chat chat = chatManager.createChat("yin_slin@" + server, new XmppSmackTest().new MyXmppMessageListeners());
		try {
			/** 发送消息 */
			chat.sendMessage("h!~ yin_slin……");

			/** 用message对象发送消息 */
			Message message = new Message();
			message.setBody("message");
			message.setProperty("color", "red");
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		connection.disconnect();
	}

	/**
	 * <b>function:</b> 消息监听器，用户监听对方发送的消息，也可以想对方发送消息
	 * 
	 * @author hoojo
	 * @createDate 2012-6-25 下午05:05:31
	 * @file SmackXMPPTest.java
	 * @package com.hoo.smack
	 * @project jwchat
	 * @blog http://blog.csdn.net/IBM_hoojo
	 * @email hoojo_@126.com
	 * @version 1.0
	 */
	class MyXmppMessageListeners implements MessageListener {
		public void processMessage(Chat chat, Message message) {
			if (message.getBody() != null) {
				try {
					/** 发送消息 */
					chat.sendMessage("dingding……" + message.getBody());
				} catch (XMPPException e) {
					e.printStackTrace();
				}

				System.out.println("From:::: " + message.getFrom() + ", To: " + message.getTo() + ", Type: "
						+ message.getType() + ", Sub: " + message.toXML() + ", body: " + message.getBody());
			}
		}
	}

}
