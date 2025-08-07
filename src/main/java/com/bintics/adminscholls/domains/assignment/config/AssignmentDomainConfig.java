package com.bintics.adminscholls.domains.assignment.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.bintics.adminscholls.domains.assignment.src")
@EntityScan(basePackages = "com.bintics.adminscholls.domains.assignment.src.model")
@EnableJpaRepositories(basePackages = "com.bintics.adminscholls.domains.assignment.src.repository")
public class AssignmentDomainConfig {

    // Esta configuración permite que el dominio Assignment tenga su propio contexto
    // y sea autodescubierto por Spring Boot

}
