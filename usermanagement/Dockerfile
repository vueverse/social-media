# Stage 1: Build the application using the latest Maven version with JDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS build


# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code to the container
COPY src ./src

# Build the application (skip tests if needed)
RUN mvn clean package -DskipTests

# Stage 2: Run the application using Eclipse Temurin JDK 21
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/usermanagement-0.0.1.jar app.jar

# Expose the port on which your Spring Boot application runs
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
