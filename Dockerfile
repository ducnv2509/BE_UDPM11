## Docker Build Stage
#FROM maven:3-amazoncorretto-11 AS build
#
#
## Copy folder in docker
#WORKDIR /opt/app
#
#COPY ./ /opt/app
#RUN mvn clean install -DskipTests
#
#
#
# Run spring boot in Docker
#FROM openjdk:11
#COPY --from=build /opt/app/target/*.war app.war
#
#ENV PORT 8083
#EXPOSE $PORT
#
#ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","app.war"]

FROM tomcat:8.0.51-jre8-alpine
RUN rm -rf /opt/tomcat/webapps/*
COPY ./target/BE_UDPM_11_V1-0.0.1-SNAPSHOT.war /opt/tomcat/webapps/app.war
CMD ["catalina.sh","run"]