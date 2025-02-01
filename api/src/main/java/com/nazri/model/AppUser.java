package com.nazri.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class AppUser extends PanacheEntityBase {

    @Id
    private String userUID;

    @CreationTimestamp
    @NotNull(message = "Created Date/Time cannot be null")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @NotNull(message = "Updated Date/Time cannot be null")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Gratitude> gratitudeList;

    public String getUserUID() {
        return userUID;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public List<Gratitude> getGratitudeList() {
        return gratitudeList;
    }
}
