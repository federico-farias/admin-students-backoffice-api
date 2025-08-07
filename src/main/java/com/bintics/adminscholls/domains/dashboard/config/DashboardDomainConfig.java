package com.bintics.adminscholls.domains.dashboard.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.bintics.adminscholls.domains.dashboard.src")
public class DashboardDomainConfig {

    // Esta configuración permite que el dominio Dashboard tenga su propio contexto
    // y sea autodescubierto por Spring Boot
    // Nota: Dashboard no necesita EntityScan ni EnableJpaRepositories ya que no tiene entidades propias

}
