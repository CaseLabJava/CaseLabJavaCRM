FROM eclipse-temurin:17-alpine
WORKDIR .
COPY CRM/target/CRM-0.0.1-SNAPSHOT.jar CRM.jar
RUN apk update && apk add curl
ENTRYPOINT ["java","-jar","CRM.jar"]