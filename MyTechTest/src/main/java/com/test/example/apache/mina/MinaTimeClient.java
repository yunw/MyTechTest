package com.test.example.apache.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.statistic.ProfilerTimerFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaTimeClient {

    public static void main(String[] args) {
        // 创建客户端连接器.
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        connector.getFilterChain().addFirst("profile", new ProfilerTimerFilter(TimeUnit.MILLISECONDS, IoEventType.SESSION_CREATED, IoEventType.SESSION_OPENED, IoEventType.SESSION_CLOSED, IoEventType.MESSAGE_RECEIVED, IoEventType.MESSAGE_SENT, IoEventType.SESSION_IDLE, IoEventType.EXCEPTION_CAUGHT, IoEventType.WRITE, IoEventType.CLOSE));

        // 设置连接超时检查时间
        connector.setConnectTimeoutCheckInterval(30);
        connector.setHandler(new TimeClientHandler());

        // 建立连接
        ConnectFuture cf = connector.connect(new InetSocketAddress("localhost", 6488));
        // 等待连接创建完成
        cf.awaitUninterruptibly();

        cf.getSession().write("Hi Server!");
        cf.getSession().write("quit");

        // 等待连接断开
        cf.getSession().getCloseFuture().awaitUninterruptibly();
        // 释放连接
        connector.dispose();
    }
}
