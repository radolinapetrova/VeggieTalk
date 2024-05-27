## Use an official Maven image as a parent image
#FROM maven:3.8.4-openjdk-21-slim AS build
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the pom.xml file to the working directory
#COPY ./pom.xml .
#
## Copy the rest of the application code
#COPY ./src ./src
#
## Build the Maven project
#RUN mvn clean install -DskipTests
#
## Debug: List contents of target directory
#RUN ls -la ./target
#
## Use another lightweight image as a base image
#FROM openjdk:21-jdk-slim AS runtime
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the built artifact from the build stage to the runtime stage
#COPY --from=build /app/target/*.jar ./app.jar
#
## Specify the command to run your application
#CMD ["java", "-jar", "app.jar"]

FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/*.jar /app/post_service.jar
#
## Expose port 8080 (assuming your application listens on port 8080)
#EXPOSE 8080

# Set the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "post_service.jar"]
