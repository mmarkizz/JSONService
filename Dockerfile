FROM openjdk:17-jdk-slim-buster
WORKDIR /JSONService
COPY /target/JSONService-0.0.1-SNAPSHOT.jar /JSONService/jsonservice.jar
ENTRYPOINT ["java", "-jar", "jsonservice.jar"]
