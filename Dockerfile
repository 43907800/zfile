FROM openjdk:8
COPY target/zfile-3.0.jar /usr/local/zfile/zfile.jar
WORKDIR /usr/local/zfile
CMD java -Dfile.encoding=utf-8 -jar -Dserver.port=8088 -Dspring.profiles.active=$PROFILES_ACTIVE -Dzfile.db.pwd=q4r40k -Dzfile.db.url=vwkj6eq6.2419.dnstoo.com  zfile.jar