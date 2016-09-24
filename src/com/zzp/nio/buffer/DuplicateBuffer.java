package com.zzp.nio.buffer;

import org.junit.Test;

import java.nio.CharBuffer;

/**
 * Desc
 * Created by zzp
 * on 2016/8/29.9:33
 */
public class DuplicateBuffer {

    @Test
    public void testDuplicateSliceBuffer() {
        //Dublicate
        CharBuffer cb = CharBuffer.allocate(100);
        cb.position(3).mark().limit(33).position(5);
        CharBuffer cbDublicate = cb.duplicate();
        FirstTest.outTitle();
        FirstTest.out(cbDublicate);


        //Slice
        CharBuffer cbSlice = cb.slice();
        FirstTest.out(cbSlice);
    }
}