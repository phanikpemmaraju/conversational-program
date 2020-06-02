package org.legacy.it.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class Config {

    @Scope("prototype")
    @Bean
    public ExecutorService newExecutorService(){
        return Executors.newFixedThreadPool(1);
    }
}
