Для проверки livenessProbe и readinessProb при rolling update в репозиторий dokcer hub добавлены 2 версии приложения: 1.0 и 2.0

Включаем ingress
```
# minikube addons enable ingress

# kubectl get pods -n kube-system
NAME                                        READY   STATUS      RESTARTS   AGE
coredns-74ff55c5b-pmgp2                     1/1     Running     2          4d21h
etcd-minikube                               1/1     Running     2          4d21h
ingress-nginx-admission-create-nbpvw        0/1     Completed   0          55m
ingress-nginx-admission-patch-p97zw         0/1     Completed   0          55m
ingress-nginx-controller-558664778f-krs99   1/1     Running     0          55m
kube-apiserver-minikube                     1/1     Running     2          4d21h
kube-controller-manager-minikube            1/1     Running     2          4d21h
kube-proxy-mvdfb                            1/1     Running     2          4d21h
kube-scheduler-minikube                     1/1     Running     2          4d21h
storage-provisioner                         1/1     Running     3          4d21h
```

Ingress включен и запущен

Запуск осуществялется командой:
```
kubectl apply -f .
```

Для получения url сервиса выполним команду:
```
# minikube service health-app-service --url

* Starting tunnel for service health-app-service.
|-----------|--------------------|-------------|------------------------|
| NAMESPACE |        NAME        | TARGET PORT |          URL           |
|-----------|--------------------|-------------|------------------------|
| default   | health-app-service |             | http://127.0.0.1:64790 |
|-----------|--------------------|-------------|------------------------|
http://127.0.0.1:64790
! Because you are using a Docker driver on windows, the terminal needs to be open to run it.
```

С локальной машины приложение будет доступно по адресу http://127.0.0.1:64790