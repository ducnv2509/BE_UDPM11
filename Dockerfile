# Docker Build Stage
FROM maven:3-amazoncorretto-11 AS build


# Copy folder in docker
WORKDIR /opt/app

COPY ./ /opt/app
RUN mvn clean install -DskipTests


# Run spring boot in Docker
#FROM openjdk:11
#
#COPY --from=build /opt/app/*.jar app.jar

#ENV PORT 8083
#EXPOSE $PORT
#
#ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","app.jar"]