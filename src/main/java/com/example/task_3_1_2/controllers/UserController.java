package com.example.task_3_1_2.controllers;


import com.example.task_3_1_2.models.User;
import com.example.task_3_1_2.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping(path = "/user")
//@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    String showUser(Authentication authentication, Model model) {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
            model.addAttribute("username", username);
            model.addAttribute("roles", roles);
            model.addAttribute("user", user);
            return "user";
    }
}
