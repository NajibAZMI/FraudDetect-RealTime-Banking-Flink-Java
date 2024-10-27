
FROM openjdk:23-jdk-slim AS build

WORKDIR /app

COPY target/demo-1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
