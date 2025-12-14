FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build

COPY common ./common

RUN mvn -f common/pom.xml clean install -DskipTests

COPY subscription-service ./subscription-service

RUN mvn -f subscription-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

EXPOSE 8080

COPY --from=build /build/subscription-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
