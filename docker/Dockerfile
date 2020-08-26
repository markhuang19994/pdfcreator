FROM openjdk:8

WORKDIR /app
COPY ./resources/java /app
COPY ./resources/init /init
COPY ./resources/start.sh /app/start.sh

RUN  ln -s /app/start.sh /usr/bin/start && \
     chown -R 1000:1000 /init

USER 1000:1000
CMD ["/bin/sh", "-c", "echo 'hello world'"]
