package com.byteworksinc.batchdemo.batch.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchJobScheduler {
  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

  private final JobLauncher jobLauncher;
  private final Job dailyLoanBalanceJob;

  @Autowired
  public BatchJobScheduler(JobLauncher jobLauncher, final Job dailyLoanBalanceJob) {
    log.debug("BatchJobScheduler()");
    this.jobLauncher = jobLauncher;
    this.dailyLoanBalanceJob = dailyLoanBalanceJob;
  }

  @Scheduled(cron = "${batch.cron-expression:0 23 * * * ?}")
  public void scheduleTaskWithCronExpression()
      throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
    log.debug("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        jobLauncher.run(dailyLoanBalanceJob, new JobParametersBuilder().toJobParameters());
  }

}
