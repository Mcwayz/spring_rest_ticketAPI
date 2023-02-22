package com.example.TicketingRestApi.security.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.TicketingRestApi.security.model.User;
import com.example.TicketingRestApi.security.repository.UserRepository;
import com.example.TicketingRestApi.security.service.RoleService;
import com.example.TicketingRestApi.security.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/security/users")
    public String getAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/security/user_list";
    }

    @GetMapping("/security/users/addUser")
    public String addUser()
    {
        return "/security/user_add";
    }

    @GetMapping("/security/user/edit/{id}")
    public String userEdit(@PathVariable Integer id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));
        return "/security/user_edit"; //returns employeeEdit or employeeDetails
    }

    @PostMapping("/users/addNew")
    public RedirectView addNew(User user, RedirectAttributes redir) {
        userService.save(user);

        RedirectView redirectView = new RedirectView("/login", true);
        redir.addFlashAttribute("message", "You have successfully registered a new user!");
        return redirectView;
    }


    @GetMapping("/userProfile")
    public String userProfile(Model model, Principal principal)
    {
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));

        return "user/user_profile";
    }

}
