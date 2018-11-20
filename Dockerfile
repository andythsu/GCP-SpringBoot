FROM gcr.io/google-appengine/openjdk

MAINTAINER Andy Su <andythsu.98@gmail.com>

VOLUME /tmp

ADD target/SpringBoot-0.0.1-SNAPSHOT.jar target/app.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","target/app.jar"]
