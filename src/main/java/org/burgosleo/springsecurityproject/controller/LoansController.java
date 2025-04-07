package org.burgosleo.springsecurityproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/myLoans")
public class LoansController {

    @GetMapping
    public String getLoansDetails() {
        return "Loans details from the DB";
    }

}
