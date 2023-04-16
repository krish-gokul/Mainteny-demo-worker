FROM openjdk:11-stretch
EXPOSE 8000
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT [ "sh", "-c", "java -Dserver.port=8000 -Dlogging.level.root=INFO -Djava.security.egd=file:/dev/./urandom -jar app.jar -spring.config.location=config/application.properties" ]




#FROM eclipse-temurin:17-jre-jammy as builder
#WORKDIR application
#ARG JAR_FILE=build/libs/app.jar
#COPY ${JAR_FILE} app.jar
#RUN java -Djarmode=layertools -jar app.jar extract

#FROM eclipse-temurin:17-jre-jammy
#WORKDIR application
#COPY --from=builder application/dependencies/ ./
#COPY --from=builder application/spring-boot-loader/ ./
#COPY --from=builder application/snapshot-dependencies/ ./
#COPY --from=builder application/application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
