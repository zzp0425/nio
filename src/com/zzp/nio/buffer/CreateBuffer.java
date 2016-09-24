package com.zzp.nio.buffer;

import org.junit.Test;

import java.nio.CharBuffer;

/**
 * Desc 创建缓冲区
 * Created by zzp
 * on 2016/8/28.12:14
 */
public class CreateBuffer {

    @Test
    public void createBuffer() {
        FirstTest.outTitle();
        CharBuffer charBuffer = CharBuffer.allocate(100);


        FirstTest.out(charBuffer);

        char[] mychars = new char[100];
        CharBuffer warpCharBuffer = CharBuffer.wrap(mychars);
        FirstTest.out(warpCharBuffer);

        //创建了一个 position 值为 10，limit 值为 52，容量为 mychars.length 的缓冲区。
        CharBuffer positionBuffer = CharBuffer.wrap(mychars, 10, 42);//缓冲区postion：10， limit: position + length : 10 + 42 = 52
        FirstTest.out(positionBuffer);
        System.out.println(positionBuffer.arrayOffset());
        positionBuffer.clear();
        FirstTest.out(positionBuffer);
        System.out.println(positionBuffer.arrayOffset());
    }
}
