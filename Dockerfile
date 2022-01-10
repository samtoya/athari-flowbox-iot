FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} athari-iot-service.jar
ENTRYPOINT ["java", "-jar", "/athari-iot-service.jar"]
EXPOSE 3456