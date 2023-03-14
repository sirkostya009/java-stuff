FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/javastuff-0.0.1-SNAPSHOT.jar /app

CMD ["java", "-jar", "javastuff-0.0.1-SNAPSHOT.jar"]
