package com.byteworksinc.batchdemo.repository;

import com.byteworksinc.batchdemo.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, String> {

}
