FROM maven:3.8.4-openjdk-17 as build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package
COPY --from=build /usr/src/app/target/*.jar application.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/application.jar"]
