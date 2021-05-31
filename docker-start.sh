git pull origin master

mvn clean package

docker stop zfile3
docker rm zfile3
docker rmi zfile3
docker build -t zfile3 .
docker run -p 8088:8088 --name zfile3 -e PROFILES_ACTIVE=prod-mysql -d zfile3