package com.cjl.im.server.handler;

import java.util.List;
import java.util.Map;

import com.cjl.im.dto.GroupRequestPacket;
import com.cjl.im.dto.GroupResponsePacket;
import com.cjl.im.server.session.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GroupRequestPacketHandler extends SimpleChannelInboundHandler<GroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupRequestPacket msg) throws Exception {
        Map<String, List<String>> groupCache = SessionUtil.INSTANCE.getGroupCache();
        groupCache.put(msg.getGroupName(),msg.getUserNames());
        GroupResponsePacket groupResponsePacket = new GroupResponsePacket();
        groupResponsePacket.setGroupName(msg.getGroupName());
        groupResponsePacket.setUserNames(msg.getUserNames());
        for (String userName : msg.getUserNames()) {
            Channel channel = SessionUtil.INSTANCE.getChannel(userName);
            channel.writeAndFlush(groupResponsePacket);
        }
        System.out.println("创建群组："+msg.getGroupName()+", 群组成员: "+msg.getUserNames());

    }
}
