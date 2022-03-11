package com.cjl.im.codec;

import java.util.HashMap;
import java.util.Map;

import com.cjl.im.dto.*;
import com.cjl.im.enums.Command;
import com.cjl.im.serialize.JSONSerializer;
import com.cjl.im.serialize.Serializer;

import io.netty.buffer.ByteBuf;

public class PacketCodec {
    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST.getValue(), LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE.getValue(), LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST.getValue(), MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE.getValue(), MessageResponsePacket.class);
//        packetTypeMap.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
//        packetTypeMap.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(Command.GROUP_REQUEST.getValue(), GroupRequestPacket.class);
        packetTypeMap.put(Command.GROUP_RESPONSE.getValue(), GroupResponsePacket.class);
//        packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
//        packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
//        packetTypeMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
//        packetTypeMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
//        packetTypeMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
//        packetTypeMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
//        packetTypeMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
//        packetTypeMap.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
//        packetTypeMap.put(HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
//        packetTypeMap.put(HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand().getValue());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
