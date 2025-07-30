package dev.phquartin.movieflix.service;

import dev.phquartin.movieflix.controller.request.UserRequest;
import dev.phquartin.movieflix.controller.response.UserResponse;
import dev.phquartin.movieflix.mapper.UserMapper;
import dev.phquartin.movieflix.model.User;
import dev.phquartin.movieflix.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserResponse save(UserRequest request){
        User saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

}
