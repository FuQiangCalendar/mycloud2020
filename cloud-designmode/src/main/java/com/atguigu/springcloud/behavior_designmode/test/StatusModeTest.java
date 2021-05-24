package com.atguigu.springcloud.behavior_designmode.test;

import java.util.Scanner;

/**
 * @Package com.atguigu.springcloud.behavior_designmode.test
 * @ClassName StatusModeTest
 * @Description 行为型模式 -- 状态
 *
 * 允许一个对象在其内部状态改变时改变它的行为。对象看起来似乎修改了它的类。
 * 状态模式（State）经常用在带有状态的对象中。
 *
 * 什么是状态？我们以QQ聊天为例，一个用户的QQ有几种状态：
 *
 * 离线状态（尚未登录）；
 * 正在登录状态；
 * 在线状态；
 * 忙状态（暂时离开）。
 *
 * 如何表示状态？我们定义一个enum就可以表示不同的状态。但不同的状态需要对应不同的行为，比如收到消息时：
 *
 * if (state == ONLINE) {
 *     // 闪烁图标
 * } else if (state == BUSY) {
 *     reply("现在忙，稍后回复");
 * } else if ...
 * 状态模式的目的是为了把上述一大串if...else...的逻辑给分拆到不同的状态类中，使得将来增加状态比较容易。
 *
 * 例如，我们设计一个聊天机器人，它有两个状态：
 *
 * 未连线；
 * 已连线。
 * 对于未连线状态，我们收到消息也不回复
 *
 * public class DisconnectedState implements State {
 *     public String init() {
 *         return "Bye!";
 *     }
 *
 *     public String reply(String input) {
 *         return "";
 *     }
 * }
 *
 * 对于已连线状态，我们回应收到的消息：
 *
 * public class ConnectedState implements State {
 *     public String init() {
 *         return "Hello, I'm Bob.";
 *     }
 *
 *     public String reply(String input) {
 *         if (input.endsWith("?")) {
 *             return "Yes. " + input.substring(0, input.length() - 1) + "!";
 *         }
 *         if (input.endsWith(".")) {
 *             return input.substring(0, input.length() - 1) + "!";
 *         }
 *         return input.substring(0, input.length() - 1) + "?";
 *     }
 * }
 *
 * 小结：
 * 状态模式的设计思想是把不同状态的逻辑分离到不同的状态类中，从而使得增加新状态更容易；
 * 状态模式的实现关键在于状态转换。简单的状态转换可以直接由调用方指定，复杂的状态转换可以在内部根据条件触发完成。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/21 17:29
 * @Version 1.0
 **/
public class StatusModeTest {

    public static void main(String[] args) {
        test1();
    }

    /**
    * 这样，一个价值千万的AI聊天机器人就诞生了
    * @Author:FuQiangCalendar
    * @Date: 2021/5/24 16:53
    */
    public static void test1 () {
        Scanner scanner = new Scanner(System.in);
        BotContext bot = new BotContext();
        for (;;) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String output = bot.chat(input);
            System.out.println(output.isEmpty() ? "(no reply)" : "< " + output);
        }
    }
}

interface State {
    String init();
    String reply(String input);
}

class DisconnectedState implements State {
    @Override
    public String init() {
        return "Bye!";
    }

    @Override
    public String reply(String input) {
        return "";
    }
}

class ConnectedState implements State {
    @Override
    public String init() {
        return "Hello, I'm Bob.";
    }

    @Override
    public String reply(String input) {
        if (input.endsWith("?")) {
            return "Yes. " + input.substring(0, input.length() - 1) + "!";
        }
        if (input.endsWith(".")) {
            return input.substring(0, input.length() - 1) + "!";
        }
        return input.substring(0, input.length() - 1) + "?";
    }
}

/**
* 状态模式的关键设计思想在于状态切换，我们引入一个BotContext完成状态切换
* @Author:FuQiangCalendar
* @Date: 2021/5/24 16:49
*/
class BotContext {
    private State state = new DisconnectedState();

    public String chat(String input) {
        if ("hello".equalsIgnoreCase(input)) {
            // 收到hello切换到在线状态:
            state = new ConnectedState();
            return state.init();
        } else if ("bye".equalsIgnoreCase(input)) {
            //  收到bye切换到离线状态:
            state = new DisconnectedState();
            return state.init();
        }
        return state.reply(input);
    }
}

