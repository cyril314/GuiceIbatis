package conf;

import com.fit.filter.CharsetEncodingFilter;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import org.mybatis.guice.XMLMyBatisModule;

/**
 * @AUTO 用与注册module的，就是google guice用的
 * @FILE Module.java
 * @DATE 2017-9-28 下午8:35:38
 * @Author AIM
 */
@Singleton
public class Module extends AbstractModule {

    @Override
    protected void configure() {
        // 安装 MyBatis 模块
        install(new XMLMyBatisModule() {
            @Override
            protected void initialize() {
                setEnvironmentId("fit");
                setClassPathResource("mybatis-config.xml");
            }
        });

        // 安装 ServletModule 并绑定 Filter
        install(new ServletModule() {
            @Override
            protected void configureServlets() {
                filter("/*").through(CharsetEncodingFilter.class);
            }
        });
    }
}