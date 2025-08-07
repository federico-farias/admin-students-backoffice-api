package com.bintics.adminscholls.domains.breakfast.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.bintics.adminscholls.domains.breakfast.src")
@EntityScan(basePackages = "com.bintics.adminscholls.domains.breakfast.src.model")
@EnableJpaRepositories(basePackages = "com.bintics.adminscholls.domains.breakfast.src.repository")
public class BreakfastDomainConfig {

    // Esta configuración permite que el dominio Breakfast tenga su propio contexto
    // y sea autodescubierto por Spring Boot

}
