FROM openjdk:11

USER root

ARG APP_HOME=/opt/health

COPY target/health-@project.version@.jar /tmp/

RUN	mkdir ${APP_HOME} && mv /tmp/health-@project.version@.jar ${APP_HOME}

WORKDIR ${APP_HOME}

CMD ["java", "-jar", "health-@project.version@.jar"]
