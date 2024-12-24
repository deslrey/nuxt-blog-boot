---
title: CentOS安装RabbitMQ
date: 2024-07-11 17:33:37
index_img: /img/2024-07-11_17-44.jpg
excerpt: CentOS安装RabbitMQ
tags: 
  - RabbitMQ
---



### 1、下载安装启动RabbitMQ

#### 	1.1、下载RabbitMQ

​	**下载地址**：https://github.com/rabbitmq/rabbitmq-server/releases?q=3.8.14&expanded=true

{% asset_img s1.jpg %}	

#### 	1.2、下载Erlang

​	**下载地址**：https://packagecloud.io/rabbitmq/erlang/packages/el/7/erlang-23.2.7-2.el7.x86_64.rpm

​	点击右上角的 **Download**

{% asset_img s2.jpg %}	



#### 	1.3、安装Erlang

首先将下载好的文件上传到服务器，创建一个文件夹用来存放文件

{% asset_img s3.jpg %}	

开始解压安装 **erlang**

```shell
# 解压
rpm -Uvh erlang-23.2.7-2.el7.x86_64.rpm

# 安装
yum install -y erlang
```

{% asset_img s4.jpg %}	

安装完成后查看版本号

{% asset_img s5.jpg %}	



#### 1.4、安装Socat

```shell
rpm -ivh socat-1.7.3.2-2.el7.x86_64.rpm
```



{% asset_img s6.jpg %}	



#### 1.5、安装RabbitMQ

```shell
# 解压
rpm -Uvh rabbitmq-server-3.8.14-1.el7.noarch.rpm

# 安装
yum install -y rabbitmq-server
```



{% asset_img s7.jpg %}	



#### 1.6、启动RabbitMQ服务 

```shell
# 启动rabbitmq
systemctl start rabbitmq-server

# 查看rabbitmq状态
systemctl status rabbitmq-server
```

显示`active`则表示服务安装并启动成功

{% asset_img s8.jpg %}	



**其他命令：**

```shell
# 设置rabbitmq服务开机自启动
systemctl enable rabbitmq-server

# 关闭rabbitmq服务
systemctl stop rabbitmq-server

# 重启rabbitmq服务
systemctl restart rabbitmq-server

# 禁止开启启动
systemctl disable rabbitmq-server.service

# 查看rabbitmq开机启动状态 enabled:开启, disabled:关闭
systemctl is-enabled rabbitmq-server.service
```



### 2、RabbitMQWeb管理界面及授权操作

默认情况下，rabbitmq没有安装web端的客户端软件，需要安装才可以生效

```shell
# 查看插件列表
rabbitmq-plugins list

# 打开RabbitMQWeb管理界面插件
rabbitmq-plugins enable rabbitmq_management
```

{% asset_img s9.jpg %}	

然后我们打开浏览器，访问`服务器ip:15672`，就可以看到管理界面

{% asset_img s10.jpg %}	

`rabbitmq`有一个默认的账号密码`guest`，但该情况仅限于本机localhost进行访问，所以需要添加一个远程登录的用户



#### 2.1、添加远程用户

```shell
# 添加用户
rabbitmqctl add_user 用户名 密码

# 设置用户角色,分配操作权限
rabbitmqctl set_user_tags 用户名 角色

# 为用户添加资源权限(授予访问虚拟机根节点的所有权限)
rabbitmqctl set_permissions -p / 用户名 ".*" ".*" ".*"
```

**角色有四种**：

- `administrator`：可以登录控制台、查看所有信息、并对rabbitmq进行管理
- `monToring`：监控者；登录控制台，查看所有信息
- `policymaker`：策略制定者；登录控制台指定策略
- `managment`：普通管理员；登录控制

这里创建用户`root，密码`200381`，设置`adminstator`角色，赋予所有权限

{% asset_img s11.jpg %}	



其他指令：

```shell
# 修改密码
rabbitmqctl change_ password 用户名 新密码

# 删除用户
rabbitmqctl delete_user 用户名

# 查看用户清单
rabbitmqctl list_users
```

