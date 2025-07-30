package dev.phquartin.movieflix.mapper;

import dev.phquartin.movieflix.controller.request.UserRequest;
import dev.phquartin.movieflix.controller.response.UserResponse;
import dev.phquartin.movieflix.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest request);

    UserResponse toResponse(User user);

}
