package com.nazri.service;

import com.nazri.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AppUserService {

    @Inject
    UserRepository userRepository;

}
