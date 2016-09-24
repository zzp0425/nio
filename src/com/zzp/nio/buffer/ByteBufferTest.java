package com.zzp.nio.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Desc
 * Created by zzp
 * on 2016/8/29.11:06
 */
public class ByteBufferTest {

    @Test
    public void toCharBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(7);
        CharBuffer cb = byteBuffer.asCharBuffer();

        byteBuffer.put (0, (byte)0);
        byteBuffer.put (1, (byte)'H');
        byteBuffer.put (2, (byte)0);
        byteBuffer.put (3, (byte)'i');
        byteBuffer.put (4, (byte)0);
        byteBuffer.put (5, (byte)'!');
        byteBuffer.put (6, (byte)0);

        FirstTest.outTitle();
        FirstTest.out(byteBuffer);
        System.out.print(": " + byteBuffer.toString() + "\r\n");
        FirstTest.out(cb);
        System.out.print(": " + cb.toString() + "\r\n");


    }
}
