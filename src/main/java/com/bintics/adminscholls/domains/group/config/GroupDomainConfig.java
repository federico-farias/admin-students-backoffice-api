package com.bintics.adminscholls.domains.group.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.bintics.adminscholls.domains.group.src")
@EntityScan(basePackages = "com.bintics.adminscholls.domains.group.model")
@EnableJpaRepositories(basePackages = "com.bintics.adminscholls.domains.group.repository")
public class GroupDomainConfig {

    // Esta configuración permite que el dominio Group tenga su propio contexto
    // y sea autodescubierto por Spring Boot

}
