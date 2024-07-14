package com.mafer.car.registry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;



@EnableAsync
public class AsyncConfig {

    @Bean(name="taskExecutor")
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); //Número de hilos
        executor.setMaxPoolSize(10); //Número máx de hilos
        executor.setQueueCapacity(100); //Capacidad de la cola
        executor.setThreadNamePrefix("demoThead-"); //Nombre del hilo
        executor.initialize();

        return executor;
    }

}
