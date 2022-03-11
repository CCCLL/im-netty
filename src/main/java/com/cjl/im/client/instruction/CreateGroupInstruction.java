package com.cjl.im.client.instruction;

import java.util.*;
import java.util.stream.Collectors;

import com.cjl.im.dto.GroupRequestPacket;
import com.cjl.im.server.session.SessionUtil;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class CreateGroupInstruction implements Instruction{
    public static final String INSTRUCTION_SYMBOL = "create group";
    private static final String SEPARATOR = ",";
    @Override
    public void execute(Scanner scanner, Channel channel) {
        System.out.println("请输入想要添加进群组的用户名(英文逗号分隔):");
        String line = scanner.nextLine();
        String[] names = line.split(SEPARATOR);
        GroupRequestPacket groupRequestPacket = new GroupRequestPacket();
        List<String> nameList = Arrays.stream(names).collect(Collectors.toList());
        Attribute<String> attr = channel.attr(SessionUtil.ATTRIBUTE_KEY);
        nameList.add(attr.get());
        groupRequestPacket.setUserNames(nameList);
        System.out.println("请输入群组名:");
        String groupName = scanner.nextLine();
        groupRequestPacket.setGroupName(groupName);
        channel.writeAndFlush(groupRequestPacket);
    }
}
