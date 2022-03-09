package com.cjl.im.server.session;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class SessionUtil {

    public static final SessionUtil INSTANCE = new SessionUtil();

    public static final AttributeKey ATTRIBUTE_KEY = AttributeKey.valueOf("session");

    private Map<String, Channel> sessionCache = new HashMap<>();

    public Channel putSession(String userName, Channel channel) {
        return sessionCache.put(userName, channel);
    }

    public Channel getChannel(String userName) {
        return sessionCache.get(userName);
    }

    public boolean hasLogin(Channel channel) {
        return  channel.hasAttr(ATTRIBUTE_KEY);
    }

}
