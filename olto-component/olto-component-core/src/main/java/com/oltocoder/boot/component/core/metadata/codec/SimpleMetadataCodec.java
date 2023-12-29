package com.oltocoder.boot.component.core.metadata.codec;

import com.oltocoder.boot.component.core.metadata.ThingMetadata;
import com.oltocoder.boot.component.core.metadata.supports.SimpleThingMetadata;
import com.alibaba.fastjson.JSON;
import reactor.core.publisher.Mono;

public class SimpleMetadataCodec implements ThingMetadataCodec {

    private static final SimpleMetadataCodec INSTANCE = new SimpleMetadataCodec();

    public static SimpleMetadataCodec getInstance() {
        return INSTANCE;
    }
    public ThingMetadata doDecode(String json) {
        return new SimpleThingMetadata(JSON.parseObject(json));
    }

    @Override
    public Mono<ThingMetadata> decode(String source) {
        return Mono.just(doDecode(source));
    }

    @Override
    public Mono<String> encode(ThingMetadata metadata) {
        return Mono.just(doEncode(metadata));
    }

    private String doEncode(ThingMetadata metadata) {
        return new SimpleThingMetadata(metadata).toJson().toJSONString();
    }
}
