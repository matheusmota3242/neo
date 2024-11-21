package com.m2g2.neo.function;

import com.m2g2.neo.dto.response.DashboardResponse;
import com.m2g2.neo.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.function.Supplier;

@CrossOrigin
@Configuration
public class DashboardFunction {

    private static final Logger logger = LoggerFactory.getLogger(DashboardFunction.class);
    private final DashboardService service;

    public DashboardFunction(DashboardService service) {
        this.service = service;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // Allows any origin - replace with specific URL for security
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public Supplier<DashboardResponse> getDashboard() {
        logger.info("In: getDashboard");
        DashboardResponse dashboardResponse = service.getDashboard();
        logger.info("Out: getDashboard | Response: {}", dashboardResponse);
        return () -> dashboardResponse;
    }

}
