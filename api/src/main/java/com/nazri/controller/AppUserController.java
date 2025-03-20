package com.nazri.controller;

import com.nazri.model.AppUser;
import com.nazri.service.AppUserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppUserController {

    @Inject
    AppUserService appUserService;

    @POST
    public Response creatUser(AppUser appUser) {
        AppUser createdAppUser = appUserService.createAppUser(appUser);
        return Response.status(Response.Status.CREATED).entity(createdAppUser).build();
    }

    // Read (Single)
    @GET
    @Path("/{userUID}")
    public Response getAppUser(@PathParam("userUID") String userUID) {
        AppUser appUser = appUserService.findAppUserByUserUID(userUID);
        return Response.ok(appUser).build();
    }

    // Read (All)
    @GET
    public List<AppUser> getAllAppUsers() {
        return appUserService.findAllAppUsers();
    }

    // Update
//    @PUT
//    @Path("/{userUID}")
//    public Response updateAppUser(@PathParam("userUID") Long userUID, AppUser appUser) {
//        AppUser updatedAppUser = appUserService.updateAppUser(userUID, appUser);
//        return Response.ok(updatedAppUser).build();
//    }

    // Delete
    @DELETE
    @Path("/{userUID}")
    public Response deleteAppUser(@PathParam("userUID") String userUID) {
        appUserService.deleteAppUser(userUID);
        return Response.noContent().build();
    }
}
