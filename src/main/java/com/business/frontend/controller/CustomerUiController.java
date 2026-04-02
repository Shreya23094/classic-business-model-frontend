package com.business.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/ui")
public class CustomerUiController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/customers")
    public String getCustomers(
            @RequestParam(required = false) String country,
            Model model) {

        if (country != null) {
            String url = "http://localhost:8085/api/customers?country="
                    + country + "&page=0&size=5";

            Object response = restTemplate.getForObject(url, Object.class);
            model.addAttribute("customers", response);
        }

        return "customers";
    }
}