package com.atguigu.springcloud.behavior_designmode.test;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.atguigu.springcloud.behavior_designmode.test
 * @ClassName TemplateMethodModeTest
 * @Description 行为型模式 -- 模板方法
 *
 * 定义一个操作中的算法的骨架，而将一些步骤延迟到子类中，使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
 * 模板方法（Template Method）是一个比较简单的模式。它的主要思想是，定义一个操作的一系列步骤，对于某些暂时确定不下来的步骤，就留给子类去实现好了，这样不同的子类就可以定义出不同的步骤。
 * 因此，模板方法的核心在于定义一个“骨架”。我们还是举例说明。
 *
 * 客户端代码使用本地缓存的代码这么写：
 *
 * AbstractSetting setting1 = new LocalSetting();
 * System.out.println("test = " + setting1.getSetting("test"));
 * System.out.println("test = " + setting1.getSetting("test"));
 * 要改成Redis缓存，只需要把LocalSetting替换为RedisSetting：
 *
 * AbstractSetting setting2 = new RedisSetting();
 * System.out.println("autosave = " + setting2.getSetting("autosave"));
 * System.out.println("autosave = " + setting2.getSetting("autosave"));
 * 可见，模板方法的核心思想是：父类定义骨架，子类实现某些细节。
 *
 * 为了防止子类重写父类的骨架方法，可以在父类中对骨架方法使用final。对于需要子类实现的抽象方法，一般声明为protected，使得这些方法对外部客户端不可见。
 *
 * Java标准库也有很多模板方法的应用。在集合类中，AbstractList和AbstractQueuedSynchronizer都定义了很多通用操作，子类只需要实现某些必要方法。
 *
 * 练习
 * 使用模板方法增加一个使用Guava Cache的子类。
 *
 * 思考：能否将readFromDatabase()作为模板方法，使得子类可以选择从数据库读取还是从文件读取。
 * 再思考如果既可以扩展缓存，又可以扩展底层存储，会不会出现子类数量爆炸的情况？如何解决？
 *
 * 小结：
 * 模板方法是一种高层定义骨架，底层实现细节的设计模式，适用于流程固定，但某些步骤不确定或可替换的情况。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/21 17:30
 * @Version 1.0
 **/
@Slf4j
public class TemplateMethodModeTest {

}

/**
* 数据库读取设置的类
* @Author:FuQiangCalendar
* @Date: 2021/5/24 17:14
*/
class Setting1 {
    public final String getSetting(String key) {
        String value = readFromDatabase(key);
        return value;
    }

    private String readFromDatabase(String key) {
        // TODO: 从数据库读取
        return null;
    }
}

/**
* 由于从数据库读取数据较慢，我们可以考虑把读取的设置缓存起来，这样下一次读取同样的key就不必再访问数据库了。
 * 但是怎么实现缓存，暂时没想好，但不妨碍我们先写出使用缓存的代码：
 * 整个流程没有问题，但是，lookupCache(key)和putIntoCache(key, value)这两个方法还根本没实现，怎么编译通过？
 * 这个不要紧，我们声明抽象方法就可以，见AbstractSetting
* @Author:FuQiangCalendar
* @Date: 2021/5/24 17:15
*/
class Setting2 {
    public final String getSetting(String key) {
        // 先从缓存读取:
        /*String value = lookupCache(key);
        if (value == null) {
            // 在缓存中未找到,从数据库读取:
            value = readFromDatabase(key);
            System.out.println("[DEBUG] load from db: " + key + " = " + value);
            // 放入缓存:
            putIntoCache(key, value);
        } else {
            System.out.println("[DEBUG] load from cache: " + key + " = " + value);
        }
        return value;*/
        return  null;
    }
}

/**
* 因为声明了抽象方法，自然整个类也必须是抽象类。如何实现lookupCache(key)和putIntoCache(key, value)这两个方法就交给子类了。
 * 子类其实并不关心核心代码getSetting(key)的逻辑，它只需要关心如何完成两个小小的子任务就可以了。
* @Author:FuQiangCalendar
* @Date: 2021/5/24 17:20
*/
abstract class AbstractSetting {
    //从数据库读
    String readFromDatabase (String key) {
        return "";
    }

    public final String getSetting(String key) {
        String value = lookupCache(key);
        if (value == null) {
            value = readFromDatabase(key);
            putIntoCache(key, value);
        }
        return value;
    }

    protected abstract String lookupCache(String key);

    protected abstract void putIntoCache(String key, String value);
}

/**
* 本地用Map做缓存
* @Author:FuQiangCalendar
* @Date: 2021/5/24 17:26
*/
class LocalSetting extends AbstractSetting {
    private Map<String, String> cache = new HashMap<>();

    protected String lookupCache(String key) {
        return cache.get(key);
    }

    protected void putIntoCache(String key, String value) {
        cache.put(key, value);
    }
}

/**
* Redis 做缓存
* @Author:FuQiangCalendar
* @Date: 2021/5/24 17:27
*/
class RedisSetting extends AbstractSetting {
    private RedisClient client = RedisClient.create("redis://localhost:6379");

    protected String lookupCache(String key) {
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisCommands<String, String> commands = connection.sync();
            return commands.get(key);
        }
    }

    protected void putIntoCache(String key, String value) {
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisCommands<String, String> commands = connection.sync();
            commands.set(key, value);
        }
    }
}