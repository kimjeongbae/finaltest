package com.example.finaltest2.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserForm userForm){
        return "signup_form";
    }

    @PostMapping("/signup")
    public String userSignup(UserForm userForm){
        if(!userForm.getPassword().equals(userForm.getPassword2())){
            System.out.println("비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }
        userService.create(userForm.getUsername(),userForm.getPassword(),userForm.getEmail());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){

        return "login_form";
    }


}
