package com.example.finaltest2.user;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {

    private String username;
    private String password;
    private String password2;
    @Email
    private String email;
}
