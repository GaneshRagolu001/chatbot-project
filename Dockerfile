# Stage 1: Build with Maven
FROM maven:3.8.4-openjdk AS build

WORKDIR /app

# Copy the pom.xml and download dependencies (to cache them)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source
COPY src ./src

# Package the app
RUN mvn clean package -DskipTests

# Stage 2: Run with OpenJDK
FROM openjdk:17-jdk-slim

# Create app directory
WORKDIR /app

# Copy JAR file from the builder stage
COPY --from=build /app/target/Ai-chatbot-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/Ai-chatbot-0.0.1-SNAPSHOT.jar"]

