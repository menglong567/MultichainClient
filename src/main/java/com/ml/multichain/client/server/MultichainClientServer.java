package com.ml.multichain.client.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mengl
 */
@SpringBootApplication(scanBasePackages = "com.ml")
public class MultichainClientServer {
    public static void main(String[] args) {
        SpringApplication.run(MultichainClientServer.class, args);
    }
}

