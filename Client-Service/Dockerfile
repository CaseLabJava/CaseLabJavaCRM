FROM eclipse-temurin:17-alpine
WORKDIR .
COPY Client-Service/target/Client-Service-0.0.1-SNAPSHOT.jar Client-Service.jar

ENTRYPOINT ["java","-jar","Client-Service.jar"]