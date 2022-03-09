package com.cjl.im.client.instruction;

import java.util.*;

import io.netty.channel.Channel;

public interface Instruction {
    void execute(Scanner scanner, Channel channel);
}
