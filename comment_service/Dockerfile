FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY ./pom.xml .

COPY ./src ./src

RUN mvn clean install -DskipTests

RUN ls -la ./target

FROM openjdk:21-jdk-slim AS runtime

WORKDIR /app

COPY --from=build /app/target/*.jar ./app.jar

CMD ["java", "-jar", "app.jar"]

