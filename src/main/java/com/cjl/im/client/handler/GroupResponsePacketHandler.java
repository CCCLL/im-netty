package com.cjl.im.client.handler;

import com.cjl.im.dto.GroupResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GroupResponsePacketHandler extends SimpleChannelInboundHandler<GroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupResponsePacket msg) throws Exception {
        System.out.println("加入群组："+msg.getGroupName()+", 群组成员: "+msg.getUserNames());
    }
}
