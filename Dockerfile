FROM openjdk:8
COPY target/zfile-3.1.jar /usr/local/zfile/zfile.jar
WORKDIR /usr/local/zfile
CMD java -Dfile.encoding=utf-8 -jar -Dserver.port=8088 -Dspring.profiles.active=prod-mysql -Dzfile.db.host=$DB_HOST -Dzfile.db.name=$DB_NAME -Dzfile.db.user=$DB_USER -Dzfile.db.pwd=$DB_PWD   zfile.jar