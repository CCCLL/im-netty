package com.cjl.im.dto;

import com.cjl.im.enums.Command;


public class LoginRequestPacket extends Packet {

    private String userName;

    private String password = "123";

    @Override
    public Command getCommand() {
        return Command.LOGIN_REQUEST;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestPacket{" + ", username='" + userName + '\'' + ", password='" + password + '\'' + '}';
    }
}
