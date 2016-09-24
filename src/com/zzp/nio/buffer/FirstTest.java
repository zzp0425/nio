package com.zzp.nio.buffer;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Desc  缓冲区基础api测试
 * Created by zzp
 * on 2016/8/24.14:39
 */
public class FirstTest {

    @Test
    public void test1() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        outTitle();
        out(buffer);
        buffer.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o');
        out(buffer);
        buffer.put(0, (byte) 'M').put((byte) 'w');
        out(buffer);
        buffer.flip();
//        buffer.limit(buffer.position()).position(0);
        out(buffer);
        buffer.clear();
        out(buffer);
//        buffer.limit(buffer.position()).position(0);
        byte[] bytes = new byte[10];

        for (int i = 0; buffer.hasRemaining(); i++) {
            bytes[i] = buffer.get();
        }

        for (int i = 0; i < buffer.remaining(); i++) {
            bytes[i] = buffer.get();
        }

    }

    public static void out(Buffer buffer) {

        System.out.println(buffer.limit() + " | " + buffer.capacity() + " | " + buffer.position());
    }

    public static void outTitle() {
        System.out.println("limit | capacity | position");
        System.out.println("--- | --- | ---");
    }
}
