package com.byteworksinc.batchdemo.batch.repository;

import com.byteworksinc.batchdemo.domain.LoanDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LoanDailyRepository extends JpaRepository<LoanDaily, String> {

  @Modifying
  @Transactional
  @Query("delete from LoanDaily l")
  void deleteAllWithQuery();

}
