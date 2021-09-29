FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
ENV DB_USER DB_PASS JWT 
ARG SPRING_PROFILE="dev"

ARG JAR_FILE="target/e-bank-0.0.1-SNAPSHOT.jar"
RUN addgroup --system --gid 1001 appuser
RUN adduser --system --uid 1001 appuser appuser
COPY --chown=appuser:appuser ${JAR_FILE} app.jar
USER appuser

ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=${SPRING_PROFILE}", "--db.user=${DB_USER}", "--db.pass=${DB_PASS}", "--jwt.sec=${JWT}"]

# docker run --rm -p 8080:8080 -d --env SPRING_PROFILE=prod --env DB_USER=test
# docker run --rm -p 8080:8080 -d --env-file .env.prod
# .env.prod
#   SPRING_PROFILE=prod
#   DB_USER=test
#   DB_PASS=test