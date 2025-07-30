package dev.phquartin.movieflix.service;

import dev.phquartin.movieflix.controller.request.UserRequest;
import dev.phquartin.movieflix.controller.response.UserResponse;
import dev.phquartin.movieflix.exception.ResourceAlreadyExistsException;
import dev.phquartin.movieflix.mapper.UserMapper;
import dev.phquartin.movieflix.model.User;
import dev.phquartin.movieflix.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    public UserResponse save(UserRequest request){
        User entity = mapper.toEntity(request);

        if(repository.existsByEmail(entity.getEmail())) throw new ResourceAlreadyExistsException("User name or email already exists");
        if(repository.existsByName(entity.getName())) throw new ResourceAlreadyExistsException("User name or email already exists");

        entity.setPassword(encoder.encode(entity.getPassword()));
        User saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

}
