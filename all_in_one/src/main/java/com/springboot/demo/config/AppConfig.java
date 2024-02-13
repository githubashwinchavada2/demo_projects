package com.springboot.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.springboot.demo.*", "com.springboot.dependent.*"})
public class AppConfig {

}
