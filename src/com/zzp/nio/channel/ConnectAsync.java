package com.zzp.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * Desc
 * Created by zzp
 * on 2016/9/5.10:01
 */
public class ConnectAsync {


    public static void main(String[] args) {
        String info = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<Message>" +
                "<Head>" +
                "<TrsCode>CX0001</TrsCode>" +
                "<ResTime>2016-09-05 14:38:52.229</ResTime>" +
                "</Head>" +
                "<Body>" +
                "<QryType>2</QryType>" +
                "<AcNo>6224480000390917</AcNo>" +
                "<ID>1473057532229</ID>" +
                "<Time>20160905143852</Time>" +
                "<Currency>CNY</Currency>" +
                "</Body>" +
                "</Message>\r\n";

        long time = new Date().getTime();
        long temp;
        while (true) {
            dosomingUseFul(info);
            temp = new Date().getTime();
            System.out.println(temp - time);
            time = temp;
        }
    }

    public static void dosomingUseFul(String info) {
        try {
            SocketChannel sc = SocketChannel.open();
            sc.configureBlocking(false);
            sc.connect(new InetSocketAddress("172.26.2.200", 16001));

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 4);


            byteBuffer.put(info.getBytes());

            while (!sc.finishConnect()) {

            }
            System.out.println("connected");
            byteBuffer.flip();
            sc.write(byteBuffer);


            ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024 * 4);
            long data = 0;
            while (data == 0) {
                data = sc.read(byteBuffer1);
                if (data != 0) {
                    byteBuffer1.flip();
                    System.out.println(new String(byteBuffer1.array()));
                }
            }
            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
