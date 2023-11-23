FROM maven:eclipse-temurin AS builder
ADD . /src
WORKDIR /src
RUN  mvn clean package -Pprod -Dmaven.test.skip

FROM amazoncorretto:17.0.8
COPY  --from=builder /src/target/CaseLabJavaCRM-1.0.0.jar CaseLabJavaCRM.jar

ENTRYPOINT ["java","-jar","CaseLabJavaCRM.jar"]