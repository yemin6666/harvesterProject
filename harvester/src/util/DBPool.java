package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class DBPool {
	private static final ComboPooledDataSource dataSource = new ComboPooledDataSource();
	private static String driverName;
	private static String url;
	private static String username;
	private static String password;
	private static int initialPoolSize;
	private static int maxPoolSize;
	private static int maxIdleTime;
	private static int acquireRetryAttempts;
	private static int acquireRetryDelay;
	private static int idleConnectionTestPeriod;
	private static String preferredTestQuery;
	private static int loginTimeout;
	private static int checkoutTimeout;
	private static boolean autoCommitOnClose;
	private static boolean debugUnreturnedConnectionStackTraces;
	private static boolean breakAfterAcquireFailure;
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
			initialPoolSize = Integer.parseInt(prop.getProperty("db.InitialPoolSize"));
			maxPoolSize = Integer.parseInt(prop.getProperty("db.MaxPoolSize"));
			maxIdleTime = Integer.parseInt(prop.getProperty("db.MaxIdleTime"));
			acquireRetryAttempts = Integer.parseInt(prop.getProperty("db.AcquireRetryAttempts"));
			acquireRetryDelay = Integer.parseInt(prop.getProperty("db.AcquireRetryDelay"));
			idleConnectionTestPeriod = Integer.parseInt(prop.getProperty("db.IdleConnectionTestPeriod"));
			preferredTestQuery = prop.getProperty("db.preferredTestQuery");
			loginTimeout = Integer.parseInt(prop.getProperty("db.LoginTimeout"));
			checkoutTimeout = Integer.parseInt(prop.getProperty("db.checkoutTimeout"));
			autoCommitOnClose = Boolean.parseBoolean(prop.getProperty("db.autoCommitOnClose"));
			debugUnreturnedConnectionStackTraces = Boolean.parseBoolean(
					prop.getProperty("db.debugUnreturnedConnectionStackTraces"));
			breakAfterAcquireFailure = Boolean.parseBoolean(prop.getProperty("db.breakAfterAcquireFailure"));
			
			driverName = prop.getProperty("db.driver");
			url = prop.getProperty("db.url");
    		username = prop.getProperty("db.username");
    		password = prop.getProperty("db.password");
			
		    // initialize dataSource
		    dataSource.setDriverClass(driverName);
			dataSource.setJdbcUrl(url);
			dataSource.setUser(username);
			dataSource.setPassword(password);
			//初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3
			dataSource.setInitialPoolSize(initialPoolSize);
			//连接池中保留的最大连接数。Default: 15 
			dataSource.setMaxPoolSize(maxPoolSize);
			// 连接池中保留的最小连接数。
			//dataSource.setMinPoolSize(1);
			//当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
			//dataSource.setAcquireIncrement(1);
			//每idleConnectionTestPeriod秒检查所有连接池中的空闲连接。Default: 0 
			dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
			//最大空闲时间,maxIdleTime秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
			dataSource.setMaxIdleTime(maxIdleTime);
			//连接关闭时默认将所有未提交的操作回滚。Default: false
			dataSource.setAutoCommitOnClose(autoCommitOnClose);
			//定义所有连接测试都执行的测试语句
			dataSource.setPreferredTestQuery(preferredTestQuery);
			//如果设为true那么在取得连接的同时将校验连接的有效性。Default: false 因性能消耗大请只在需要的时候使用它
			//dataSource.setTestConnectionOnCheckin(true);
			//定义在从数据库获取新连接失败后重复尝试的次数。Default: 30
			dataSource.setAcquireRetryAttempts(acquireRetryAttempts);
			//两次连接中间隔时间，单位毫秒。Default: 1000
			dataSource.setAcquireRetryDelay(acquireRetryDelay);
			//获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。但是数据源仍有效,并在下次调用getConnection()的时候继续尝试获取连接
			//如果设为true，那么在尝试获取连接失败后该数据源将申明已断开并永久关闭。Default: false
			dataSource.setBreakAfterAcquireFailure(breakAfterAcquireFailure);
			dataSource.setLoginTimeout(loginTimeout);
			//连接泄漏时打印堆栈信息
			dataSource.setDebugUnreturnedConnectionStackTraces(debugUnreturnedConnectionStackTraces);
			//获取连接超时时间为10秒，默认则无限等待。设置此值高并发时（连接数占满）可能会引发中断数据库操作风险
			dataSource.setCheckoutTimeout(checkoutTimeout);
		    
		} catch (Exception e) {
			throw new RuntimeException("DBPool初始化失败",e);
		}
	}
	
	private DBPool() {}
	
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
	
}
