FROM holidaykang/yomankum:latest
LABEL authors="holiday <holiday.k1@icloud.com>"
# For ubuntu
RUN apt-get update && apt-get install apache2 -y
VOLUME ["/var/lib/yomankum"]
COPY build/libs/yomankum-0.0.1-SNAPSHOT.jar yomankum.jar
ENTRYPOINT ["java", "-jar", "/yomankum.jar"]