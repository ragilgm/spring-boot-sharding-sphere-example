package com.example.springbootshardingsphereexample;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: Gilang Whisperer
 * Created on 05/03/2024
 */
@SpringBootApplication(exclude = {JtaAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@EnableJpaAuditing
@Slf4j
public class SpringBootShardingSphereExampleApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootShardingSphereExampleApplication.class, args);
    }

}
