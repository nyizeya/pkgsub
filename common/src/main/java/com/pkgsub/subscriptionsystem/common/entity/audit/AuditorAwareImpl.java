package com.pkgsub.subscriptionsystem.common.entity.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(getUsername());
    }

    private String getUsername() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return ObjectUtils.isEmpty(authentication) ? "System" : authentication.getName();
        return "God";
    }
}