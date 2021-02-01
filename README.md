


#### Заметки

Прототип сервиса проверки доступности приложения

Используемый стек технологий:

 - Java 11
 - Spring Boot
 - Spring Web MVC

Docker Image построен на официальном образе https://hub.docker.com/_/openjdk

Сборка java приложения, docker-контейнера и его запуск
```
mvn clean install package 
docker-compose -f target/docker-compose.yml build
docker-compose -f target/docker-compose.yml up -d
```
Проверка работоспособности приложения в контейнере
```
docker ps

CONTAINER ID   IMAGE                      COMMAND                  CREATED         STATUS         PORTS                    NAMES
cd744ae54971   symryvvin/health-app:1.0   "java -jar health-1.…"   3 seconds ago   Up 2 seconds   0.0.0.0:8080->8080/tcp   health-app

docker exec -it cd744ae54971 bash

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
Все OK, пушим образы в docker-hub
```
docker login -u symryvvin
docker push symryvvin/health:1.0
docker push symryvvin/health:2.0
```

Установка Kubectl & Minikube

https://kubernetes.io/ru/docs/tasks/tools/install-kubectl/
https://kubernetes.io/ru/docs/tasks/tools/install-minikube/

проверка версии и запуск minikube
```
# minikube version
minikube version: v1.17.0
commit: 7e8b5a89575945ba8f8246bfe547178c1a995198

# minikube start
* minikube v1.17.0 on Microsoft Windows 10 Pro 10.0.19041 Build 19041
* minikube 1.17.1 is available! Download it: https://github.com/kubernetes/minikube/releases/tag/v1.17.1
* To disable this notice, run: 'minikube config set WantUpdateNotification false'

* Using the docker driver based on existing profile
* Starting control plane node minikube in cluster minikube
* Preparing Kubernetes v1.20.2 on Docker 20.10.2 ...
* Verifying Kubernetes components...
* Enabled addons: storage-provisioner, default-storageclass
* Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default

# minikube status
minikube
type: Control Plane
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured
timeToStop: Nonexistent
```
создадим пространство имен для приложения и сделаем его текущим, чтобы все команды отображали ресурсы из этого пространства имен
```
kubectl create namespace myapp
kubectl config set-context --current --namespace=myapp
```
запускаем команду отображающую все ресурсы каждый 2 секунды (аналог watch)
```
for /l %g in () do @(cls & kubectl get all & timeout /t 2 )
```
получаем доступы к docker внутри minikube
```
# minikube docker-env
SET DOCKER_TLS_VERIFY=1
SET DOCKER_HOST=tcp://127.0.0.1:49159
SET DOCKER_CERT_PATH=C:\Users\Aizen\.minikube\certs
SET MINIKUBE_ACTIVE_DOCKERD=minikube
REM To point your shell to minikube's docker-daemon, run:
REM @FOR /f "tokens=*" %i IN ('minikube -p minikube docker-env') DO @%i

# SET DOCKER_TLS_VERIFY=1
# SET DOCKER_HOST=tcp://127.0.0.1:49159
# SET DOCKER_CERT_PATH=C:\Users\Aizen\.minikube\certs
# SET MINIKUBE_ACTIVE_DOCKERD=minikube
```
Создаем простейший deploy/manifest/pod.yml (не для провека ДЗ) и проверяем работу приложения в minikube
```
kubectl apply -f pod.yml

# kubectl get pods
NAME         READY   STATUS    RESTARTS   AGE
health-app   1/1     Running   0          3m1s

# kubectl describe pod health-app
Name:         health-app
Namespace:    default
Priority:     0
Node:         minikube/192.168.49.2
Start Time:   Fri, 29 Jan 2021 11:54:22 +0300
Labels:       app=health-app
Annotations:  <none>
Status:       Running
IP:           172.17.0.3
IPs:
  IP:  172.17.0.3
Containers:
  health-app:
    Container ID:   docker://09714b1a4ff1289d5800f393300aeef5f6e8d3b11d92b951ef23df044f97cb29
    Image:          symryvvin/health-app:1.0.0
    Image ID:       docker-pullable://symryvvin/health-app@sha256:5a0ea4768d2e5cc54d1a5f4a03c283148777be2f4c137671e0e79a5ddc8557e8
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Fri, 29 Jan 2021 11:56:31 +0300
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-wvj56 (ro)
Conditions:
  Type              Status
  Initialized       True
  Ready             True
  ContainersReady   True
  PodScheduled      True
Volumes:
  default-token-wvj56:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-wvj56
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                 node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type    Reason     Age    From               Message
  ----    ------     ----   ----               -------
  Normal  Scheduled  5m42s  default-scheduler  Successfully assigned default/health-app to minikube
  Normal  Pulling    5m42s  kubelet            Pulling image "symryvvin/health-app:1.0.0"
  Normal  Pulled     3m35s  kubelet            Successfully pulled image "symryvvin/health-app:1.0.0" in 2m6.8605376s
  Normal  Created    3m34s  kubelet            Created container health-app
  Normal  Started    3m34s  kubelet            Started container health-app

# minikube ssh
docker@minikube:~$ curl -s http://172.17.0.3:8080/health
{"status":"OK"}

kubectl delete -f pod.yml
```
Все работает, оформляем необходимые *.yml согласно ДЗ

Для проверки livenessProbe и readinessProb при rolling update в репозиторий docker hub добавлены 2 версии приложения: 1.0 и 2.0

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

Применяем манифесты
```
# cd homework
# kubectl apply -f .
```

Для получения url сервиса выполним команду:
```
# minikube service health-app-service --url
http://192.168.49.2:32651

# curl -H 'Host: arch.homework' http://192.168.49.2/otusapp/ushakov/health
{"status":"OK"}
```
Добавим 192.168.49.2 arch.homework в файл /etc/hosts и все заработает без заголовка
```
# curl  http://arch.homework/otusapp/ushakov/health
{"status":"OK"}
```