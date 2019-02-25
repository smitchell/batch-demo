package com.byteworksinc.batchdemo.batch.repository;

import com.byteworksinc.batchdemo.domain.LoanDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanDailyRepository extends JpaRepository<LoanDaily, String> {

}
