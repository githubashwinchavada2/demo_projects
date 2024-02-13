package com.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

//    http://192.168.0.102:8082/application/default
//    http://192.168.0.102:8082/application/DEV
//    http://192.168.0.102:8082/application/QA
//    http://192.168.0.102:8082/application/PROD
//    URL for yml files

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
