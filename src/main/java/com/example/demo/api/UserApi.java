package com.example.demo.api;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.grpc.stub.StreamObserver;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserApi extends UserApiGrpc.UserApiImplBase {

    private final UserService userService;

    @Override
    public void getUser(GetUserRequest request, StreamObserver<UserDto> responseObserver) {
        userService.getUser(request.getId())
                .map(this::mapToDto)
                .subscribe(userDto -> {
                    responseObserver.onNext(userDto);
                    responseObserver.onCompleted();
                });
    }

    @Override
    public void saveUser(UserDto request, StreamObserver<UserDto> responseObserver) {
        userService.saveUser(mapToUser(request))
                .map(this::mapToDto)
                .subscribe(userDto -> {
                    responseObserver.onNext(userDto);
                    responseObserver.onCompleted();
                });
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
