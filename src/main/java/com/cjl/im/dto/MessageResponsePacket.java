package com.cjl.im.dto;

import com.cjl.im.enums.Command;

public class MessageResponsePacket extends Packet{
    private String userNameFrom;
    private String message;

    public String getUserNameFrom() {
        return userNameFrom;
    }

    public void setUserNameFrom(String userNameFrom) {
        this.userNameFrom = userNameFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Command getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
