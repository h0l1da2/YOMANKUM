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

ENV DATA_URL jdbc:mysql://cube.3trolls.me:13306/yomankum?useSSL=false&characterEncoding=UTF-8
ENV DATA_USERNAME yomankum
ENV DATA_PASSWORD in8282
ENV NAVER_CLIENT_ID VVrviPtw50xif3PUsvby
ENV NAVER_CLIENT_SECRET xGKXY5vHJP
ENV KAKAO_CLIENT_ID de846ae383b9c41af8d14bcc90700379
ENV KAKAO_CLIENT_SECRET otrfLjd6BmlQkRdBGyVSfsNbFDbD1G8t
ENV MAIL_USERNAME y0mankum
ENV MAIL_PASSWORD yomankum1004
ENV MAIL_ID y0mankum@naver.com
ENV MYSQL_ROOT_PASSWORD in8282
ENV MYSQL_USER yomankum
ENV MYSQL_PASSWORD in8282
ENV MYSQL_DATABASE yomankum
ENV REDIS_HOST cube.3trolls.me
ENV REDIS_PORT 16379
ENV REDIS_PASSWORD yomankum1004

# Command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "yomankum.jar"]