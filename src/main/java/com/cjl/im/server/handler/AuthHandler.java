package com.cjl.im.server.handler;

import com.cjl.im.server.session.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class AuthHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (ctx.channel().hasAttr(SessionUtil.ATTRIBUTE_KEY)) {
            ctx.pipeline().remove(this);
            ctx.fireChannelRead(msg);
        } else {
            ctx.channel().close();
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().hasAttr(SessionUtil.ATTRIBUTE_KEY)) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }

}
