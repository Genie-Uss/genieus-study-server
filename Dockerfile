FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/*.jar app.jar

# Spring Boot 로그가 stdout으로 출력되도록 보장
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Dlogging.file.name=", "-jar", "app.jar"]