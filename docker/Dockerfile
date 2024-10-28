FROM openjdk:23-ea-21-jdk-slim-bookworm
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar","web"]
EXPOSE 8080

#
# FROM ubuntu:24.10
#
# RUN apt-get update
# RUN apt install openjdk-21-jdk -y
# # ENV JAVA_HOME="/opt/corretto-21.0.4"
#
# WORKDIR /app
#
# COPY ./ ./
# # src pom.xml mvnw .mvn/ ./
#
#
# EXPOSE 8000
# RUN ./mvnw spring-boot:compile
# CMD ["./mvnw", "spring-boot:"]
#
# # CMD [ "/bin/bash", "-c", "--", "while true; do sleep 30; done;" ]
#
#
