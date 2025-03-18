package com.nazri.service;

import com.nazri.model.AppUser;
import com.nazri.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class AppUserService {

    @Inject
    UserRepository userRepository;

    // Create
    public AppUser createAppUser(AppUser appUser) {
        userRepository.persist(appUser);
        return appUser;
    }

    // Read (Single)
    public AppUser findAppUserByUserUID(Long userUID) {
        return userRepository.findByIdOptional(userUID)
                .orElseThrow(() -> new NotFoundException("AppUser with UID " + userUID + " not found."));
    }

    // Read (All)
    public List<AppUser> findAllAppUsers() {
        return userRepository.listAll();
    }

//    // Update
//    public AppUser updateAppUser(Long userUID, AppUser appUser) {
//        AppUser existingAppUser = userRepository.findByIdOptional(userUID)
//                .orElseThrow(() -> new NotFoundException("AppUser with UID " + userUID + " not found."));
//
//        // Update fields
//        existingAppUser.setGratitudeList(appUser.getGratitudeList());
//        userRepository.persist(existingAppUser);
//        return existingAppUser;
//    }

    // Delete
    public void deleteAppUser(Long userUID) {
        AppUser existingAppUser = userRepository.findByIdOptional(userUID)
                .orElseThrow(() -> new NotFoundException("AppUser with UID " + userUID + " not found."));

        userRepository.delete(existingAppUser);
    }

}
