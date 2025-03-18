package com.nazri.service;

import com.nazri.model.Gratitude;
import com.nazri.repository.GratitudeRepository;
import com.nazri.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.list;

@ApplicationScoped
public class GratitudeService {

    @Context
    SecurityContext securityContext;

    @Inject
    GratitudeRepository gratitudeRepository;

    @Inject
    UserRepository userRepository;

    // Create
    public Gratitude createGratitude(Gratitude gratitude, String userUID) {
        gratitude.setUser(userRepository.find("userUID", userUID).firstResult());
        gratitudeRepository.persist(gratitude);
        return gratitude;
    }

    // Read (Single)
    public Gratitude findGratitudeByIdAndUserUID(Long id, String userUID) {
        return gratitudeRepository.findByIdAndUserUID(id, userUID)
                .orElseThrow(() -> new NotFoundException("Gratitude with ID " + id + " not found or does not belong to you."));
    }

    // Read (All for a User)
    public List<Gratitude> findAllGratitudesForUser(String userUID) {
        return list("appUser.userUID", userUID);
    }


    // Update
    public Gratitude updateGratitude(Long id, Gratitude gratitude, String userUID) {
        Gratitude existingGratitude = gratitudeRepository.findByIdAndUserUID(id, userUID)
                .orElseThrow(() -> new NotFoundException("Gratitude with ID " + id + " not found or does not belong to you."));

        existingGratitude.setMessage(gratitude.getMessage());
        gratitudeRepository.persist(existingGratitude);
        return existingGratitude;
    }

    // Delete
    public void deleteGratitude(Long id, String userUID) {
        Gratitude existingGratitude = gratitudeRepository.findByIdAndUserUID(id, userUID)
                .orElseThrow(() -> new NotFoundException("Gratitude with ID " + id + " not found or does not belong to you."));

        gratitudeRepository.delete(existingGratitude);
    }
}
