FROM gcr.io/google-appengine/openjdk

MAINTAINER Andy Su <andythsu.98@gmail.com>

# RUN apt-get -y update && apt-get install -y \
#  	default-jdk \
#  	maven

VOLUME /tmp

WORKDIR /tmp

# ADD pom.xml pom.xml

# RUN mvn package

ADD target/SpringBoot-0.0.1-SNAPSHOT.jar app.jar

ADD gcp_config/GCP_service_account.json GCP_service_account.json

EXPOSE 80

ENTRYPOINT ["java","-jar","app.jar"]
