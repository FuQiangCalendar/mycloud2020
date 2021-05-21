package com.atguigu.springcloud.structure_designmode.test;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * @Package com.atguigu.springcloud.structure_designmode.test
 * @ClassName ProxyModeTest
 * @Description 结构模型 -- 代理
 * 为其他对象提供一种代理以控制对这个对象的访问。
 * 代理模式，即Proxy，它和Adapter模式很类似。我们先回顾Adapter模式，它用于把A接口转换为B接口：
 *
 * public BAdapter implements B {
 *     private A a;
 *     public BAdapter(A a) {
 *         this.a = a;
 *     }
 *     public void b() {
 *         a.a();
 *     }
 * }
 * 而Proxy模式不是把A接口转换成B接口，它还是转换成A接口：
 *
 * public AProxy implements A {
 *     private A a;
 *     public AProxy(A a) {
 *         this.a = a;
 *     }
 *     public void a() {
 *         this.a.a();
 *     }
 * }
 * 合着Proxy就是为了给A接口再包一层，这不是脱了裤子放屁吗？
 * 当然不是。我们观察Proxy的实现A接口的方法：
 * public void a() {
 *     this.a.a();
 * }
 * 这样写当然没啥卵用。但是，如果我们在调用a.a()的前后，加一些额外的代码：
 *
 * public void a() {
 *     if (getCurrentUser().isRoot()) {
 *         this.a.a();
 *     } else {
 *         throw new SecurityException("Forbidden");
 *     }
 * }
 * 这样一来，我们就实现了权限检查，只有符合要求的用户，才会真正调用目标方法，否则，会直接抛出异常。
 * 有的童鞋会问，为啥不把权限检查的功能直接写到目标实例A的内部？
 * 因为我们编写代码的原则有：
 *
 * 职责清晰：一个类只负责一件事；
 * 易于测试：一次只测一个功能。
 * 用Proxy实现这个权限检查，我们可以获得更清晰、更简洁的代码：
 *
 * A接口：只定义接口；
 * ABusiness类：只实现A接口的业务逻辑；
 * APermissionProxy类：只实现A接口的权限检查代理。
 * 如果我们希望编写其他类型的代理，可以继续增加类似ALogProxy，而不必对现有的A接口、ABusiness类进行修改。
 *
 * 实际上权限检查只是代理模式的一种应用。Proxy还广泛应用在：
 * 远程代理
 * 远程代理即Remote Proxy，本地的调用者持有的接口实际上是一个代理，这个代理负责把对接口的方法访问转换成远程调用，然后返回结果。
 * Java内置的RMI机制就是一个完整的远程代理模式。
 *
 * 虚代理
 * 虚代理即Virtual Proxy，它让调用者先持有一个代理对象，但真正的对象尚未创建。
 * 如果没有必要，这个真正的对象是不会被创建的，直到客户端需要真的必须调用时，才创建真正的对象。
 * JDBC的连接池返回的JDBC连接（Connection对象）就可以是一个虚代理，即获取连接时根本没有任何实际的数据库连接，
 * 直到第一次执行JDBC查询或更新操作时，才真正创建实际的JDBC连接。
 *
 * 保护代理
 * 保护代理即Protection Proxy，它用代理对象控制对原始对象的访问，常用于鉴权。
 *
 * 智能引用
 * 智能引用即Smart Reference，它也是一种代理对象，如果有很多客户端对它进行访问，
 * 通过内部的计数器可以在外部调用者都不使用后自动释放它。
 *
 * 小结：
 * 代理模式通过封装一个已有接口，并向调用方返回相同的接口类型，能让调用方在不改变任何代码的前提下增强某些功能（例如，鉴权、延迟加载、连接池复用等）。
 * 使用Proxy模式要求调用方持有接口，作为Proxy的类也必须实现相同的接口类型。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/21 13:45
 * @Version 1.0
 **/
@Slf4j
public class ProxyModeTest {

    public static void main(String[] args) throws SQLException {
//        test1();
        test2();
    }

    /**
    * @Description : 我们来看一下如何应用代理模式编写一个JDBC连接池（DataSource）。
     * 我们首先来编写一个虚代理，即如果调用者获取到Connection后，并没有执行任何SQL操作，
     * 那么这个Connection Proxy实际上并不会真正打开JDBC连接。调用者代码如下：
     *
    *   可见第一个getConnection()调用获取到的LazyConnectionProxy并没有实际打开真正的JDBC Connection。
     * 使用连接池的时候，我们更希望能重复使用连接。如果调用方编写这样的代码：
    * @Param []
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/21 16:15
    */
    public static void test1 () throws SQLException {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/shunchao?characterEncoding=UTF-8&useUnicode=true&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8";
        String jdbcUsername = "root";
        String jdbcPassword = "root";

        DataSource lazyDataSource = new LazyDataSource(jdbcUrl, jdbcUsername, jdbcPassword);
        System.out.println("get lazy connection...");
        try (Connection conn1 = lazyDataSource.getConnection()) {
            // 并没有实际打开真正的Connection
        }
        System.out.println("get lazy connection...");
        try (Connection conn2 = lazyDataSource.getConnection()) {
            try (PreparedStatement ps = conn2.prepareStatement("SELECT * FROM sys_user ")) { // 打开了真正的Connection
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(rs.getString("realname"));
                    }
                }
            }
        }
    }

    /**
    * @Description : 使用连接池的时候，我们更希望能重复使用连接。如果调用方编写这样的代码：
    *
     * 除了第一次打开了一个真正的JDBC Connection，后续获取的Connection实际上是同一个JDBC Connection。但是，对于调用方来说，完全不需要知道底层做了哪些优化。
     * 我们实际使用的DataSource，例如HikariCP，都是基于代理模式实现的，原理同上，但增加了更多的如动态伸缩的功能（一个连接空闲一段时间后自动关闭）。
     * 有的童鞋会发现Proxy模式和Decorator模式有些类似。确实，这两者看起来很像，
     * 但区别在于：Decorator模式让调用者自己创建核心类，然后组合各种功能，
     * 而Proxy模式决不能让调用者自己创建再组合，否则就失去了代理的功能。
     * Proxy模式让调用者认为获取到的是核心类接口，但实际上是代理类。
     *
    * @Param []
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/21 16:58
    */
    public static void test2 () throws SQLException {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/shunchao?characterEncoding=UTF-8&useUnicode=true&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8";
        String jdbcUsername = "root";
        String jdbcPassword = "root";
        DataSource pooledDataSource = new PooledDataSource(jdbcUrl, jdbcUsername, jdbcPassword);
        try (Connection conn = pooledDataSource.getConnection()) {
        }
        try (Connection conn = pooledDataSource.getConnection()) {
            // 获取到的是同一个Connection
        }
        try (Connection conn = pooledDataSource.getConnection()) {
            // 获取到的是同一个Connection
        }
    }
}

/**
* @Description : 现在我们来思考如何实现这个LazyConnectionProxy。为了简化代码，我们首先针对Connection接口做一个抽象的代理类：
* @Author:FuQiangCalendar
* @Date: 2021/5/21 16:18
*/
abstract class AbstractConnectionProxy implements Connection {

    // 抽象方法获取实际的Connection:
    protected abstract Connection getRealConnection();

    // 实现Connection接口的每一个方法:
    @Override
    public Statement createStatement() throws SQLException {
        return getRealConnection().createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return getRealConnection().prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return getRealConnection().prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return getRealConnection().nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        getRealConnection().setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return getRealConnection().getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        getRealConnection().commit();
    }

    @Override
    public void rollback() throws SQLException {
        getRealConnection().rollback();
    }

    @Override
    public void close() throws SQLException {
        getRealConnection().close();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return getRealConnection().isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return getRealConnection().getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        getRealConnection().setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return getRealConnection().isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        getRealConnection().setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        return getRealConnection().getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        getRealConnection().setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return getRealConnection().getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return getRealConnection().getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        getRealConnection().clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return getRealConnection().createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return getRealConnection().prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return getRealConnection().prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return getRealConnection().getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        getRealConnection().setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        getRealConnection().setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
        return getRealConnection().getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return getRealConnection().setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return getRealConnection().setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        getRealConnection().rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        getRealConnection().releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return getRealConnection().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return getRealConnection().prepareStatement(sql,resultSetType, resultSetConcurrency,resultSetHoldability);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return getRealConnection().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return getRealConnection().prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return getRealConnection().prepareStatement(sql, columnIndexes);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return getRealConnection().prepareStatement(sql, columnNames);
    }

    @Override
    public Clob createClob() throws SQLException {
        return getRealConnection().createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        return getRealConnection().createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return getRealConnection().createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return getRealConnection().isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        getRealConnection().setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        getRealConnection().setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return getRealConnection().getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return getRealConnection().createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return getRealConnection().createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        getRealConnection().setSchema(schema);
    }

    @Override
    public String getSchema() throws SQLException {
        return getRealConnection().getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        getRealConnection().abort(executor);
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        getRealConnection().setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return getRealConnection().getNetworkTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getRealConnection().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getRealConnection().isWrapperFor(iface);
    }
}

class LazyConnectionProxy extends AbstractConnectionProxy {
    private Supplier<Connection> supplier;
    private Connection target = null;

    public LazyConnectionProxy(Supplier<Connection> supplier) {
        this.supplier = supplier;
    }

    // 覆写close方法：只有target不为null时才需要关闭:
    public void close() throws SQLException {
        if (target != null) {
            System.out.println("Close connection: " + target);
            super.close();
        }
    }

    @Override
    protected Connection getRealConnection() {
        if (target == null) {
            target = supplier.get();
        }
        return target;
    }
}

class LazyDataSource implements DataSource {
    private String url;
    private String username;
    private String password;
    /** Log stream */
    protected transient PrintWriter logWriter = null;

    public LazyDataSource(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new LazyConnectionProxy(() -> {
            try {
                Connection conn = DriverManager.getConnection(url, username, password);
                System.out.println("Open connection: " + conn);
                return conn;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return new LazyConnectionProxy(() -> {
            try {
                Connection conn = DriverManager.getConnection(url, username, password);
                System.out.println("Open connection: " + conn);
                return conn;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getConnection().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getConnection().isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return this.logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

}

/**
* @Description : 调用方并不关心是否复用了Connection，
 * 但从PooledDataSource获取的Connection确实自带这个优化功能。如何实现可复用Connection的连接池？
 * 答案仍然是使用代理模式。
 *
 * 复用连接的关键在于覆写close()方法，它并没有真正关闭底层JDBC连接，而是把自己放回一个空闲队列，以便下次使用。
 * 空闲队列由PooledDataSource负责维护：
 *
* @Author:FuQiangCalendar
* @Date: 2021/5/21 16:55
*/
class PooledConnectionProxy extends AbstractConnectionProxy {
    // 实际的Connection:
    Connection target;
    // 空闲队列:
    Queue<PooledConnectionProxy> idleQueue;

    public PooledConnectionProxy(Queue<PooledConnectionProxy> idleQueue, Connection target) {
        this.idleQueue = idleQueue;
        this.target = target;
    }

    public void close() throws SQLException {
        System.out.println("Fake close and released to idle queue for future reuse: " + target);
        // 并没有调用实际Connection的close()方法,
        // 而是把自己放入空闲队列:
        idleQueue.offer(this);
    }

    protected Connection getRealConnection() {
        return target;
    }
}

class PooledDataSource implements DataSource {
    private String url;
    private String username;
    private String password;

    // 维护一个空闲队列:
    private Queue<PooledConnectionProxy> idleQueue = new ArrayBlockingQueue<>(100);

    public PooledDataSource(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        // 首先试图获取一个空闲连接:
        PooledConnectionProxy conn = idleQueue.poll();
        if (conn == null) {
            // 没有空闲连接时，打开一个新连接:
            conn = openNewConnection();
        } else {
            System.out.println("Return pooled connection: " + conn.target);
        }
        return conn;
    }

    public Connection getConnection(String username, String password) throws SQLException {
        // 首先试图获取一个空闲连接:
        PooledConnectionProxy conn = idleQueue.poll();
        if (conn == null) {
            // 没有空闲连接时，打开一个新连接:
            conn = openNewConnection();
        } else {
            System.out.println("Return pooled connection: " + conn.target);
        }
        return conn;
    }

    private PooledConnectionProxy openNewConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println("Open new connection: " + conn);
        return new PooledConnectionProxy(idleQueue, conn);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}