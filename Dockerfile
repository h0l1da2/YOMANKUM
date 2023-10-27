FROM ubuntu:latest
LABEL authors="holiday"
RUN apt-get update && apt-get install apache2 -y
ENTRYPOINT ["top", "-b"]