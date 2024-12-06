package ru.itis.backend.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Session {
    private Long userId;
    private String token;
}
