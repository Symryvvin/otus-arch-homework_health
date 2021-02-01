Применение манифестов (из папки homework):
```
kubectl apply -f .
```

URL для проверки сервиса
```
http://arch.homework/otusapp/ushakov/health

без хоста
curl -H 'Host: arch.homework' http://192.168.49.2/otusapp/ushakov/health
```

Ссылка на [docker-hub/symryvvin](https://hub.docker.com/repository/docker/symryvvin/health-app)


