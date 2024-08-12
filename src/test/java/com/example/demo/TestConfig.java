package com.example.demo;

import com.example.demo.aspect.GeneralInterceptorAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Configuration class for setting up application aspects.
 */
@Configuration
@EnableAspectJAutoProxy
public class TestConfig {

    /**
     * Bean definition for the GeneralInterceptorAspect.
     *
     * @return a new instance of GeneralInterceptorAspect
     */
    @Bean
    public GeneralInterceptorAspect generalInterceptorAspect() {
        return new GeneralInterceptorAspect();
    }
}
