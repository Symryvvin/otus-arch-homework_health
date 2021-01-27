


####Заметки

Прототип сервиса проверки доступности приложения

Используемый стек технологий:

 - Java 11
 - Spring Boot
 - Spring Web MVC

Docker Image построен на официальном образе https://hub.docker.com/_/openjdk

Сборка java приложения, docker-котейнера и его запуск
```
mvn clean install package 
docker-compose -f target/docker-compose.yml build
docker-compose -f target/docker-compose.yml up -d
```
Проверка работоспособности приложения в контейнере
```
docker ps

CONTAINER ID   IMAGE          COMMAND                  CREATED         STATUS         PORTS                    NAMES
a62e9f121126   health:1.0.0   "java -jar health-1.…"   2 minutes ago   Up 2 minutes   0.0.0.0:8080->8080/tcp   health_check

docker exec -it a62e9f121126 bash

root@a62e9f121126:/opt/my_apps/health# curl localhost:8080/health
{"status":"OK"}
```
Проверка работы с локального ПК
```
curl http://localhost:8080/health

StatusCode        : 200
StatusDescription :
Content           : {"status":"OK"}
```
Все OK, пушим образ в docker-hub
```
docker login -u symryvvin
docker tag health:1.0.0 symryvvin/health:1.0.0
docker push symryvvin/health:1.0.0
```
