package com.nazri.config;


import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.SecurityContext;

public class SecurityContextProducer {

    @Produces
    @Singleton
    @IfBuildProfile("local")
    public SecurityContext produceMockSecurityContext(MockSecurityContext mockSecurityContext) {
        return mockSecurityContext;
    }
}
