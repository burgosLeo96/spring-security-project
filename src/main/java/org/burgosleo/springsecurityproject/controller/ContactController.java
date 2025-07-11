package org.burgosleo.springsecurityproject.controller;

import lombok.RequiredArgsConstructor;
import org.burgosleo.springsecurityproject.model.Contact;
import org.burgosleo.springsecurityproject.repository.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contact")
public class ContactController {

    public static final int UPPER_BOUND = 999999999;
    public static final int LOWER_BOUND = 9999;
    private final ContactRepository contactRepository;

    private static final Random RANDOM = new Random();

    @GetMapping
    public String getContact(){
        return "Contact details from the DB";
    }

    @PostMapping
    // @PreFilter("filterObject.contactName != 'Test'")
    @PostFilter("filterObject.contactName != 'Test'")
    public List<Contact> createContact(@RequestBody List<Contact> contacts){
        if (contacts.isEmpty()) {
            return Collections.emptyList();
        }

        Contact contact = contacts.getFirst();

        contact.setContactId(getReqNumber());
        contact.setCreateDt(new Date(System.currentTimeMillis()));
        return Collections.singletonList(contactRepository.save(contact));
    }

    private static String getReqNumber() {
        return "SR%s".formatted(RANDOM.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND);
    }
}
