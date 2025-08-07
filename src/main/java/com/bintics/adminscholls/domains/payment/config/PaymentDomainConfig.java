package com.bintics.adminscholls.domains.payment.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.bintics.adminscholls.domains.payment.src")
@EntityScan(basePackages = "com.bintics.adminscholls.domains.payment.src.model")
@EnableJpaRepositories(basePackages = "com.bintics.adminscholls.domains.payment.src.repository")
public class PaymentDomainConfig {

    // Esta configuración permite que el dominio Payment tenga su propio contexto
    // y sea autodescubierto por Spring Boot

}
