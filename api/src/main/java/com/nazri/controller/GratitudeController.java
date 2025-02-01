package com.nazri.controller;

import com.nazri.model.Gratitude;
import com.nazri.repository.GratitudeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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

    @GET
    public List<Gratitude> getAllGratitudes() {
//        log.info(event.toString());
        return gratitudeRepository.listAll();
    }


    @POST
    public void createGratitude(Gratitude gratitude) {
        gratitudeRepository.persist(gratitude);
    }
}
