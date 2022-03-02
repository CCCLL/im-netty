package com.cjl.im.dto;


import com.cjl.im.enums.Command;



public abstract class Packet {

    private Byte version = 1;

    public abstract Command getCommand();

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }
}
