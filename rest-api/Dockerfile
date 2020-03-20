FROM gradle:6.2.2 AS builder
 
COPY . /app
WORKDIR /app
RUN gradle clean build


FROM openjdk:8-jdk-alpine

COPY --from=builder /app/build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]