package com.oltocoder.boot.component.core.message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//todo
public class MessageType {
    //事件消息
//    READ_PROPERTY(ReadPropertyMessage::new, DefaultReadPropertyMessage::new),
//    READ_PROPERTY_REPLY(ReadPropertyMessageReply::new, DefaultReadPropertyMessageReply::new);
//    final Supplier<? extends Message> deviceInstance;
//    final Supplier<? extends Message> thingInstance;

//    private static final Map<String, MessageType> mapping;
//
//    static {
//        mapping = new HashMap<>();
//        for (MessageType value : values()) {
//            mapping.put(value.name().toLowerCase(), value);
//            mapping.put(value.name().toUpperCase(), value);
//        }
//    }

    public static Optional<MessageType> of(Map<String, Object> map) {
//        if (map.containsKey("event")) {
//            return Optional.of(EVENT);
//        }

//        if (map.containsKey("properties")) {
//            Object properties = map.get("properties");
//            return properties instanceof Collection ? Optional.of(READ_PROPERTY) : Optional.of(READ_PROPERTY_REPLY);
//        }

        return Optional.of(null);
    }
}
