package com.pkgsub.subscriptionsystem.subscriptionservice.config;

import com.pkgsub.subscriptionsystem.common.entity.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
        "com.pkgsub.subscriptionsystem.subscriptionservice.repository"
})
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {
    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}