package com.cjl.im.client;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.cjl.im.client.handler.LoginResponsePacketHandler;
import com.cjl.im.client.handler.MessageResponsePacketHandler;
import com.cjl.im.codec.PacketDecoder;
import com.cjl.im.codec.PacketEncoder;
import com.cjl.im.codec.Spliter;
import com.cjl.im.client.handler.ClientHandler;
import com.cjl.im.dto.LoginRequestPacket;
import com.cjl.im.dto.MessageRequestPacket;
import com.cjl.im.server.session.SessionUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;

public class NettyClient {
    private static final int MAX_RETRY = 5;

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 8088;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup).channel(NioSocketChannel.class).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponsePacketHandler());
                        ch.pipeline().addLast(new MessageResponsePacketHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (!Thread.interrupted()) {
                if (SessionUtil.INSTANCE.hasLogin(channel)){
                    System.out.println("请输入对方名字和要发送的信息: ");
                    String toUserId = sc.next();
                    String message = sc.next();
                    MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                    Attribute<String> attr = channel.attr(SessionUtil.ATTRIBUTE_KEY);
                    messageRequestPacket.setUserNameFrom(attr.get());
                    messageRequestPacket.setUserNameTo(toUserId);
                    messageRequestPacket.setMessage(message);
                    channel.writeAndFlush(messageRequestPacket);
                }else {
                    System.out.println("请登录，输入用户名: ");
                    String line = sc.nextLine();
                    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
                    loginRequestPacket.setUserName(line);
                    channel.writeAndFlush(loginRequestPacket);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

}
