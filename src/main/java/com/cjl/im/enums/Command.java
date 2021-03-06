package com.cjl.im.enums;

public enum Command {
    LOGIN_REQUEST(1), LOGIN_RESPONSE(2), MESSAGE_REQUEST(3), MESSAGE_RESPONSE(4), GROUP_REQUEST(5), GROUP_RESPONSE(6);

    private final Byte value;

    Command(int value) {
        this.value = (byte) value;
    }

    public Byte getValue() {
        return value;
    }
}
