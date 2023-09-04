FROM openjdk:17

COPY target/thymeleafdemo-0.0.1-SNAPSHOT.jar thymeleafdemo-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/thymeleafdemo-0.0.1-SNAPSHOT.jar"]

LABEL authors="PRATEEK_M22AIE214"
