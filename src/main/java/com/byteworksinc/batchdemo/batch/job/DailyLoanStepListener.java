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
    StringBuilder sb = new StringBuilder("\nStepExecutionListener - afterStep\n");
    sb.append("getCommitCount=").append(stepExecution.getCommitCount()).append("\n");
    sb.append("getFilterCount=").append(stepExecution.getFilterCount()).append("\n");
    sb.append("getProcessSkipCount=").append(stepExecution.getProcessSkipCount()).append("\n");
    sb.append("getReadCount=").append(stepExecution.getReadCount()).append("\n");
    sb.append("getReadSkipCount=").append(stepExecution.getReadSkipCount()).append("\n");
    sb.append("getRollbackCount=").append(stepExecution.getRollbackCount()).append("\n");
    sb.append("getWriteCount=").append(stepExecution.getWriteCount()).append("\n");
    sb.append("getWriteSkipCount=").append(stepExecution.getWriteSkipCount()).append("\n");
    sb.append("getStepName=").append(stepExecution.getStepName()).append("\n");
    sb.append("getSummary=").append(stepExecution.getSummary()).append("\n");
    sb.append("getStartTime=").append(stepExecution.getStartTime()).append("\n");
    sb.append("getStartTime=").append(stepExecution.getEndTime()).append("\n");
    sb.append("getLastUpdated=").append(stepExecution.getLastUpdated()).append("\n");
    sb.append("getExitStatus=").append(stepExecution.getExitStatus()).append("\n");
    sb.append("getFailureExceptions=").append(stepExecution.getFailureExceptions()).append("\n");
    log.debug(sb.toString());
    return stepExecution.getExitStatus();
  }

}
