package com.nazri.config;


import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import java.io.IOException;
import java.security.Principal;

@Provider
@PreMatching
@IfBuildProfile("prod")
public class JwtSecurityContextFilter implements ContainerRequestFilter {
    private static final Logger log = Logger.getLogger(JwtSecurityContextFilter.class);

    @Context
    APIGatewayV2HTTPEvent.RequestContext req;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String sub = req.getAuthorizer().getJwt().getClaims().get("sub");
        // To Be Implemented
        String role = req.getAuthorizer().getJwt().getClaims().get("sub");

        //TODO: Check sub (and role) is not null/empty
        // Create a Principal
        Principal principal = new FirebasePrincipal(sub);

        SecurityContext securityContext = new CustomSecurityContext(principal, new String[0]);

        // Set the SecurityContext in the request
        requestContext.setSecurityContext(securityContext);


    }

    @ServerRequestFilter(preMatching = true)
    public void preMatchingFilter(ContainerRequestContext requestContext) {
        log.info("NAZRI PrematchingFilter REQUEST CONTEXT: " + req.toString());
    }

    @ServerRequestFilter
    public void matchingFilter(ContainerRequestContext requestContext) {
        log.info("NAZRI MatchingFilter REQUEST CONTEXT: " + req.toString());
    }


}

class CustomSecurityContext implements SecurityContext {

    private final Principal principal;
    private final String[] roles;

    public CustomSecurityContext(Principal principal, String[] roles) {
        this.principal = principal;
        this.roles = roles;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        for (String r : roles) {
            if (r.equals(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSecure() {
        return true; // Assume HTTPS
    }

    @Override
    public String getAuthenticationScheme() {
        return "JWT";
    }
}

class FirebasePrincipal implements Principal {

    private final String name;

    public FirebasePrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
