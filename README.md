# Spring Boot/JPA/Batch Demo
## Purpose
This project was created as my test harness modeling work
projects that use Spring Boot, JPA, and Batch.

The premise, slightly contrived, is that a company has Loan records that need to be captured at 
the end of each business day. There is a JPA Reader to fetch Loan records, a Processor that 
copies the Loan attributes to a LoanDaily record, and a JPA Writer to capture the state of 
each loan at the close of the business each day.


## Running the Project

The easist way to run this project is from your IDE, but you can also deploy to 
PCF Dev or PWS.

### Running the Project Locally

If you use Intellij IDEA all you have to select the Application 
class in the Project window and press Shift-F10, or open the Run menu and select 
"Run BatchDemoApplication," or right-click the Application class and select 
"Run BatchDemoApplication."

### Running the Project with Pivotal

This project can be deployed to Pivotal Cloud Foundry or PCF Dev.
(e.g. PCF or your local PCF Dev environment: see https://network.pivotal.io/products/pcfdev).

* cf login
  * e.g. pcf dev - "cf login -a https://api.dev.cfdev.sh --skip-ssl-validation"
  * e.g. pcf web - "cf login -a https://api.run.pivotal.io"
* mvn clean install
* cf push -f cloudfoundry/manifest.yml -p target/batch-demo-0-SNAPSHOT.jar
* cf logs batch-demo --recent 
* Repeat step 4 as needed to see Batch logging.
* cf stop batch-demo

The batch job is set to run every minute:

```@Scheduled(fixedDelay = 60000, initialDelay = 120000)```

The initialDelay was added to prevent the scheduler from interferring with the JUnit tests.

You'll see something like this in the logs

```
Cron Task :: Execution Time - 18:51:00
Cron Task :: Execution Time - 18:52:00
Cron Task :: Execution Time - 18:53:00
```

Each following by something like this:
```
Job: [FlowJob: [name=dailyLoanBalanceJob]] completed with the following parameters: [{}] and the following status: [COMPLETED]
```


