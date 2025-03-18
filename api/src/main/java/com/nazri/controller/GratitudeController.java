package com.nazri.controller;

import com.nazri.model.Gratitude;
import com.nazri.repository.GratitudeRepository;
import com.nazri.service.GratitudeService;
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
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GratitudeController {

    private static final Logger log = Logger.getLogger(GratitudeController.class);

    @Inject
    GratitudeService gratitudeService;

    @Context
    SecurityContext securityContext;

//    @Context
//    SecurityIdentity securityIdentity;
//     log.info(": SECURITY IDENTITY: "+ securityIdentity.getPrincipal().getName());


    // Helper method to get the current user's UID
    private String getCurrentUserUID() {
        return securityContext.getUserPrincipal().getName();
    }

    @POST
    public Response createGratitude(Gratitude gratitude) {
        String userUID = getCurrentUserUID();
        Gratitude createdGratitude = gratitudeService.createGratitude(gratitude, userUID);
        return Response.status(Response.Status.CREATED).entity(createdGratitude).build();
    }

    // Read (Single)
    @GET
    @Path("/{id}")
    public Response getGratitudeById(@PathParam("id") Long id) {
        String userUID = getCurrentUserUID();
        Gratitude gratitude = gratitudeService.findGratitudeByIdAndUserUID(id, userUID);
        return Response.ok(gratitude).build();
    }

    // Read (All for the Current User)
    @GET
    public List<Gratitude> getAllGratitudesForCurrentUser() {
        String userUID = getCurrentUserUID();
        return gratitudeService.findAllGratitudesForUser(userUID);
    }

    // Update
    @PUT
    @Path("/{id}")
    public Response updateGratitude(@PathParam("id") Long id, Gratitude gratitude) {
        String userUID = getCurrentUserUID();
        Gratitude updatedGratitude = gratitudeService.updateGratitude(id, gratitude, userUID);
        return Response.ok(updatedGratitude).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteGratitude(@PathParam("id") Long id) {
        String userUID = getCurrentUserUID();
        gratitudeService.deleteGratitude(id, userUID);
        return Response.noContent().build();
    }
}
