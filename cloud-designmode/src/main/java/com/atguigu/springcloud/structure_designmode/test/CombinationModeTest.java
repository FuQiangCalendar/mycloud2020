package com.atguigu.springcloud.structure_designmode.test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;



/**
 * @Package com.atguigu.springcloud.structure_designmode.test
 * @ClassName CombinationModeTest
 * @Description 结果模型 -- 组合模型
 *
 * 将对象组合成树形结构以表示“部分-整体”的层次结构，使得用户对单个对象和组合对象的使用具有一致性。
 * 组合模式（Composite）经常用于树形结构，为了简化代码，使用Composite可以把一个叶子节点与一个父节点统一起来处理。
 * 我们来看一个具体的例子。在XML或HTML中，从根节点开始，每个节点都可能包含任意个其他节点，这些层层嵌套的节点就构成了一颗树。
 *
 * 小结：
 * Composite模式使得叶子对象和容器对象具有一致性，从而形成统一的树形结构，并用一致的方式去处理它们。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/21 13:42
 * @Version 1.0
 **/
@Slf4j
public class CombinationModeTest {

    public static void main(String[] args) {
        test1();
    }

    /**
    * @Description : 通过ElementNode、TextNode和CommentNode，我们就可以构造出一颗树：
     * 最后通过root节点输出的XML如下：
     *
     * <school>
     * <classA>
     * Tom
     * Alice
     * </classA>
     * <classB>
     * Bob
     * Grace
     * <!-- comment... -->
     * </classB>
     * </school>
     * 可见，使用Composite模式时，需要先统一单个节点以及“容器”节点的接口：
     *
     *              ┌───────────┐
     *              │   Node    │
     *              └───────────┘
     *                    ▲
     *       ┌────────────┼────────────┐
     *       │            │            │
     * ┌───────────┐┌───────────┐┌───────────┐
     * │ElementNode││ TextNode  ││CommentNode│
     * └───────────┘└───────────┘└───────────┘
     * 作为容器节点的ElementNode又可以添加任意个Node，这样就可以构成层级结构。
     * 类似的，像文件夹和文件、GUI窗口的各种组件，都符合Composite模式的定义，因为它们的结构天生就是层级结构
     *
    * @Param []
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/21 14:21
    */
    public static void test1 () {
        Node root = new ElementNode("school");
        root.add(new ElementNode("classA")
                .add(new TextNode("Tom"))
                .add(new TextNode("Alice")));
        root.add(new ElementNode("classB")
                .add(new TextNode("Bob"))
                .add(new TextNode("Grace"))
                .add(new CommentNode("comment...")));
        System.out.println(root.toXml());
    }
}

/**
* @Description : 要以树的结构表示XML，我们可以先抽象出节点类型Node
* @Author:FuQiangCalendar
* @Date: 2021/5/21 14:13
*/
interface Node {
    // 添加一个节点为子节点:
    Node add(Node node);
    // 获取子节点:
    List<Node> children();
    // 输出为XML:
    String toXml();
}

/**
* @Description : 对于一个<abc>这样的节点，我们称之为ElementNode，它可以作为容器包含多个子节点：
* @Author:FuQiangCalendar
* @Date: 2021/5/21 14:14
*/
class ElementNode implements Node {
    private String name;
    private List<Node> list = new ArrayList<>();

    public ElementNode(String name) {
        this.name = name;
    }

    public Node add(Node node) {
        list.add(node);
        return this;
    }

    public List<Node> children() {
        return list;
    }

    public String toXml() {
        String start = "<" + name + ">\n";
        String end = "</" + name + ">\n";
        StringJoiner sj = new StringJoiner("", start, end);
        list.forEach(node -> {
            sj.add(node.toXml() + "\n");
        });
        return sj.toString();
    }
}

/**
* @Description : 对于普通文本，我们把它看作TextNode，它没有子节点：
* @Author:FuQiangCalendar
* @Date: 2021/5/21 14:15
*/
class TextNode implements Node {
    private String text;

    public TextNode(String text) {
        this.text = text;
    }

    public Node add(Node node) {
        throw new UnsupportedOperationException();
    }

    public List<Node> children() {
        return null;
    }

    public String toXml() {
        return text;
    }
}

/**
* @Description : 此外，还可以有注释节点：
* @Author:FuQiangCalendar
* @Date: 2021/5/21 14:15
*/
class CommentNode implements Node {
    private String text;

    public CommentNode(String text) {
        this.text = text;
    }

    public Node add(Node node) {
        throw new UnsupportedOperationException();
    }

    public List<Node> children() {
        return null;
    }

    public String toXml() {
        return "<!-- " + text + " -->";
    }
}