FROM openjdk:11
ADD target/atm-service.jar atm-service.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "atm-service.jar"]
