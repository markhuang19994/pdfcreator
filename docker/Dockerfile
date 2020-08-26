FROM openjdk:8

WORKDIR /app
COPY ./resources/java /app
COPY ./resources/start.sh /app/start.sh

RUN  ln -s /app/start.sh /usr/bin/start

CMD ["/bin/sh", "-c", "echo 'hello world'"]
