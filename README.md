Прототип сервиса проверки доступности приложения

Используемый стек технологий:

Java 11
Spring Boot + Spring Web



Docker Image построен на официальном образе https://hub.docker.com/_/openjdk

1. Собрать приложение 
mvn clean install package
2. Собрать образ docker 
docker build target/deploy/container-config/Dockerfile -t health-check:latest .

