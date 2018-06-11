package com.diao.base.config;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(
        mode = AdviceMode.PROXY,
        order = 2147483647
)
@EnableAspectJAutoProxy(
        proxyTargetClass = true
)
@MapperScan(
        basePackages = {"com.diao.**.mapper"}
)
@PropertySource({"classpath:application-db.properties"})
public class DatasourceConfig {

    private static final Logger log = LoggerFactory.getLogger(DatasourceConfig.class);
    private static final String[] TX_WRITE_FUNC_LIST = new String[]{"save*", "insert*", "update*", "modify*", "remove*", "delete*"};
    private static final String[] READ_FUNC_LIST = new String[]{"load*", "list*", "get*", "count*"};
    @Resource
    private DataSource dataSource;

    public DatasourceConfig() {
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource);
    }

    @Bean
    public Properties txInterceptorProperties() {
        Properties p = new Properties();
        p.setProperty("*", "readOnly");
        String[] var2 = TX_WRITE_FUNC_LIST;
        int var3 = var2.length;

        int var4;
        String prefix;
        for(var4 = 0; var4 < var3; ++var4) {
            prefix = var2[var4];
            if (!StringUtils.isBlank(prefix)) {
                p.setProperty(prefix, "PROPAGATION_REQUIRED,-Exception");
            }
        }

        var2 = READ_FUNC_LIST;
        var3 = var2.length;

        for(var4 = 0; var4 < var3; ++var4) {
            prefix = var2[var4];
            if (!StringUtils.isBlank(prefix)) {
                p.setProperty(prefix, "PROPAGATION_SUPPORTS,readOnly,-Exception");
            }
        }

        return p;
    }

    @Bean
    @Primary
    public Advisor jpaRepositoryAdvisor(PlatformTransactionManager txManager, @Qualifier("txInterceptorProperties") Properties txInterceptorProperties) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("(execution(* com.yazuo..biz..*.*(..)) && !@target(org.springframework.transaction.annotation.Transactional) && !@annotation(org.springframework.transaction.annotation.Transactional))");
        TransactionInterceptor txInterceptor = new TransactionInterceptor(txManager, txInterceptorProperties);
        txInterceptor.afterPropertiesSet();
        log.debug("Transaction Interceptor use Properties[{}].", txInterceptorProperties);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, txInterceptor);
        advisor.setOrder(2147483647);
        return advisor;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        WildcardSqlSessionFactoryBean sessionFactory = new WildcardSqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setConfigLocation((new PathMatchingResourcePatternResolver()).getResources("classpath*:mybatis-config.xml")[0]);
        sessionFactory.setMapperLocations((new PathMatchingResourcePatternResolver()).getResources("classpath*:mappers/**/*.xml"));
        sessionFactory.setTypeAliasesPackage("com.yazuo.**.entity");
        return sessionFactory.getObject();
    }

    @Bean({"druid-stat-interceptor"})
    public DruidStatInterceptor druidStatInterceptor() throws Exception {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("(execution(* com.yazuo..biz..*.*(..))");
        DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();
        druidStatInterceptor.afterPropertiesSet();
        log.debug("Druid Statistics Interceptor AOP advisor.");
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, druidStatInterceptor);
        advisor.setOrder(2147483647);
        return druidStatInterceptor;
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setProxyTargetClass(true);
        beanNameAutoProxyCreator.setInterceptorNames(new String[]{"druid-stat-interceptor"});
        return beanNameAutoProxyCreator;
    }
}
