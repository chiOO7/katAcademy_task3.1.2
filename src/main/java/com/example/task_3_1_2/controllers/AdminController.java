package com.example.task_3_1_2.controllers;


import com.example.task_3_1_2.models.User;
import com.example.task_3_1_2.repos.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {
    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/add")
    public String addUserPage(Model model) {
        model.addAttribute("user", new User());
        return "add_user";
    }

    @PostMapping(path = "/add")
    public String addNewUser(@ModelAttribute("user") User user) {
        user.setActive(true);
        user.setUsername(user.getEmail());
        userRepository.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userRepository.findById(id));
        return "edit";
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        user.setActive(true);
        user.setUsername(user.getEmail());
        userRepository.save(user);
        return "redirect:/admin";
    }

    @GetMapping(path = "")
    public String showAllUsers(Model model, Authentication authentication) {
        List<User> users = (List<User>) userRepository.findAll();
        String username = authentication.getName();
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("username", username);
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        return "admin";
    }

    @DeleteMapping(path = "/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }
}
