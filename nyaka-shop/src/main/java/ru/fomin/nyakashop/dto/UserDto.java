package ru.fomin.nyakashop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    String firstName;
    String lastName;
    String fatherName;
    String login;
    String email;
    String password;

}
