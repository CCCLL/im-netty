package com.cjl.im.client.instruction;

import java.util.Scanner;

import com.cjl.im.dto.LoginRequestPacket;

import io.netty.channel.Channel;

public class LoginInstruction implements Instruction{
    public static final String INSTRUCTION_SYMBOL = "login";

    @Override
    public void execute(Scanner scanner, Channel channel) {
        System.out.println("请登录，输入用户名: ");
        String line = scanner.nextLine();
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
