package com.xiangan.platform.chainserver;

import com.xiangan.platform.chainserver.sdk.config.SDKConfigFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 服务启动器
 *
 * @Creater liuzhudong
 * @Date 2017/4/7 17:01
 * @Version 1.0
 * @Copyright
 */
@SpringBootApplication
public class Application {

    @Value("${fabric.sdk.config.path}")
    private String sdkConfigPath;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(initMethod = "init")
    public SDKConfigFactory sdkConfigFactory() {
        SDKConfigFactory factory = new SDKConfigFactory();
        factory.setSdkConfigPath(sdkConfigPath);
        return factory;
    }


}
