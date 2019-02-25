package com.byteworksinc.batchdemo.test.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.byteworksinc.batchdemo.batch.repository.LoanRepository;
import com.byteworksinc.batchdemo.domain.Loan;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Random;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
public class LoanRepositoryTest {
  public static final String UUID_PATTERN = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$";

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private LoanRepository loanRepository;

  private MockMvc mockMvc;

  @Before
  public void setMockMvc(){
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testAchTransaction() throws Exception {
    final Loan loan = generateLoan();
    String objectString = new ObjectMapper().writeValueAsString(loan);
    MvcResult create = mockMvc.perform(
        post("/loans/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectString))
        .andExpect(status().is2xxSuccessful())
        .andExpect(header().string("Location", containsString(
            "http://localhost/loans/"))) // Should be http://localhost/user/{valid-uuid}
        .andReturn();

    String loanId = create.getResponse().getHeader("Location")
        .split("/")[4]; // The part of the location after the 4th slash
    Assert.assertTrue(
        loanId.matches(UUID_PATTERN)); //Make sure {valid-id} matches the pattern

    mockMvc.perform(
        get("/loans/" + loanId))
        .andExpect(status().is2xxSuccessful())
        .andExpect(
            header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$.loanNumber", is(loan.getLoanNumber())))
        .andExpect(jsonPath("$.balance", is(loan.getBalance().doubleValue())))
        .andExpect(jsonPath("$.status", is("ACTIVE")));

  }

  private Loan generateLoan() {
    int max = 5000;
    int min = 1;
    Random rand = new Random();
    final Loan loan = new Loan();
    loan.setLoanNumber(RandomStringUtils.randomAlphabetic(10));
    loan.setBalance(BigDecimal.valueOf(rand.nextInt((max - min) + 1) + min));
    return loan;
  }
}
