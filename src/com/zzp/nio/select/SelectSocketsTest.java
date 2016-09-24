package com.zzp.nio.select;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Desc
 * Created by zzp
 * on 2016/9/9.16:59
 */
public class SelectSocketsTest {

    public static final int PORT = 1234;

    private static ByteBuffer buffer = ByteBuffer.allocate(1024);
    public static void main(String[] args) {
        go();
    }

    public static void go() {
        int port = PORT;
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(port)).configureBlocking(false);
            System.out.println("listing port " + port);
            Selector selector = Selector.open();

            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int count = selector.select();
                if (count == 0) {
                    continue;
                }

                Iterator iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = (SelectionKey) iterator.next();
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel socket = server.accept();

                        registChannel(socket, selector, SelectionKey.OP_READ);
                        sayHello(socket);

                    }
                    if (key.isReadable()) {
                        readDataFromSocket(key);
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 注册socket通道到selector
     * @param channel
     * @param selector
     * @param ops
     * @throws IOException
     */
    public static void registChannel(SocketChannel channel, Selector selector, int ops) throws IOException {

        if (channel == null) {
            return;
        }

        channel.configureBlocking(false);
        channel.register(selector, ops);
    }

    /**
     * 首次连接，发送hello字符串给客户端
     * @param channel
     * @throws IOException
     */
    public static void sayHello(SocketChannel channel) throws IOException {
        buffer.clear();
        buffer.put("hello\r\n".getBytes());

        buffer.flip();
        channel.write(buffer);
    }


    /**
     * 从socket中读取数据
     * @param key
     */
    public static void readDataFromSocket(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        int count;
        buffer.clear();
        while ((count = channel.read(buffer)) > 0)  {
            buffer.flip();

            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
            buffer.clear();
        }
        if (count < 0) {
            channel.close();
        }
    }
}
