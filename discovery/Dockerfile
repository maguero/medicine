FROM maven:3.6.1-jdk-12 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /build/src/
RUN mvn package

FROM openjdk:8-jdk-alpine as Target
COPY --from=Builder /build/target/discovery-0.0.1-SNAPSHOT.jar discovery.jar
ENTRYPOINT ["java","-jar","discovery.jar"]

EXPOSE 8761