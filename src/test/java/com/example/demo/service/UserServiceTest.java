package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    /**
     * @verifies save a user to the db
     * @see UserService#saveUser(com.example.demo.model.User)
     */
    @Test
    void saveUser_shouldSaveAUserToTheDb() {
        //given
        User user = User.builder()
                .firstName("Test")
                .lastName("Name")
                .email("test@test.com")
                .build();
        User expectedUser = user.toBuilder().id(UUID.randomUUID()).build();
        doReturn(Mono.just(expectedUser)).when(userRepository).save(user);
        //when & then
        StepVerifier.create(userService.saveUser(user))
                .assertNext(savedUser -> assertEquals(savedUser, expectedUser))
                .expectComplete();
        Mockito.verify(userRepository).save(any());
    }
}
