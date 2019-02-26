package com.byteworksinc.batchdemo.domain.projection;

import com.byteworksinc.batchdemo.domain.LoanDaily;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "loanDailyView", types = {LoanDaily.class})
public interface LoanDailyView {

  String NAME = "loanDailyView";

  String getId();

  Date getAsOfTime();

  String getLoanNumber();

  BigDecimal getBalance();

  String getStatus();

  Date getCreatedDate();

  Date getLastModifiedDate();

  String getCreatedBy();

  String getUpdatedBy();
}
