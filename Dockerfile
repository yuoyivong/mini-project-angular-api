# Use a base image with OpenJDK
FROM alpine/java:22-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY build/libs/spring-data-jpa-homework-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port the app runs on
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]