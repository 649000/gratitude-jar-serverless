package com.nazri.repository;

import com.nazri.model.Gratitude;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GratitudeRepository implements PanacheRepository<Gratitude> {
}
