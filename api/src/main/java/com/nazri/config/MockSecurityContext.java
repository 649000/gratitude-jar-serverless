package com.nazri.config;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.security.Principal;
@RequestScoped
@Alternative
@Priority(1)
@IfBuildProfile("local")
public class MockSecurityContext implements SecurityContext {

    @ConfigProperty(name = "mock.username")
    String mockUserName;

    @Override
    public Principal getUserPrincipal() {
        return () -> mockUserName;  // Return the mock user name
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return "MOCK";
    }
}