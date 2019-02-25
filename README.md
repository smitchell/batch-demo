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




