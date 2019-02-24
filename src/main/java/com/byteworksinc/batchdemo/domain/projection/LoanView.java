package com.byteworksinc.batchdemo.domain.projection;

import com.byteworksinc.batchdemo.domain.Loan;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "loanView", types = {Loan.class})
public interface LoanView {
  String NAME = "loanView";

  String getId();

  String getLoanNumber();

  BigDecimal getBalance();

  String getStatus();

  Date getCreatedDate();

  Date getLastModifiedDate();

  String getCreatedBy();

  String getUpdatedBy();
}
