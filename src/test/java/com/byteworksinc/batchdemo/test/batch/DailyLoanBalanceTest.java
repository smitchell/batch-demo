package com.byteworksinc.batchdemo.test.batch;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import com.byteworksinc.batchdemo.batch.repository.LoanDailyRepository;
import com.byteworksinc.batchdemo.batch.repository.LoanRepository;
import com.byteworksinc.batchdemo.domain.Loan;
import com.byteworksinc.batchdemo.domain.LoanDaily;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ComponentScan("com.byteworksinc.batchdemo.test")
@WithMockUser(username = "00000000-0000-0000-0000-000000000000")
public class DailyLoanBalanceTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Autowired
  private LoanRepository loanRepository;

  @Autowired
  private LoanDailyRepository loanDailyRepository;

  @Before
  @Transactional
  public void runBefore() {
    loanDailyRepository.deleteAllWithQuery();
  }

  @Test
  public void launchJob() throws Exception {

    //testing a job
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();

    //Testing a individual step
    //JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");

    assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

    List<Loan> loans = loanRepository.findAll();
    List<LoanDaily> loanDailies = loanDailyRepository.findAll();
    assertNotNull(loanDailies);
    assertTrue(!loanDailies.isEmpty());
    assertEquals(loans.size(), loanDailies.size());
  }

}
