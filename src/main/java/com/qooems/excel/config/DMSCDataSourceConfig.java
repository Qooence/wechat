package com.qooems.excel.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = DMSCDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "dMSCSqlSessionFactory")
public class DMSCDataSourceConfig {

    /**
     * 配置多数据源 关键就在这里 这里配置了不同的数据源扫描不同mapper
     */
    static final String PACKAGE = "com.qooems.excel.mapper.dsmc";
    static final String MAPPER_LOCATION = "classpath:mappers/dsmc/*.xml";

    /**
     * 连接数据库信息 这个其实更好的是用配置中心完成
     */
    @Value("${spring.datasource.dmsc.datasource.url:#{null}}")
    private String url;

    @Value("${spring.datasource.dmsc.datasource.username:#{null}}")
    private String username;

    @Value("${spring.datasource.dmsc.datasource..password:#{null}}")
    private String password;

    @Value("${spring.datasource.dmsc.datasource.driverClassName:#{null}}")
    private String driverClassName;


    /**
     * 下面的配置信息可以读取配置文件，其实可以直接写死 如果是多数据源的话 还是考虑读取配置文件
     */
    @Value("${spring.datasource.druid.initial-size:#{null}}")
    private int initialSize;

    @Value("${spring.datasource.druid..min-idle:#{null}}")
    private int minIdle;

    @Value("${spring.datasource.druid.max-active:#{null}}")
    private int maxActive;

    @Value("${spring.datasource.druid.max-wait:#{null}}")
    private int maxWait;

    @Value("${spring.datasource.druid.time-between-eviction-runs-millis:#{null}}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.min-evictable-idle-time-millis:#{null}}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validation-query:#{null}}")
    private String validationQuery;

    @Value("${spring.datasource.druid.test-while-idle:#{null}}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.druid.test-on-borrow:#{null}}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.test-on-return:#{null}}")
    private boolean testOnReturn;

    @Value("${spring.datasource.druid.pool-prepared-statements:#{null}}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.druid.max-pool-prepared-statement-per-connection-size:#{null}}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.druid.filters:#{null}}")
    private String filters;

    //监控 先注释，有空在搞 https://blog.csdn.net/cauchy6317/article/details/90698311
    @Value("{spring.datasource.druid.connectionProperties:#{null}}")
    private String connectionProperties;


    @Bean(name = "dMSCDataSource")
    public DataSource DMSCDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        //具体配置
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        /**
         * 这个是用来配置 druid 监控sql语句的 非常有用 如果你有两个数据源 这个配置哪个数据源就监控哪个数据源的sql 同时配置那就都监控
         */
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        //先注释，有空在搞
        dataSource.setConnectionProperties(connectionProperties);
        return dataSource;
    }

    @Bean(name = "dMSCTransactionManager")
    public DataSourceTransactionManager DMSCTransactionManager() {
        return new DataSourceTransactionManager(DMSCDataSource());
    }

    @Bean(name = "dMSCSqlSessionFactory")
    public SqlSessionFactory WeChatSqlSessionFactory(@Qualifier("dMSCDataSource") DataSource dMSCDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dMSCDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DMSCDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
