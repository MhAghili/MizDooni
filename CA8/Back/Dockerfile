

# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory for the build
WORKDIR /app

# Copy the pom.xml and the source code to the container
COPY pom.xml .
COPY src ./src

# Run Maven to build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:18-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable WAR file from the build stage
COPY --from=build /app/target/CA6.war /app/CA6.war

# Expose the port the application runs on
EXPOSE 8080

# Run the WAR file
ENTRYPOINT ["java", "-jar", "/app/CA6.war"]
