package com.cjl.im.server.handler;

import com.cjl.im.dto.MessageRequestPacket;
import com.cjl.im.dto.MessageResponsePacket;
import com.cjl.im.server.session.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestPacketHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        Channel channel = SessionUtil.INSTANCE.getChannel(msg.getUserNameTo());

        if (channel == null) {
            System.out.println("用户：" + msg.getUserNameTo() + "，不在线");
        } else {
            System.out.println(msg);
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setUserNameFrom(msg.getUserNameFrom());
            messageResponsePacket.setMessage(msg.getMessage());
            channel.writeAndFlush(messageResponsePacket);
        }

    }
}
