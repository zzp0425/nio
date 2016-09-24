package com.zzp.nio.regex;

import org.junit.Test;

import java.nio.CharBuffer;

/**
 * Desc
 * Created by zzp
 * on 2016/8/25.14:59
 */
public class CharSeqTest {

    @Test
    public void testCharSeq() {
        StringBuffer sb = new StringBuffer("Hello World");
        CharBuffer cb = CharBuffer.allocate(20);
        CharSequence cs = "Hello World";
        printCharSequence(cs);

        cs = sb;
        printCharSequence(cs);

        sb.setLength(0);
        sb.append("World Hello");

        printCharSequence(cs);

        cs = cb;
        cb.put("XXXXXXXXXXXXXXXXXXXX"); // limit = 20; mark = -1; position = 20;
        cb.clear();                     // limit = 20; mark = -1; position = 0;
        cb.put("Hello World");          // limit = 20; mark = -1; position = 11;
        cb.flip();                      // limit = 11; mark = -1; position = 0;
        printCharSequence(cs);


        cb.mark();                      // limit = 11; mark = position = 0;
        cb.put("seeya");                // limit = 11; mark = 0; position = 5;
        cb.reset();                     // limit = 11; mark = position = 0;
        printCharSequence(cs);

        cb.clear();                     // limit = 20; mark = -1; position = 0;
        printCharSequence(cs);
    }


    private static void printCharSequence (CharSequence cs) {
        System.out.println ("length=" + cs.length( )
                + ", content='" + cs.toString( ) + "'");
    }

}
