FROM openjdk:11.0.8-jdk
ENV DB_USER DB_PASS 
ARG SPRING_PROFILE="dev"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=${SPRING_PROFILE}", "--db.user=${DB_USER}", "--db.pass=${DB_PASS}"]

# docker run --rm -p 8080:8080 -d --env SPRING_PROFILE=prod --env DB_USER=test
# docker run --rm -p 8080:8080 -d --env-file .env.prod
# .env.prod
#   SPRING_PROFILE=prod
#   DB_USER=test
#   DB_PASS=test