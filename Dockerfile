FROM ubuntu:latest
LABEL authors="holiday"

ENTRYPOINT ["top", "-b"]