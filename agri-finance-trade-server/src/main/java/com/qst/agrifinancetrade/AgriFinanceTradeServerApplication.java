package com.qst.agrifinancetrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// 修改:使用MyBatis-Plus的Mapper扫描注解
import org.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan("com.qst.agrifinancetrade.dao")

public class AgriFinanceTradeServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgriFinanceTradeServerApplication.class,args);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}