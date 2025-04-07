package org.burgosleo.springsecurityproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/myAccount")
public class AccountController {

    @GetMapping
    public String getAccountDetails() {
        return "Account details from the DB";
    }

}
