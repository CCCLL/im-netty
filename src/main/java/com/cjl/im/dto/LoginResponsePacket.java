package com.cjl.im.dto;


import com.cjl.im.enums.Command;


public class LoginResponsePacket extends Packet {

    private boolean success;
    private String userName;
    private String reason;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "LoginResponsePacket{" + "success=" + success + ", userName='" + userName + '\'' + ", reason='" + reason + '\'' + '}';
    }

    @Override
    public Command getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
