FROM gradle as builder
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle bootJar


FROM openjdk:8-jdk-alpine
EXPOSE 8081
VOLUME /tmp
ARG targethost=localhost:8080
ENV DATA_IP_ADDRESS=$targethost
ARG LIBS=app/build/libs
COPY --from=builder ${LIBS}/ /app/lib
ENTRYPOINT ["java","-jar","./app/lib/project-auth-service-0.0.1-SNAPSHOT.jar"]