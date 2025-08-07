package com.bintics.adminscholls.domains.enrollment.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.bintics.adminscholls.domains.enrollment.src")
@EntityScan(basePackages = "com.bintics.adminscholls.domains.enrollment.model")
@EnableJpaRepositories(basePackages = "com.bintics.adminscholls.domains.enrollment.repository")
public class EnrollmentDomainConfig {

    // Esta configuración permite que el dominio Enrollment tenga su propio contexto
    // y sea autodescubierto por Spring Boot

}
