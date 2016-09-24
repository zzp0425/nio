package com.zzp.nio.buffer;

import org.junit.Test;

import java.nio.CharBuffer;

/**
 * Desc 释放缓冲区
 * Created by zzp
 * on 2016/8/24.19:47
 */
public class CompactTest {

    @Test
    public void compactTest() {
        CharBuffer buffer = CharBuffer.allocate(10);

        String info = "hello";

        for (int i = 0; i < info.length(); i++) {
            buffer.put(info.charAt(i));
        }

//        buffer.flip();
//        while (buffer.hasRemaining()){
//            System.out.print(buffer.get());
//        }

        buffer.compact().flip();
        System.out.println();
        FirstTest.outTitle();
        FirstTest.out(buffer);
    }
}
