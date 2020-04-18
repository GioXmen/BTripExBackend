package com.btplanner.btripexbackend.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {

    @Bean
    public ImageUtilityImpl imageUtility() {
        return new ImageUtilityImpl();
    }

    @Bean
    public JasperReportsUtil generateEventReport() {
        return new JasperReportsUtil();
    }
}
