package com.example.Library.Util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

// singleton
// utility to get the spring application context to retrieve bean from non-spring managed classes
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ctx == null) {
            ctx = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}
