​                      

​                                         Kubernetes 学习笔记

 常用命令

```powershell
查看default命名空间下的pods
kubectl get pods
查看kube-system命名空间下的pods
kubectl get pods -n kube-system
查看所有命名空间下的pods
kubectl get pod --all-namespaces
kubectl get pod -A
以纯文本输出格式列出所有pod ,并包含附加信息(如节点名)
kubectl get pods -o wide
```

**运行pod**

```powershell
在default命名空间中创建一个pod副本的deployment
kubectl run tomcat9-test --image=tomcat:9.0.20-jre-alpine --port=8080
kubectl get pod
kubectl get pod -o wide
```

**扩容**

```powershell
kubectl scale --replicas=3 deployment/tomcat9-test
kubectl get deployment
kubectl get deployment -o wide
```

**创建服务**

```powershell
kubectl expose deployment tomcat9-test --name=tomcat9-svc --port=8888 --target-port=8080 --protocol=TCP --type=NodePort
kubectl get svc 
kubectl get svc -o wide
```

**delete 命令**

```
kubectl delete -f pod.yaml 
 删除所有 pod，包括未初始化的 pod
 kubectl delete pods --all
```

**进入容器**

```
kubectl exec -ti <pod-name> /bin/bash
```



**logs命令**

```
# 从 pod 返回日志快照
kubectl logs <pod-name>
# 从 pod <pod-name> 开始流式传输日志。这类似于 'tail -f' Linux 命令。
kubectl logs -f <pod-name>
```

**格式化输出**

```
将pod信息格式化输出到一个yaml文件 
kubectl get pod web-pod-13je7 -o yaml
```

**强制删除pod**

```
强制删除一个pod 
--force --grace-period=0
```

