SpringBoot

## Run the application locally

1. Set the correct Cloud SDK project via `gcloud config set project
   YOUR_PROJECT` to the ID of your application.
1. Run `mvn spring-boot:run`
1. Visit http://localhost:8080

## Install Google App engine SDK for Java


```
gcloud components update app-engine-java
gcloud components update
```

## Deploy to App Engine flexible environment

1. Make sure gcloud is pointing at right project by running `gcloud config set project [YOUR_PROJECT_ID]`
1. `mvn appengine:deploy`
1. Visit `http://YOUR_PROJECT.appspot.com`.
