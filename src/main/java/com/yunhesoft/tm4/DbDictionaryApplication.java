package com.yunhesoft.tm4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author zhang.jt
 */
@SpringBootApplication
@ComponentScans(value = { @ComponentScan(value = "com.yunhesoft.tm4.dbdictionary.*"),
		@ComponentScan(value = "com.yunhesoft.tm4.dbdictionary.repository"),
		@ComponentScan(value = "com.yunhesoft.tm4.dbdictionary.utils") })
public class DbDictionaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbDictionaryApplication.class, args);
	}

}
