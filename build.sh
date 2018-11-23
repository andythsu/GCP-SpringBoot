if [ "$#" -eq 0 ]
then
    echo "./build.sh <tag_name>"
    exit 1
fi

docker build --tag $1 .

