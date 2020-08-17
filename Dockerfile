FROM  ubuntu:bionic-20200713

ENV  UBUNTU_VERSION ubuntu:bionic-20200713
ENV  APP_VERSION datamakerri-1.0.0

RUN  apt-get update \
     && apt-get install -y openjdk-11-jre-headless \
     && mkdir /app

COPY  target/datamakerri-1.0.0.jar  /app/datamakerri-1.0.0.jar

EXPOSE  8080

CMD  ["java", "-jar", "/app/datamakerri-1.0.0.jar"]

