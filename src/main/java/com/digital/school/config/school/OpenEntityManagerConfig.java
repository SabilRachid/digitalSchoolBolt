package com.digital.school.config.school;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

@Configuration
public class OpenEntityManagerConfig {

    @Bean
    public FilterRegistrationBean<OpenEntityManagerInViewFilter> openEntityManagerInViewFilter() {
        FilterRegistrationBean<OpenEntityManagerInViewFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new OpenEntityManagerInViewFilter());
        registrationBean.addUrlPatterns("/*"); // Appliquer à toutes les URL
        registrationBean.setOrder(1); // Assure-toi qu'il s'exécute avant d'autres filtres si besoin
        return registrationBean;
    }
}
