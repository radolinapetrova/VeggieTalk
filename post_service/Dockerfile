FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/*.jar /app/post_service.jar

# Expose port 8080 (assuming your application listens on port 8080)
EXPOSE 8081

# Set the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "post_service.jar"]

