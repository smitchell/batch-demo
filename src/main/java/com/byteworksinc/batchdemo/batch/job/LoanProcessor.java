package com.byteworksinc.batchdemo.batch.job;

import com.byteworksinc.batchdemo.domain.Loan;
import com.byteworksinc.batchdemo.domain.LoanDaily;
import java.util.Date;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;

public class LoanProcessor implements ItemProcessor<Loan, LoanDaily> {

  @Override
  public LoanDaily process(Loan source) throws Exception {
    final LoanDaily target = new LoanDaily();
    BeanUtils.copyProperties(source, target);
    target.setId(null);
    target.setLoanId(source.getId());
    target.setCreatedDate(new Date());
    target.setLastModifiedDate(new Date());
    return target;
  }
}
