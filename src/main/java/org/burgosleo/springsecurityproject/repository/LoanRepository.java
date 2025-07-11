package org.burgosleo.springsecurityproject.repository;

import org.burgosleo.springsecurityproject.model.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    @PreAuthorize("hasRole('USER')")
    List<Loan> findByCustomerIdOrderByStartDtDesc(Long customerId);

}
