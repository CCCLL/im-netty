package com.cjl.im.client.handler;


import com.cjl.im.dto.LoginResponsePacket;
import com.cjl.im.server.session.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;

public class LoginResponsePacketHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()){
            Attribute<String> attr = ctx.channel().attr(SessionUtil.ATTRIBUTE_KEY);
            attr.set(msg.getUserName());
            System.out.println("登陆成功");
        }else {
            System.out.println("登陆失败");
        }

    }
}
