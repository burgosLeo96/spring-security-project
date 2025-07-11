package org.burgosleo.springsecurityproject.service;

import lombok.RequiredArgsConstructor;
import org.burgosleo.springsecurityproject.model.Loan;
import org.burgosleo.springsecurityproject.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    public List<Loan> getByCustomerId(Long customerId) {
        return loanRepository.findByCustomerIdOrderByStartDtDesc(customerId);
    }

}
