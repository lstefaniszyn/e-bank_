# e-bank\_

REST API for an E-Banking Portal for returning list of transactions. Contains following features:

- Java 11
- Spring Boot 2
- Postgres DB
- Swagger 2 API documentation
- MrChecker for integration testing
- JMeter for performace testing
- CI/CD Jenkins pipeline configured
- Two environments (DEV and PROD)

## Endpoints

- Application - http://localhost:8080/
- Swagger UI - http://localhost:8080/swagger-ui.html
- Application status - http://localhost:8080/api/

## Building & running

To run spring app with application-{profile_name}.properties Profile. Default is "dev"

> mvn -Plocal spring-boot:run

> mvn -Pdev spring-boot:run

> mvn -Pprod spring-boot:run

To run spring app on Dev with arguments

> mvn -Pdev -Dspring-boot.run.arguments="--db.user=test --db.pass=test" spring-boot:run

To run jar app with arguments

> java -jar ebank.jar --spring.profiles.active=dev --db.user=test --db.pass=test

## Variables:

### Kafka

- kafka.host=

### RabbitMQ

- rabbit.hostname=
- rabbit.port=
- rabbit.user=
- rabbit.pass=

### DataBase

- db.host=
- db.user=
- db.pass=

## Mocked data

Mocked data are located in json files in: `src/main/resources/data`.

Add `mock` profile while running the application to switch to mocked data instead of PostgreSQL. However, remember that `mock` profile is only a side profile, i.e. it still needs to be used together with one of the main profiles (`local/dev/prod`).

- `accounts.json` contains 100 accounts with subsequent ids.
- `customers.json` contains 152 customers with subsequent ids, transaction list is not present here.
- `transactions_1_1.json` contains 10 transactions per month for each month in period 01/2018 - 12/2019. For one customer and one account, i.e. the same transactions are returned regardless of requested customer and/or account.
