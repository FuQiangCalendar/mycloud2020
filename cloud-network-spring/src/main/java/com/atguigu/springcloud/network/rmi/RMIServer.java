package com.atguigu.springcloud.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Description : 现在，服务器端的服务相关代码就编写完毕。我们需要通过Java RMI提供的一系列底层支持接口，
 * 把上面编写的服务以RMI的形式暴露在网络上，客户端才能调用：
 *
 * 下列代码主要目的是通过RMI提供的相关类，将我们自己的WorldClock实例注册到RMI服务上。
 * RMI的默认端口是1099，最后一步注册服务时通过rebind()指定服务名称为"WorldClock"
 * @Param
 * @return:
 * @Author:FuQiangCalendar
 * @Date: 2021/5/25 15:47
 */

public class RMIServer {
    public static void main(String[] args) throws RemoteException {
        System.out.println("create World clock remote service...");
        // 实例化一个WorldClock:
        WorldClock worldClock = new WorldClockService();
        // 将此服务转换为远程服务接口:
        WorldClock skeleton = (WorldClock) UnicastRemoteObject.exportObject(worldClock, 0);
        // 将RMI服务注册到1099端口:
        Registry registry = LocateRegistry.createRegistry(1099);
        // 注册此服务，服务名为"WorldClock":
        registry.rebind("WorldClock", skeleton);
    }
}

/**
 * @Description : 要实现RMI，服务器和客户端必须共享同一个接口。我们定义一个WorldClock接口，代码如下
 * @Param
 * @return:
 * @Author:FuQiangCalendar
 * @Date: 2021/5/25 15:45
 */
interface WorldClock extends Remote {
    LocalDateTime getLocalDateTime(String zoneId) throws RemoteException;
}

/**
 * @Description : Java的RMI规定此接口必须派生自java.rmi.Remote，并在每个方法声明抛出RemoteException。
 * 下一步是编写服务器的实现类，因为客户端请求的调用方法getLocalDateTime()最终会通过这个实现类返回结果。
 * 实现类WorldClockService代码如下
 * @Param
 * @return:
 * @Author:FuQiangCalendar
 * @Date: 2021/5/25 15:46
 */
class WorldClockService implements WorldClock {
    @Override
    public LocalDateTime getLocalDateTime(String zoneId) throws RemoteException {
        return LocalDateTime.now(ZoneId.of(zoneId)).withNano(0);
    }
}