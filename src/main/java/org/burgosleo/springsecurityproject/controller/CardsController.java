package org.burgosleo.springsecurityproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/myCards")
public class CardsController {

    @GetMapping
    public String getCardsDetails() {
        return "Cards details from the DB";
    }

}
