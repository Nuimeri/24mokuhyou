package com.circleaf.circleaf_api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.circleaf.circleaf_api.mapper")
public class CircleafApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CircleafApiApplication.class, args);
	}

}
