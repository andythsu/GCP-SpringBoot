FROM gcr.io/google-appengine/openjdk

LABEL MAINTAINER="Andy Su <andythsu.98@gmail.com>" 

# RUN apt-get -y update && apt-get install -y \
#  	default-jdk \
#  	maven

# Downloading gcloud package
# RUN curl https://dl.google.com/dl/cloudsdk/release/google-cloud-sdk.tar.gz > /tmp/google-cloud-sdk.tar.gz

# Installing the package
# RUN mkdir -p /usr/local/gcloud \
#   && tar -C /usr/local/gcloud -xvf /tmp/google-cloud-sdk.tar.gz \
#   && /usr/local/gcloud/google-cloud-sdk/install.sh

# Adding the package path to local
# ENV PATH $PATH:/usr/local/gcloud/google-cloud-sdk/bin

VOLUME /tmp

WORKDIR /tmp

# ADD pom.xml pom.xml

# RUN mvn package

ADD target/SpringBoot-0.0.1-SNAPSHOT.jar app.jar

ADD gcp_config/GCP_service_account.json GCP_service_account.json

# RUN gcloud auth activate-service-account --key-file GCP_service_account.json

# RUN gcloud config set project andyproject-220200

EXPOSE 80

ENTRYPOINT ["java","-jar","app.jar"]
