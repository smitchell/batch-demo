package com.byteworksinc.batchdemo.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


  private final JobBuilderFactory jobs;

  private final StepBuilderFactory steps;

  @Autowired
  public BatchConfiguration(
      final JobBuilderFactory jobs,
      final StepBuilderFactory steps) {
    this.jobs = jobs;
    this.steps = steps;
  }

}
