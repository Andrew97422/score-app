FROM maven:3.8.4-openjdk-17 as build

COPY ./target/*.jar application.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/application.jar"]
