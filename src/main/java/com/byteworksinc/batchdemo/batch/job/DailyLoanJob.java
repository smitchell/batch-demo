package com.byteworksinc.batchdemo.batch.job;

import com.byteworksinc.batchdemo.batch.repository.LoanDailyRepository;
import com.byteworksinc.batchdemo.domain.Loan;
import com.byteworksinc.batchdemo.domain.LoanDaily;
import javax.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DailyLoanJob {

  private final DailyLoanStepListener dailyLoanStepListener;

  private final LoanDailyRepository loanDailyRepository;

  private final EntityManagerFactory entityManagerFactory;

  @Autowired
  public DailyLoanJob(
      final DailyLoanStepListener dailyLoanStepListener,
      final EntityManagerFactory entityManagerFactory,
      final LoanDailyRepository loanDailyRepository) {
    this.dailyLoanStepListener = dailyLoanStepListener;
    this.entityManagerFactory = entityManagerFactory;
    this.loanDailyRepository = loanDailyRepository;
  }

  @Bean
  public Job dailyLoanBalanceJob(JobBuilderFactory jobs, Step s1) {
    log.debug("dailyLoanBalanceJob <--- " + jobs + ", " + s1);

    return jobs.get("dailyLoanBalanceJob")
        .flow(s1)
        .end()
        .build();
  }

  @Bean
  public JpaPagingItemReader<Loan> loanReader() throws Exception {
    log.debug("loanReader()");
    String jpqlQuery = "SELECT l FROM Loan l";
    JpaPagingItemReader<Loan> reader = new JpaPagingItemReader<>();
    reader.setQueryString(jpqlQuery);
    reader.setEntityManagerFactory(entityManagerFactory);
    reader.setPageSize(1000);
    reader.afterPropertiesSet();
    reader.setSaveState(true);

    return reader;
  }

  @Bean
  public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Loan> reader,
      ItemWriter<LoanDaily> writer, ItemProcessor<Loan, LoanDaily> processor) {
    log.debug("step1()");
    return stepBuilderFactory.get("step1")
        .listener(dailyLoanStepListener)
        .<Loan, LoanDaily>chunk(1000)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }

  @Bean
  public ItemProcessor<Loan, LoanDaily> processor() {
    return new LoanProcessor();
  }

  @Bean
  public RepositoryItemWriter<LoanDaily> writer() {
    log.debug("writer()");
    RepositoryItemWriter<LoanDaily> writer = new RepositoryItemWriter<>();
    writer.setRepository(loanDailyRepository);
    writer.setMethodName("save");
    return writer;
  }

}
