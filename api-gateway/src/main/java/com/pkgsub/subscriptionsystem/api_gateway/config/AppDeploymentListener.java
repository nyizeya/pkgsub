package com.pkgsub.subscriptionsystem.api_gateway.config;

import com.pkgsub.subscriptionsystem.api_gateway.entity.User;
import com.pkgsub.subscriptionsystem.api_gateway.repository.UserRepository;
import com.pkgsub.subscriptionsystem.common.enumerations.AppUserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppDeploymentListener implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            log.info("***** Creating admin user *****");
            User user = new User();
            user.setUsername("admin");
            user.setEmail("admin@gmail.com");
            user.setRole(AppUserRole.ADMIN);
            user.setPassword(passwordEncoder.encode("test1234"));
            userRepository.save(user);
        }

    }
}
