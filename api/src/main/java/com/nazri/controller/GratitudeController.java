package com.nazri.controller;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.nazri.model.Gratitude;
import com.nazri.repository.GratitudeRepository;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
@Path("/api/gratitude")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GratitudeController {

    private static final Logger log = Logger.getLogger(GratitudeController.class);

    @Inject
    GratitudeRepository gratitudeRepository;

    @Context
    SecurityContext securityContext;

    @Context
    SecurityIdentity securityIdentity;

    @GET
    public List<Gratitude> getAllGratitudes() {
        //TODO: Exploration on security, both are possible contextes
        log.info(": SECURITY CONTEXT: "+ securityContext.getUserPrincipal().getName());
        log.info(": SECURITY IDENTITY: "+ securityIdentity.getPrincipal().getName());
        return gratitudeRepository.listAll();
    }


    @POST
    public void createGratitude(Gratitude gratitude) {
        gratitudeRepository.persist(gratitude);
    }
}
