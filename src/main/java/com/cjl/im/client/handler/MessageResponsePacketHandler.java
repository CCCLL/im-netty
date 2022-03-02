package com.cjl.im.client.handler;

import com.cjl.im.dto.MessageResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponsePacketHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        System.out.println("用户 "+ msg.getUserNameFrom()+": "+msg.getMessage());
    }
}
