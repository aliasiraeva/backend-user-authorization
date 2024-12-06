package ru.itis.backend.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    Long id;
    String username;
    String password;
}
