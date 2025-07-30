package dev.phquartin.movieflix.service;

import dev.phquartin.movieflix.exception.ResourceNotFoundException;
import dev.phquartin.movieflix.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository repository;
    public AuthService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return repository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("email or password invalid"));
    }
}
