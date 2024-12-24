---
title: CentOS7离线安装Python3环境
date: 2024-08-09 09:39:45
index_img: /img/2024-08-09_10-46.jpg
excerpt: Linux配置python
tags: 
- Linux
categories:
- Python3
---



## 1.安装环境准备

python3环境依赖包：py3_lib.tar.gz

python3安装包：Python-3.9.9.tar.xz

百度网盘链接：https://pan.baidu.com/s/1ZVPsCw5UtHbWr2VpmidgVw?pwd=w5hi 



## 2.上传python3环境依赖包和安装包，并解压

```shell
# 先创建文件夹
mkdir /usr/local/python3
 
cd /usr/local/python3
# 上传python环境依赖包到此目录下,解压
tar -zxvf py3_lib.tar.gz
#进入解压目录
cd py3
#强制安装全部依赖
rpm -ivh *.rpm --force --nodeps
 
cd /usr/local/python3
# 上传python3安装包到此目录下,解压
tar -xvJf  Python-3.9.9.tar.xz
#进入解压目录
cd Python-3.9.9
#开始安装python3
./configure --prefix=/usr/local/python3
#执行此命令需要gcc	可先查看当前Linux是否安装gcc: gcc -v
make && make install
```

执行成功会出现 **Successfully**

{% asset_img s2.jpg %}



## 3.配置软连接，并检查是否安装成功

```shell
#创建软连接
ln -s /usr/local/python3/bin/python3 /usr/bin/python3
ln -s /usr/local/python3/bin/pip3 /usr/bin/pip3
#验证版本
python3
# 查看版本
python3 -V
pip3 -V
```



## 4.其他情况

执行 ：`./configure --prefix=/usr/local/python3 ` 结束的数据会提示：

{% asset_img s1.jpg %}

出现次状况不要去执行他给的命令，忽略就好，要不然最后的 `make && make install` 会执行失败
