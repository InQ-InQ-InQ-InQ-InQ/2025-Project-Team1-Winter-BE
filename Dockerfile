FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/team1-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /photo.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/photo.jar"]