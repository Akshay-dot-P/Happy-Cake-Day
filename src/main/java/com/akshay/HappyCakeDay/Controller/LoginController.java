package com.akshay.HappyCakeDay.Controller;

import com.akshay.HappyCakeDay.models.Role;
import com.akshay.HappyCakeDay.models.User;
import com.akshay.HappyCakeDay.repository.RolesRepository;
import com.akshay.HappyCakeDay.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller

public class LoginController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;



    @GetMapping("/showlogin")
    public String showLoginForm() {
        return "login";

    }

    @PostMapping("/login")
    public String loginprocess() {
        // Your login logic here
        return "redirect:/home";
    }



    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, HttpServletRequest request) throws Exception {
        // You should perform validation, hashing of passwords, and other necessary steps here
        //userRepository.save(user);
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();
        roles.add(rolesRepository.findById(2).get());
        user.setRoles(roles);
        userRepository.save(user);
        request.login(user.getEmail(), password);
        return "redirect:/";
    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
