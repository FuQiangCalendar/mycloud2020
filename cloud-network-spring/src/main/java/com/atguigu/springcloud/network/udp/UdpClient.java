package com.atguigu.springcloud.network.udp;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;

/**
 * @Package com.atguigu.springcloud.network.udp
 * @ClassName UdpClient
 * @Description UDP 客户端
 *
 * 客户端
 * 和服务器端相比，客户端使用UDP时，只需要直接向服务器端发送UDP包，然后接收返回的UDP包
 *
 * 客户端打开一个DatagramSocket使用以下代码：
 *
 * DatagramSocket ds = new DatagramSocket();
 * ds.setSoTimeout(1000);
 * ds.connect(InetAddress.getByName("localhost"), 6666);
 * 客户端创建DatagramSocket实例时并不需要指定端口，而是由操作系统自动指定一个当前未使用的端口。紧接着，调用setSoTimeout(1000)设定超时1秒，意思是后续接收UDP包时，等待时间最多不会超过1秒，否则在没有收到UDP包时，客户端会无限等待下去。这一点和服务器端不一样，服务器端可以无限等待，因为它本来就被设计成长时间运行。
 *
 * 注意到客户端的DatagramSocket还调用了一个connect()方法“连接”到指定的服务器端。不是说UDP是无连接的协议吗？为啥这里需要connect()？
 * 这个connect()方法不是真连接，它是为了在客户端的DatagramSocket实例中保存服务器端的IP和端口号，确保这个DatagramSocket实例只能往指定的地址和端口发送UDP包，不能往其他地址和端口发送。这么做不是UDP的限制，而是Java内置了安全检查。
 *
 * 如果客户端希望向两个不同的服务器发送UDP包，那么它必须创建两个DatagramSocket实例。
 * 后续的收发数据和服务器端是一致的。通常来说，客户端必须先发UDP包，因为客户端不发UDP包，服务器端就根本不知道客户端的地址和端口号。
 *
 * 如果客户端认为通信结束，就可以调用disconnect()断开连接：
 * ds.disconnect();
 * 注意到disconnect()也不是真正地断开连接，它只是清除了客户端DatagramSocket实例记录的远程服务器地址和端口号，这样，DatagramSocket实例就可以连接另一个服务器端。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/25 9:08
 * @Version 1.0
 **/
@Slf4j
public class UdpClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        ds.setSoTimeout(1000);
        ds.connect(InetAddress.getByName("localhost"), 6666); // 连接指定服务器和端口
        // 发送:
        byte[] data = "Hello".getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length);
        ds.send(packet);
        // 接收:
        byte[] buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        ds.receive(packet);
        String resp = new String(packet.getData(), packet.getOffset(), packet.getLength());
        log.info("UdpClient接受到的消息>>" + resp);
        ds.disconnect();
    }
}
