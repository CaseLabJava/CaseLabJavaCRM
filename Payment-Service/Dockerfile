FROM eclipse-temurin:17-alpine
WORKDIR .
COPY Payment-Service/target/Payment-Service-0.0.1-SNAPSHOT.jar Payment-Service.jar
RUN apk update && apk add curl
ENTRYPOINT ["java","-jar","Payment-Service.jar"]