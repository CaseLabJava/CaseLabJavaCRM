FROM eclipse-temurin:17-alpine
WORKDIR .
COPY Acquiring-Service/target/Acquiring-Service-0.0.1-SNAPSHOT.jar Acquiring-Service.jar
RUN apk update && apk add curl
ENTRYPOINT ["java", "-jar","Acquiring-Service.jar"]