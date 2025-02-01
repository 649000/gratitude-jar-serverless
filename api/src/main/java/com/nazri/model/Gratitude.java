package com.nazri.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Cacheable
public class Gratitude extends PanacheEntity {

    @NotNull(message = "Gratitude cannot be null")
    @Size(min = 1, max = 2000, message = "Message must be between 1 and 500 characters")
    private String message;

    @CreationTimestamp
    @NotNull(message = "Created Date/Time cannot be null")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @NotNull(message = "Updated Date/Time cannot be null")
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "App User cannot be null")
    private AppUser appUser;

    public AppUser getUser() {
        return appUser;
    }

    public void setUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
}
