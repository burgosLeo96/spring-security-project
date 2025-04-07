package org.burgosleo.springsecurityproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notices")
public class NoticesController {

    @GetMapping
    public String getNotices() {
        return "Notices details from the DB";
    }
}
