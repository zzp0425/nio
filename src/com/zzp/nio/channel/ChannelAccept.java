package com.zzp.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Desc
 * Created by zzp
 * on 2016/9/4.21:08
 */
public class ChannelAccept {

    public static final String GREETING = "Hello I must be goning.\r\n";
    public static void main(String[] args) {
        int port = 10000;

        try {
            ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());


            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(port));
            ssc.configureBlocking(false);
            while (true) {
                SocketChannel sc = ssc.accept();
                if (sc == null) {
                    Thread.sleep(20000);
                } else {
                    System.out.println("incoming connection form " + sc.getRemoteAddress());

                    buffer.rewind();
                    sc.write(buffer);
                    sc.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
