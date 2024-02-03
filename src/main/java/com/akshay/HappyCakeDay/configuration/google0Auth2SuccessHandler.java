package com.akshay.HappyCakeDay.configuration;

import com.akshay.HappyCakeDay.models.Role;
import com.akshay.HappyCakeDay.models.User;
import com.akshay.HappyCakeDay.repository.RolesRepository;
import com.akshay.HappyCakeDay.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component

public class google0Auth2SuccessHandler implements AuthenticationSuccessHandler {
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private RolesRepository rolesRepository;

        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            String email = token.getPrincipal().getAttributes().get("email").toString();
            if (userRepository.findUserByEmail(email).isPresent()) {

            }
            else {
                User user = new User();
                user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
                user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
                user.setEmail(email);
                List<Role> roles = new ArrayList<>();
                Optional<Role> optionalRole = rolesRepository.findById(2);

                if (optionalRole.isPresent()) {
                    Role role = optionalRole.get();
                    roles.add(role);
                    userRepository.save(user);
                }


            }
            redirectStrategy.sendRedirect(request, response, "/register");


        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
            AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
        }
    }


