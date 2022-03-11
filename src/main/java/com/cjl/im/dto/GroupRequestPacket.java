package com.cjl.im.dto;

import java.util.List;

import com.cjl.im.enums.Command;

public class GroupRequestPacket extends Packet{
    private String groupName;
    private List<String> userNames;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    @Override
    public Command getCommand() {
        return Command.GROUP_REQUEST;
    }
}
