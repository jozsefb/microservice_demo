package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class User {

    @Id
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
}
