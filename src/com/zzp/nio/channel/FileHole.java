package com.zzp.nio.channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Desc 文件空洞
 * Created by zzp
 * on 2016/8/31.20:13
 */
public class FileHole {


    public static void main(String[] args) {
        File file = new File("D:\\tools\\down\\log\\test.log");

        try (
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                FileChannel channel = raf.getChannel();){
            ByteBuffer bb = ByteBuffer.allocate(100);
            putData(bb, channel, 0);
            putData(bb, channel, 5000000);
            putData(bb, channel, 5000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void putData(ByteBuffer bb, FileChannel fc, long position) throws IOException {
        String info = "*<--locatioin " + position;

        bb.clear();
        bb.put(info.getBytes());
        bb.flip();
        fc.position(position);
        fc.write(bb);
    }
}
