package com.oltocoder.boot.component.core.util.radomId;

import io.netty.util.concurrent.FastThreadLocal;

import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

public class RandomIdUtils {
    private final static byte instanceId = Integer.getInteger("generator.random.instance-id", ThreadLocalRandom.current().nextInt(1, 127)).byteValue();
    static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private final static FastThreadLocal<byte[]> HOLDER = new FastThreadLocal<byte[]>() {
        @Override
        protected byte[] initialValue() {
            return new byte[24];
        }
    };

    public static String generate() {
        long now = System.currentTimeMillis();
        byte[] value = HOLDER.get();
        value[0] = instanceId;

        value[1] = (byte) (now >>> 32);
        value[2] = (byte) (now >>> 24);
        value[3] = (byte) (now >>> 16);
        value[4] = (byte) (now >>> 8);
        value[5] = (byte) (now);

        nextBytes(value, 6, 8);
        nextBytes(value, 8, 16);
        nextBytes(value, 16, 24);
        return encoder.encodeToString(value);
    }

    private static void nextBytes(byte[] bytes, int offset, int len) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = offset; i < len; ) {
            for (int rnd = random.nextInt(),
                 n = Math.min(len - i, Integer.SIZE / Byte.SIZE);
                 n-- > 0; rnd >>= Byte.SIZE) {
                bytes[i++] = (byte) rnd;
            }
        }

    }
}
