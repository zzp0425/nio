package com.zzp.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

/**
 * Desc
 * Created by zzp
 * on 2016/8/30.10:16
 */
public class FirstChannel {

    /**
     * 通道类
     * @param args
     */
    public static void main(String[] args) {
        Channel channel;
        WritableByteChannel writableByteChannel;
        ReadableByteChannel readableByteChannel;
        InterruptibleChannel interruptibleChannel;


        GatheringByteChannel gatheringByteChannel;
        ByteChannel byteChannel;
        ScatteringByteChannel scatteringByteChannel;
        AbstractInterruptibleChannel abstractInterruptibleChannel;

        SelectableChannel selectableChannel;
        FileChannel fileChannel;
        AbstractSelectableChannel abstractSelectableChannel;

        DatagramChannel datagramChannel;
        ServerSocketChannel serverSocketChannel;
        SocketChannel socketChannel;
    }

    /**
     * 打开一个通道
     */
    @Test
    public void openChannel() {
        try {
            //客户端
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("", 6666));

            //服务端
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(6666));

            DatagramChannel datagramChannel = DatagramChannel.open();

            RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\tools\\down\\log.txt", "r");
            FileChannel fileChannel = randomAccessFile.getChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void rwChannel() {
        ReadableByteChannel rb = Channels.newChannel(System.in);
        WritableByteChannel wb = Channels.newChannel(System.out);

        try {
            rb.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Channel copy method 1. This method copies data from the src
     * channel and writes it to the dest channel until EOF on src.
     * This implementation makes use of compact( ) on the temp buffer
     * to pack down the data if the buffer wasn't fully drained. This
     * may result in data copying, but minimizes system calls. It also
     * requires a cleanup loop to make sure all the data gets sent.
     */
    public void copyCompact(ReadableByteChannel rb, WritableByteChannel wb) {
        ByteBuffer bb = ByteBuffer.allocate(16 * 1024);

        try {
            while (rb.read(bb) != -1) {
                bb.flip();
                wb.write(bb);

                bb.compact();
            }
            bb.flip();
            while (bb.hasRemaining()) {
                wb.write(bb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void copyClear(ReadableByteChannel rb, WritableByteChannel wb) {
        ByteBuffer bb = ByteBuffer.allocate(16 * 1024);

        try {
            while (rb.read(bb) > -1) {
                bb.flip();
                while (bb.hasRemaining()) {
                    wb.write(bb);
                }
                bb.clear();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
