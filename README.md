# e-bank\_

To run spring app with application-{profile_name}.properties Profile. Default is "dev"

> mvn -Plocal spring-boot:run
> mvn -Pdev spring-boot:run
> mvn -Pprod spring-boot:run

To run spring app with arguments

> mvn -Dspring-boot.run.arguments="--db.user=test --db.pass=test" spring-boot:run

To run jar app with arguments

> java -jar ebank.jar --db.user=test --db.pass=test

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
