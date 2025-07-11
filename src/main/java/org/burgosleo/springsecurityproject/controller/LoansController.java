package org.burgosleo.springsecurityproject.controller;

import lombok.RequiredArgsConstructor;
import org.burgosleo.springsecurityproject.model.Loan;
import org.burgosleo.springsecurityproject.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/myLoans")
public class LoansController {

    private final LoanService loanService;

    @GetMapping
    @PostAuthorize("hasRole('USER')")
    public ResponseEntity<List<Loan>> getLoansDetails(@RequestParam Long customerId) {
        return ResponseEntity.ok(loanService.getByCustomerId(customerId));
    }

}
