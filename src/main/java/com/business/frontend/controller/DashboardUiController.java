package com.business.frontend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardUiController {

    @GetMapping({"/", "/ui/dashboard"})
    public String dashboard(Authentication auth, HttpSession session, Model model) {
        String username = (String) session.getAttribute("jwt_username");
        if (username == null && auth != null) username = auth.getName();
        model.addAttribute("username", username != null ? username : "Admin");
        return "dashboard";
    }
}