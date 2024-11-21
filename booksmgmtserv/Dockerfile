FROM gradle:8.11.1-jdk-alpine AS builder
ENV APP_DIR=/app
WORKDIR ${APP_DIR}
COPY build.gradle settings.gradle ${APP_DIR}/
RUN gradle build -x test --no-daemon || return 0
COPY . .
RUN gradle build -x test --no-daemon


FROM amazoncorretto:17-alpine3.17
ENV ARTIFACT_NAME=booksmgmtserv-0.0.1-SNAPSHOT.jar
ENV APP_DIR=/app
WORKDIR ${APP_DIR}
COPY --from=builder ${APP_DIR}/build/libs/* ./
EXPOSE 8080
SHELL ["/bin/ash", "-c"]
ENTRYPOINT java -jar ${ARTIFACT_NAME}
