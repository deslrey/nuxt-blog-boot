---
title: Linux配置Neo4j
date: 2024-02-07 14:39:40
index_img: /img/2024年2月7日14点49分.jpg
excerpt: Docker配置Neo4j
tags: 
- Neo4j
categories:
- Docker
---

###  1.Docker 镜像准备

我安装的是 **3.5.5**版本的，所以就是 ：docker pull neo4j:3.5.5

```dockerfile
//寻找镜像
docker search neo4j

//拉取镜像
docker pull neo4j(:版本号) //缺省 “:版本号” 时默认安装latest版本的

//查看镜像
docker images
```



### 2、启动配置修改

```dockerfile
docker run -d --name container_name \  //-d表示容器后台运行 --name指定容器名字
    -p 7474:7474 -p 7687:7687 \  //映射容器的端口号到宿主机的端口号
    -v /home/neo4j/data:/data \  //把容器内的数据目录挂载到宿主机的对应目录下
    -v /home/neo4j/logs:/logs \  //挂载日志目录
    -v /home/neo4j/conf:/var/lib/neo4j/conf   //挂载配置目录
    -v /home/neo4j/import:/var/lib/neo4j/import \  //挂载数据导入目录
    --env NEO4J_AUTH=neo4j/password \  //设定数据库的名字的访问密码
    neo4j:4.2.2 //指定使用的镜像 及版本号  默认laster
```

我的运行命令是：NEO4J_AUTH是可以指定的

```kotlin
docker run -d --name neo4j -p 7474:7474 -p 7687:7687 -v /opt/elastdocker-1.19.0/elasticsearch/data/neo4j/data:/data -v /opt/elastdocker-1.19.0/elasticsearch/data/neo4j/logs:/logs -v /opt/elastdocker-1.19.0/elasticsearch/data/neo4j/conf:/var/lib/neo4j/conf -v /opt/elastdocker-1.19.0/elasticsearch/data/neo4j/import:/var/lib/neo4j/import --env NEO4J_AUTH=neo4j/200381 neo4j
```



### 3.查看是否成功运行

**输入：**docker ps 查看以运行的容器

{% asset_img s1.jpg %}



### 4.浏览器查看

http://IP:7474/browser/

{% asset_img s2.jpg %}

