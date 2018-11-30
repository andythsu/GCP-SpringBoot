dockerCmd="docker run -it -d -e GOOGLE_CLOUD_PROJECT='andyproject-220200' -e GOOGLE_APPLICATION_CREDENTIALS='GCP_service_account.json' -p 80:80 --name gcp -t gcp"
echo "docker cmd: " $dockerCmd
$dockerCmd