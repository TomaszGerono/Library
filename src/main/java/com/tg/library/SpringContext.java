package com.tg.library;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringContext {

    private static ConfigurableApplicationContext context;

    public static void init(Class<?> appClass) {
        context = SpringApplication.run(appClass);
    }

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

}