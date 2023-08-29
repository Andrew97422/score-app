# score-app

Бэкенд и фронтенд - два разных сервиса, которые запускаются отдельно.

Требования для бэкенда:

- OpenJDK 17
- Maven 3+
- Spring Boot 3

Для запуска требуется ввести следующее:

```
mvn clean install
java -jar back/target/back-1.0-SNAPSHOT.jar
```

Требования для фронтенда:

- Angular 16
- NodeJS 18.17.1

Для запуска требуется ввести следующее:

```
npm install
npm run start
```

Для создания docker-образов в директориях /back и /front есть докерфайлы Dockerfile