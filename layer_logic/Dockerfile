# Use an official Maven image to build the application
FROM maven:latest AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package

# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/app-1.0.jar app.jar

# Expose the port the app runs on
EXPOSE 80

# Run the application
CMD ["java", "-jar", "app.jar"]
