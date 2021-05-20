package com.atguigu.springcloud.create_designmode.service.abstracts;

import java.io.IOException;
import java.nio.file.Path;

/**
* Word文档接口:
 * 上面的抽象工厂仅仅是一个接口，没有任何代码。同样的，因为HtmlDocument和WordDocument都比较复杂，
 * 现在我们并不知道如何实现它们，所以只有接口
*/

public interface WordDocument {
    void save(Path path) throws IOException;
}