package com.example.demo.api;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserApi extends ReactorUserApiGrpc.UserApiImplBase {

    private final UserService userService;

    @Override
    public Mono<UserDto> getUser(Mono<GetUserRequest> request) {
        return request
                .log()
                .map(GetUserRequest::getId)
                .flatMap(userService::getUser)
                .map(this::mapToDto);
    }

    @Override
    public Mono<UserDto> saveUser(Mono<UserDto> request) {
        return request
                .log()
                .map(this::mapToUser)
                .flatMap(userService::saveUser)
                .map(this::mapToDto);
    }

    private UserDto mapToDto(User user) {
        return UserDto.newBuilder()
                .setId(user.getId().toString())
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .build();
    }

    private User mapToUser(UserDto userDto) {
        return User.builder()
                .id(StringUtils.isBlank(userDto.getId()) ? null : UUID.fromString(userDto.getId()))
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
    }
}
