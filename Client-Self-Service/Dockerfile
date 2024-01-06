FROM eclipse-temurin:17-alpine
WORKDIR .
COPY  Client-Self-Service/target/Client-Self-Service-0.0.1-SNAPSHOT.jar Client-Self-Service.jar
RUN apk update && apk add curl
ENTRYPOINT ["java","-jar","Client-Self-Service.jar"]