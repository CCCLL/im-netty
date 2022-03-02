package com.cjl.im.dto;

import com.cjl.im.enums.Command;

public class MessageRequestPacket extends Packet {
    private String userNameFrom;
    private String userNameTo;
    private String message;



    public String getUserNameFrom() {
        return userNameFrom;
    }

    public void setUserNameFrom(String userNameFrom) {
        this.userNameFrom = userNameFrom;
    }

    public String getUserNameTo() {
        return userNameTo;
    }

    public void setUserNameTo(String userNameTo) {
        this.userNameTo = userNameTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageRequestPacket{" + "userNameFrom='" + userNameFrom + '\'' + ", userNameTo='" + userNameTo + '\'' + ", message='" + message + '\'' + '}';
    }

    @Override
    public Command getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
