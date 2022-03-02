package com.cjl.im.server.handler;

import com.cjl.im.dto.LoginRequestPacket;
import com.cjl.im.dto.LoginResponsePacket;
import com.cjl.im.server.session.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {

        System.out.println("user:" + msg.getUserName() + " login success");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setUserName(msg.getUserName());
        if (ctx.channel().hasAttr(SessionUtil.ATTRIBUTE_KEY)) {
            loginResponsePacket.setReason("用户已登陆");
        } else {
            SessionUtil.INSTANCE.putSession(msg.getUserName(), ctx.channel());
            Attribute<String> attr = ctx.channel().attr(SessionUtil.ATTRIBUTE_KEY);
            attr.set(msg.getUserName());
            loginResponsePacket.setReason("登陆成功");
            System.out.println("server attr "+attr.get());
        }
        loginResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

}
