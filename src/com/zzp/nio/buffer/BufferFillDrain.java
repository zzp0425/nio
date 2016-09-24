package com.zzp.nio.buffer;

import java.nio.CharBuffer;

/**
 * Desc 填充、释放缓冲区
 * Created by zzp
 * on 2016/8/24.19:25
 */
public class BufferFillDrain {
    private static int index = 0;
    private static String [] strings = {
            "A random string value",
            "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees",
            "Opening act for the Monkees: Jimi Hendrix",
            "'Scuse me while I kiss this fly", // Sorry Jimi ;-)
            "Help Me! Help Me!",
    };
    
    public static void drainBuffer(CharBuffer buffer) {
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println();
    }
    public static boolean fillBuffer(CharBuffer buffer) {
        if (index >= strings.length) {
            return false;
        }

        String info = strings[index++];

        if (buffer.capacity() < info.length()) {
            return false;
        }
        for (int i = 0; i < info.length(); i++) {
            buffer.put(info.charAt(i));
        }
        
        return true;
    }

    public static void main(String[] args) {
        CharBuffer buffer = CharBuffer.allocate(22);

        while (fillBuffer(buffer)) {
            buffer.flip();
            drainBuffer(buffer);
            buffer.clear();
        }
    }
}
