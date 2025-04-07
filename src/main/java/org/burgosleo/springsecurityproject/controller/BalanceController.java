package org.burgosleo.springsecurityproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/myBalance")
public class BalanceController {

    @GetMapping
    public String getBalanceDetails() {
        return "Balance details from the DB";
    }

}
