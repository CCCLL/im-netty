package com.cjl.im.enums;

public enum SerializerAlgorithm {
    JSON(1);
    private final Byte value;

    SerializerAlgorithm(int value) {
        this.value = (byte) value;
    }

    public Byte getValue() {
        return value;
    }
}
