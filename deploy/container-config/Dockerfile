FROM openjdk:11

USER root

ARG APP_HOME=/opt/my_apps/health

COPY health-@project.version@.jar /tmp/

RUN	mkdir -p ${APP_HOME} && mv /tmp/health-@project.version@.jar ${APP_HOME}

WORKDIR ${APP_HOME}

CMD ["java", "-jar", "health-@project.version@.jar"]
