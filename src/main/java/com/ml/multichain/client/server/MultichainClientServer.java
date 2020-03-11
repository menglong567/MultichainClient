package com.ml.multichain.client.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author mengl
 */
@SpringBootApplication(scanBasePackages = "com.ml")
@EnableSwagger2
public class MultichainClientServer {
    public static void main(String[] args) {
        SpringApplication.run(MultichainClientServer.class, args);
    }
}

