package com.nazri.service;

import com.nazri.model.Gratitude;
import com.nazri.repository.GratitudeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class GratitudeService {

    @Context
    SecurityContext securityContext;

    @Inject
    GratitudeRepository gratitudeRepository;


    public Optional<Gratitude> getGratitudeById(Long id) {
        return gratitudeRepository.findByIdAndUserUID(id, securityContext.getUserPrincipal().getName());
    }

    public List<Gratitude> getAllGratitudes() {
        return gratitudeRepository.listAll();
    }


    public void updateGratitude(Long id, Gratitude gratitude, String userUID) {
        // Ownership check
        Optional<Gratitude> existingGratitudeOpt = gratitudeRepository.findByIdAndUserUID(id, userUID);
        if (existingGratitudeOpt.isEmpty()) {
            throw new SecurityException("You are not authorized to update this Gratitude.");
        }

        // Update the entity
        Gratitude existingGratitude = existingGratitudeOpt.get();
        existingGratitude.setMessage(gratitude.getMessage());
        gratitudeRepository.persist(existingGratitude);
    }


    public boolean deleteGratitude(Long id, String userUID) {
        // Ownership check
        Optional<Gratitude> existingGratitudeOpt = gratitudeRepository.findByIdAndUserUID(id, userUID);
        if (existingGratitudeOpt.isEmpty()) {
            throw new SecurityException("You are not authorized to delete this Gratitude.");
        }

        // Delete the entity
        return gratitudeRepository.deleteById(id);
    }
}
