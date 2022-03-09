package com.cjl.im.client.instruction;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import io.netty.channel.Channel;

public class InstructionManager {
    public static final InstructionManager INSTANCE = new InstructionManager();
    private static final Map<String, Instruction> instructions = new HashMap<>();

    static {
        instructions.put(LoginInstruction.INSTRUCTION_SYMBOL, new LoginInstruction());
        instructions.put(SendInstruction.INSTRUCTION_SYMBOL, new SendInstruction());
    }

    public void handler(Scanner scanner, String instructionSymbol, Channel channel) {
        if (!instructions.containsKey(instructionSymbol)){
            System.out.println("不存在此指令!");
            return;
        }
        Instruction instruction = instructions.get(instructionSymbol);
        instruction.execute(scanner,channel);
    }

}
