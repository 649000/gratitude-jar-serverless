package com.nazri.service;

import com.nazri.model.Gratitude;
import com.nazri.repository.GratitudeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class GratitudeService {

    @Inject
    GratitudeRepository gratitudeRepository;

    
    public Gratitude getGratitudeById(Long id) {
        gratitudeRepository.findById(id);
        gratitudeRepository.findByIdOptional(id).ifPresentOrElse();
        return gratitudes.stream()
                .filter(gratitude -> gratitude.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gratitude not found"));
    }
    
    public List<Gratitude> getAllGratitudes() {
        return gratitudes;
    }
    
    public Gratitude createGratitude(Gratitude gratitude) {
        gratitudes.add(gratitude);
        return gratitude;
    }
    
    public Gratitude updateGratitude(Long id, Gratitude gratitude) {
        Gratitude existingGratitude = getGratitudeById(id);
        existingGratitude.setName(gratitude.getName());
        existingGratitude.setEmail(gratitude.getEmail());
        return existingGratitude;
    }

    public void deleteGratitude(Long id) {
        gratitudes.removeIf(gratitude -> gratitude.getId().equals(id));
    }
}
