package com.cjl.im.client.instruction;

import java.util.Scanner;

import com.cjl.im.dto.MessageRequestPacket;
import com.cjl.im.server.session.SessionUtil;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class SendInstruction implements Instruction{
    public static final String INSTRUCTION_SYMBOL = "send";

    @Override
    public void execute(Scanner scanner, Channel channel) {
        System.out.println("请输入对方名字和要发送的信息: ");
        String toUserId = scanner.next();
        String message = scanner.next();
        MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
        Attribute<String> attr = channel.attr(SessionUtil.ATTRIBUTE_KEY);
        messageRequestPacket.setUserNameFrom(attr.get());
        messageRequestPacket.setUserNameTo(toUserId);
        messageRequestPacket.setMessage(message);
        channel.writeAndFlush(messageRequestPacket);
    }
}
