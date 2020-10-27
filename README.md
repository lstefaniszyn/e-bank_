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

## Code formatter

Import code formatter into your IDE

Download:  [Format_code_standards](Utility/eclipse_format_code_standards.xml)

### IntelliJ

1. Download the config (attached)
2. In the Intellij settings (Ctrl+Alt+S), go to Code Style
3. Next to Scheme, click Manage and then Import...
4. Select 'Eclipse XML Profile' and browse to the downloaded config file.

### Eclipse

1. Download the config (attached)
2. Navigate to Window->Preferences
3. Navigate to Java->Code Style->Formatter
4. Click on Import and navigate to the downloaded config file
5. Click Apply and Ok

### Visual Studio Code

1. Install the extension [Language Support for Java(TM) by Red Hat](https://marketplace.visualstudio.com/items?itemName=redhat.java).
2. File → Preferences → Settings → java.format.settings.url: Set URL (or local file path) pointing to Eclipse Formatter Profile file.

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

### Running with external exchange rate service

#### Spring Cloud Eureka server

Exchange rate service is implemented as a mocked microservice (`com.example.exchangerate.microservice.ExchangeRateApplication` class) that will register itself in Eureka service discovery server (`com.example.exchangerate.registration.RegistrationServer`).

EBank application can be run without exchange rate service. In this case, no currency conversion is performed.

If you wish to use currency conversion, run `com.example.exchangerate.microservice.ExchangeRateApplication` and `com.example.exchangerate.registration.RegistrationServer` applications.
In this case, `/api/v1/transactions` endpoint will return list of transactions with amounts converted to GBP.

**Example**

Example using data from DEV DB.

```
curl --location --request GET 'localhost:8080/api/v1/transactions?date=2019-01&size=5'

[{"id":377041,"valueDate":"2019-01-05","amount":304.2898786561228,"currency":"GBP","description":"payment CHF"},{"id":377042,"valueDate":"2019-01-21","amount":219.8950045234303,"currency":"GBP","description":"payment CHF"},{"id":377043,"valueDate":"2019-01-11","amount":336.1576287515951,"currency":"GBP","description":"payment CHF"},{"id":377044,"valueDate":"2019-01-27","amount":108.30920607371392,"currency":"GBP","description":"payment CHF"},{"id":377045,"valueDate":"2019-01-25","amount":125.46708661282963,"currency":"GBP","description":"payment CHF"}]
```

## Variables:

### Kafka

```
kafka.host=
```

### RabbitMQ

```
rabbit.hostname=
rabbit.port=
rabbit.user=
rabbit.pass=
```

### Database

```
db.host=
db.user=
db.pass=
```

## Database schema

<img src="Database\RBS-CodingChallenge_DatabaseModel.svg"/>

## Mocked data

Use `mock` profile while running the application to switch to mocked data instead of PostgreSQL.

> mvn -Pmock spring-boot:run

Mocked data are located in json files in: `src/main/resources/data`.

- `accounts.json` contains 100 accounts with subsequent ids.
- `customers.json` contains 152 customers with subsequent ids, transaction list is not present here.
- `transactions_1_1.json` contains 10 transactions per month for each month in period 01/2018 - 12/2019. For one customer and one account, i.e. the same transactions are returned regardless of requested customer and/or account.
