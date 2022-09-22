# syntax=docker/dockerfile:1
FROM gradle:7.3.3 AS Build
COPY . /AvilonSubBot
WORKDIR /AvilonSubBot
RUN ["gradle", "build"]
FROM amazoncorretto:17.0.4 AS Execute
RUN ["mkdir", "app"]
COPY --from=Build /AvilonSubBot /app
ENTRYPOINT ["java", "-jar", "app/build/libs/AvilonSubBot-all.jar"]