FROM eclipse-temurin:17-alpine
WORKDIR .
COPY Client-Service/target/Client-service-0.0.1-SNAPSHOT.jar Client-Service.jar

ENTRYPOINT ["java","-jar","Client-Service.jar"]