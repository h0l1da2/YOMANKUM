FROM ubuntu:latest
LABEL authors="holiday <holiday.k1@icloud.com>"
# For ubuntu
RUN apt-get update && apt-get install apache2 -y
# For MySQL
RUN apt-get install -y mysql-server
EXPOSE 3306
ENV MYSQL_ROOT_PASSWORD in8282
ENV MYSQL_DATABASE yomankum
ENV MYSQL_HOST mysql
ENV MYSQL_USER yomankum
ENV MYSQL_PASSWORD in8282
VOLUME ["/var/lib/mysql"]
ENTRYPOINT ["top", "-b"]