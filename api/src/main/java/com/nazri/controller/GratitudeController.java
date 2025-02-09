package com.nazri.controller;

import com.nazri.model.Gratitude;
import com.nazri.repository.GratitudeRepository;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
@Path("/api/gratitude")
public class GratitudeController {

    private static final Logger log = Logger.getLogger(GratitudeController.class);

    @Inject
    GratitudeRepository gratitudeRepository;

    @Context
    SecurityContext securityContext;

    @Context
    SecurityIdentity securityIdentity;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGratitude(@Valid Gratitude gratitude) {
        //gratitude.setUser();
        gratitudeRepository.persist(gratitude);
        return Response.ok(gratitude).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGratitudeById(@PathParam("id") Long id) {
        // Logic to get a gratitude by ID
        return Response.ok("gratitude").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        // Logic to get all users
        return Response.ok(gratitudeRepository.listAll()).build();
    }

    @GET
    public List<Gratitude> getAllGratitudes() {
        //TODO: Exploration on security, both are possible contexts
        log.info(": SECURITY CONTEXT: "+ securityContext.getUserPrincipal().getName());
        log.info(": SECURITY IDENTITY: "+ securityIdentity.getPrincipal().getName());
        return gratitudeRepository.listAll();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGratitude(@PathParam("id") Long id, Gratitude gratitude) {
        // Logic to gratitude a user
        return Response.ok(gratitude).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteGratitude(@PathParam("id") Long id) {
        // Logic to delete a user
        return Response.noContent().build();
    }
}
