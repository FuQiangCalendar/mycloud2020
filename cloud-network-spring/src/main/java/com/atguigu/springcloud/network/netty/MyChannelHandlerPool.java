package com.atguigu.springcloud.network.netty;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
* @Name MyChannelHandlerPool
* @Description 通道组池，管理所有websocket连接
* @Author qfu1
* @Date 2022-08-26 14:48
*/
public class MyChannelHandlerPool {

    public MyChannelHandlerPool(){}

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}