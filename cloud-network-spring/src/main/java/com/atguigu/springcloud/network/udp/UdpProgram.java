package com.atguigu.springcloud.network.udp;

/**
 * @Package com.atguigu.springcloud.network.udp
 * @ClassName UdpProgram
 * @Description UDP编程
 *
 * 和TCP编程相比，UDP编程就简单得多，因为UDP没有创建连接，数据包也是一次收发一个，所以没有流的概念。
 * 在Java中使用UDP编程，仍然需要使用Socket，因为应用程序在使用UDP时必须指定网络接口（IP）和端口号。注意：UDP端口和TCP端口虽然都使用0~65535，但他们是两套独立的端口，即一个应用程序用TCP占用了端口1234，不影响另一个应用程序用UDP占用端口1234。
 *
 * 小结：
 * 使用UDP协议通信时，服务器和客户端双方无需建立连接：
 * 1、服务器端用DatagramSocket(port)监听端口；
 * 2、客户端使用DatagramSocket.connect()指定远程地址和端口；
 * 3、双方通过receive()和send()读写数据；
 * 4、DatagramSocket没有IO流接口，数据被直接写入byte[]缓冲区。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/25 9:02
 * @Version 1.0
 **/
public class UdpProgram {

}
