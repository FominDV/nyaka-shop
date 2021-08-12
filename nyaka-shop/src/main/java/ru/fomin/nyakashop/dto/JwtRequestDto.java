package ru.fomin.nyakashop.dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String email;
    private String password;
}
