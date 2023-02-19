FROM maven:3.8.7-eclipse-temurin-11 as builder

COPY src /app/src
COPY pom.xml /app

RUN mvn -f /app/pom.xml clean package

FROM openjdk:11
VOLUME /tmp

COPY --from=builder /app/target/f1bot.jar /app/f1bot.jar
ENTRYPOINT ["java", "-jar",  "-agentlib:jdwp=transport=dt_socket,server=y,address=*:4444,suspend=n", "/app/f1bot.jar"]