package com.atguigu.springcloud.network.udp;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

/**
 * @Package com.atguigu.springcloud.network.udp
 * @ClassName UdpServer
 * @Description UDP服务端
 *
 * 服务器端
 * 在服务器端，使用UDP也需要监听指定的端口。Java提供了DatagramSocket来实现这个功能，代码如下：
 *
 * 服务器端首先使用如下语句在指定的端口监听UDP数据包：
 *
 * DatagramSocket ds = new DatagramSocket(6666);
 * 如果没有其他应用程序占据这个端口，那么监听成功，我们就使用一个无限循环来处理收到的UDP数据包：
 *
 * for (;;) {
 *     ...
 * }
 * 要接收一个UDP数据包，需要准备一个byte[]缓冲区，并通过DatagramPacket实现接收：
 *
 * byte[] buffer = new byte[1024];
 * DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
 * ds.receive(packet);
 * 假设我们收取到的是一个String，那么，通过DatagramPacket返回的packet.getOffset()和packet.getLength()确定数据在缓冲区的起止位置：
 *
 * String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
 * 当服务器收到一个DatagramPacket后，通常必须立刻回复一个或多个UDP包，因为客户端地址在DatagramPacket中，每次收到的DatagramPacket可能是不同的客户端，如果不回复，客户端就收不到任何UDP包。
 *
 * 发送UDP包也是通过DatagramPacket实现的，发送代码非常简单：
 *
 * byte[] data = ...
 * packet.setData(data);
 * ds.send(packet);
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/25 9:04
 * @Version 1.0
 **/
@Slf4j
public class UdpServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(6666); // 监听指定端口
        for (;;) { // 无限循环
            // 数据缓冲区:
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            ds.receive(packet); // 收取一个UDP数据包
            // 收取到的数据存储在buffer中，由packet.getOffset(), packet.getLength()指定起始位置和长度
            // 将其按UTF-8编码转换为String:
            String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
            log.info("UdpServer接受的消息>>" + s);
            // 发送数据:
            byte[] data = "ACK".getBytes(StandardCharsets.UTF_8);
            packet.setData(data);
            ds.send(packet);
        }
    }
}
