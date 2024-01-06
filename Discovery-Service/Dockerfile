FROM eclipse-temurin:17-alpine
WORKDIR .
COPY Discovery-Service/target/Discovery-Service-0.0.1-SNAPSHOT.jar Discovery-Service.jar
RUN apk update && apk add curl
ENTRYPOINT ["java","-jar","Discovery-Service.jar"]