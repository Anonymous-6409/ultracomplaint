package com.ultrasoft.ultracomplaint.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("prod")
public class PRODWebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:https://ultrasoftsys.com/");
        registry.addViewController("/index.html").setViewName("redirect:https://ultrasoftsys.com/");
        registry.addViewController("/swagger-ui/index.html").setViewName("redirect:https://ultrasoftsys.com/");
    }
}
