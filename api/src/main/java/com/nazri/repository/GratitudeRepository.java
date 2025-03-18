package com.nazri.repository;

import com.nazri.model.Gratitude;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class GratitudeRepository implements PanacheRepository<Gratitude> {
    // Custom method to find Gratitude by id and userUID
    public Optional<Gratitude> findByIdAndUserUID(Long id, String userUID) {
        return find("id = :id AND appUser.userUID = :userUID",
                Parameters.with("id", id).and("userUID", userUID))
                .firstResultOptional(); // Returns the first matching result or null
    }
}
