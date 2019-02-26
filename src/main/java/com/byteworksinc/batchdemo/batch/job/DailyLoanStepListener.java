package com.byteworksinc.batchdemo.batch.job;

import com.byteworksinc.batchdemo.batch.repository.LoanDailyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DailyLoanStepListener implements StepExecutionListener {

  private final LoanDailyRepository loanDailyRepository;

  @Autowired
  public DailyLoanStepListener(final LoanDailyRepository loanDailyRepository) {
    this.loanDailyRepository = loanDailyRepository;
  }

  @Override
  public void beforeStep(final StepExecution stepExecutionn) {
    log.debug("beforeStep()");
    loanDailyRepository.deleteAllWithQuery();
  }

  @Override
  public ExitStatus afterStep(final StepExecution stepExecution) {
    log.debug("afterStep()");
    final String summary =
        "StepExecutionListener - afterStep\n"
            + "getCommitCount=" + stepExecution.getCommitCount() + "\n"
            + "getFilterCount=" + stepExecution.getFilterCount() + "\n"
            + "getProcessSkipCount=" + stepExecution.getProcessSkipCount() + "\n"
            + "getReadCount=" + stepExecution.getReadCount() + "\n"
            + "getReadSkipCount=" + stepExecution.getReadSkipCount() + "\n"
            + "getRollbackCount=" + stepExecution.getRollbackCount() + "\n"
            + "getWriteCount=" + stepExecution.getWriteCount() + "\n"
            + "getWriteSkipCount=" + stepExecution.getWriteSkipCount() + "\n"
            + "getStepName=" + stepExecution.getStepName() + "\n"
            + "getSummary=" + stepExecution.getSummary() + "\n"
            + "getStartTime=" + stepExecution.getStartTime() + "\n"
            + "getStartTime=" + stepExecution.getEndTime() + "\n"
            + "getLastUpdated=" + stepExecution.getLastUpdated() + "\n"
            + "getExitStatus=" + stepExecution.getExitStatus() + "\n"
            + "getFailureExceptions=" + stepExecution.getFailureExceptions();
    log.debug(summary);
    return stepExecution.getExitStatus();
  }

}
