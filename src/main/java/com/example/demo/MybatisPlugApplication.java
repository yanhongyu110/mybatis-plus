package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.example.demo.dao")
@ComponentScan("com.example.demo")
public class MybatisPlugApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlugApplication.class, args);
	}
}
