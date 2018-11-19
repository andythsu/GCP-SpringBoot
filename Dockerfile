FROM openjdk:8-jre

MAINTAINER Andy Su <andythsu.98@gmail.com>

VOLUME /tmp

ADD target/SpringBoot-0.0.1-SNAPSHOT.jar target/app.jar

ENTRYPOINT ["java","-jar","target/app.jar"]
