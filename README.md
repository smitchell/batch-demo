# Spring Boot/JPA/Batch Demo
### Purpose
This project was created as my test harness modeling work
projects that use Spring Boot, Cloud, JPA, and Batch.

The premise, slightly contrived, is that a company has Loan records that need to be captured at 
the end of each business day. There is a JPA Reader to fetch Loan records, a Processor that 
copies the Loan attributes to a LoanDaily record, and a JPA Writer to capture the state of 
each loan at the close of the business each day.


### Running the Project

This project can be deploy to your PCF Web Space or a Local PCF Dev environment ( 
See https://network.pivotal.io/products/pcfdev).

1. cf login
2. mvn clean install
3. cf push -f cloudfoundry/manifest.yml -p target/batch-demo-0-SNAPSHOT.jar
4. cf logs batch-demo --recent 
5. Repeat step 4 as needed to see Batch logging.
6. cf stop batch-demo

You may temporarily want to change the frequency of the batch job by editing this property in application.yml.

```cron-expression: '0 23 * * * ?'```

This example above runs once a day. You can change that to once a minute to see more 
activity in the logs. Don't leave the service running or you will fill-up the in
member database (I really need to add a step to truncate the table each time it runs).

```cron-expression: '0 * * * * ?'```

Using the expression above, you'll see something like this in the logs

```
Cron Task :: Execution Time - 18:51:00
Cron Task :: Execution Time - 18:52:00
Cron Task :: Execution Time - 18:53:00
```

Each following by something like this:
```
Job: [FlowJob: [name=dailyLoanBalanceJob]] completed with the following parameters: [{}] and the following status: [COMPLETED]
```


