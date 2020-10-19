# e-bank\_

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