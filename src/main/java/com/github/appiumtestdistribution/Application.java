package com.github.appiumtestdistribution;

import com.github.android.AndroidManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AndroidManager androidManager() {
        return new AndroidManager();
    }
}