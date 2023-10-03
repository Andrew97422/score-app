FROM maven:3.8.4-jdk-17-slim as build
COPY src /back/src
COPY pom.xml /back/app
RUN mvn -f /back/pom.xml clean package

FROM amazoncorretto:11-alpine-jdk
COPY --from=build /back/target/*.jar back-1.0-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/back-1.0-SNAPSHOT.jar"]