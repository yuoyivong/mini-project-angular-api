# Use an official OpenJDK runtime as the base image
# Example if your project are using jdk17 then change its base image to version 17
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file and any other necessary files
COPY target/*.jar /app/spring.jar

# Expose the port on which your Spring application will run (change as per your application)
EXPOSE 8081
ENV sring.datasource.url=jdbc:postgresql://110.74.194.123:5432/monster

# Set the command to run your Spring application when the container starts
CMD ["java", "-jar", "/app/spring.jar"]
