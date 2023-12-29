package com.oltocoder.boot.component.core.metadata.codec;

import com.oltocoder.boot.component.core.metadata.ThingMetadata;
import reactor.core.publisher.Mono;

// 物模型编解码器
public interface ThingMetadataCodec {

    //
    Mono<ThingMetadata> decode(String source);


    <T extends ThingMetadata> Mono<String> encode(T metadata);

}
