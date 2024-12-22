FROM openjdk:21-slim

ENV TZ=Asia/Seoul

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]