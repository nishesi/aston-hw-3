# syntax=docker/dockerfile:experimental

FROM maven AS build
COPY src /home/application/src
COPY pom.xml /home/application
USER root
RUN --mount=type=cache,target=/root/.m2 mvn -DskipTests=true -f /home/application/pom.xml clean package

FROM tomcat:10
COPY --from=build /home/application/target/attraction-service-0.0.1.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
WORKDIR $CATALINA_HOME
CMD ["catalina.sh", "run"]