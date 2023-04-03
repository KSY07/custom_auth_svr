FROM openjdk:17-alpine3.14
LABEL author="김세영"
ARG version="1.0"
COPY . /authsvr
WORKDIR ./authsvr
RUN chmod +x gradlew
RUN ./gradlew clean build
WORKDIR ./build/libs
EXPOSE 9090
CMD ["java", "-jar", "authsvr-1.0.jar","-Dspring.profiles.active=biz"]
