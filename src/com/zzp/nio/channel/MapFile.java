package com.zzp.nio.channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Desc
 * Created by zzp
 * on 2016/9/3.13:41
 */
public class MapFile {

    public static void main(String[] args) throws IOException {
        //创建一个临时文件并获取文件通道
        File tempFile = File.createTempFile("test.log", null);
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        FileChannel channel = raf.getChannel();
        ByteBuffer temp = ByteBuffer.allocate(100);

        //在文件位置0处写入一些内容
        temp.put("This is the file content".getBytes());
        temp.flip();
        channel.write(temp, 0);

        //在文件位置8192位置处，再写入一些内容，8912大小是8k，
        //这会引起一些文件空洞，以来于文件协同的页大小
        temp.clear();
        temp.put("This is more file content".getBytes()).flip();
        channel.write(temp, 8192);

        //从同一文件创建三种类型的内存映射
        MappedByteBuffer ro = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        MappedByteBuffer rw = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
        MappedByteBuffer cow = channel.map(FileChannel.MapMode.PRIVATE, 0, channel.size());

        //对缓冲区做任何修改前的状态
        System.out.println("Begin");
        showBuffers(ro, rw, cow);

        //修改couy-on-write缓冲区
        cow.position(8);
        cow.put("COW".getBytes());
        System.out.println("Change to COW buffer");
        showBuffers(ro, rw, cow);

        //修改read/write缓冲区
        rw.position(9);
        rw.put("RW".getBytes());
        rw.position(8194);
        rw.put("R/W".getBytes());
        rw.force();
        System.out.println("Change to R/W buffer");
        showBuffers(ro, rw, cow);

        //通过通道写入文件，两个页都写
        temp.clear();
        temp.put("Channel write".getBytes());
        temp.flip();
        channel.write(temp, 0);
        temp.rewind();
        channel.write(temp, 8202);
        System.out.println("Write on Channel");
        showBuffers(ro, rw, cow);

        //再次修改copy-on-write缓冲区
        cow.position(8207);
        cow.put("COW2".getBytes());
        System.out.println("Second change to COW buffer");
        showBuffers(ro, rw, cow);

        //修改read/write缓冲区
        rw.position(0);
        rw.put(" R/W2".getBytes());
        rw.position(8210);
        rw.put(" R/W2".getBytes());
        rw.force();
        System.out.println("Second change to R/W buffer");
        showBuffers(ro, rw, cow);

        //关闭通道
        channel.close();
        raf.close();
        tempFile.delete();

    }


    public static void showBuffers(ByteBuffer ro, ByteBuffer rw, ByteBuffer cow) {
        dumpBuffer("R/O", ro);
        dumpBuffer("R/W", rw);
        dumpBuffer("COW", cow);
        System.out.println();
    }


    public static void dumpBuffer(String perfix, ByteBuffer buffer) {
        System.out.print(perfix + ": ");

        int limit = buffer.limit();
        int nulls = 0;

        for (int i = 0; i < limit; i++) {
            char c = (char) buffer.get(i);
            if (c == '\u0000') {
                nulls++;
                continue;
            }

            if (nulls != 0) {
                System.out.print("|[" + nulls + "nulls]|");
                nulls = 0;
            }
            System.out.print(c);
        }
        System.out.println();
    }
}
