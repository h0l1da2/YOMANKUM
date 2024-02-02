# Stage 1: Build stage using Gradle
FROM openjdk:17-alpine as build

# Set the working directory inside the container
WORKDIR /app

# Copy necessary files for the Gradle build
COPY build.gradle settings.gradle /app/
COPY gradlew /app/gradlew
COPY gradle /app/gradle
COPY src /app/src

# Make the Gradle wrapper executable
RUN chmod +x /app/gradlew

# Run the build process using Gradle wrapper
RUN ./gradlew build --exclude-task test
# RUN ./gradlew build

# Stage 2: Create a minimal image to run the application
FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the previous stage to this new stage
COPY --from=build /app/build/libs/yomankum-0.0.1-SNAPSHOT.jar /app/yomankum.jar

# Expose the port your application runs on (change as per your Spring Boot configuration)
EXPOSE 8080

# Command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "yomankum.jar"]
