---
title: Neo4j基础命令
date: 2024-02-26 14:24:38
index_img: /img/2024年2月26日14点23分.jpg
excerpt: Neo4j的基础命令
tags: 
- Neo4j命令
categories:
- Neo4j 
---

###  1.创建一个简单的三国关系数据库

 **使用下面的Cypher语句：**

```cypher
CREATE (n1:`人物` {name: '刘备'}) -[:`关系`{relationship:'兄长'}]-> (n2:`人物` {name: '关羽'}),
(n2) -[:`关系`{relationship:'兄长'}]-> (n3:`人物` {name: '张飞'}),
(n1) -[:`关系`{relationship:'兄长'}]-> (n3),
(n1) -[:`关系`{relationship:'主公'}]-> (n4:`人物` {name: '赵云'}),
(n1) -[:`关系`{relationship:'对手'}]-> (n5:`人物` {name: '曹操'});
```

{% asset_img s1.jpg %}

**说明：**

​	 • 标签名和关系类型是非ASCII字符(例如中文)或者包含空格时，可以用反引号 (`)包含起来；

​	 • 使用变量n1、n2… 来保存和引用创建了的节点； 

​	 • 可以在创建节点和关系的同时创建属性； 

​	 • 关系必须是有向的； • 这里使用通用的’关系’来代表人物之间的关系，仅作示例目的，并没有考虑实际建模的需要。



**在Cypher语句中：** 

​	 • CREATE用来创建节点和属性 • 可以一次性创建多个节点和属性

​	 • 可以使用变量保存创建的节点和属性

​	 • 节点可以拥有一个或多个标签，也可以没有

​	 • 关系必须指定关系类型



### 2.查询图数据库

**使用下面的Cypher语句：**

```cypher
match (n) return n;
或者
match path = () -- ()  return path;
```

得到下面的数据

{% asset_img s2.jpg %}



**二者的区别在于：**

```cypher
match (n) return n;
只返回所有的节点；Neo4j浏览器自动将节点之间的关系也从数据库中读取出来
match path = () -- () return path;
返回所有节点和关系
```



**提示：**可以取消这个选项让 Neo4j 浏览器不再自动读取结果节点中的关系

{% asset_img s3.jpg %}



**下面演示更多的查询案例：**

```cypher
//寻找 刘备 的节点
match (n:人物{name:'刘备'}) retrurn n;
或者
match (n:人物) where n.name = '刘备' return n;

//查询 刘备 的兄弟
match (n:人物{name:'刘备'}) - [:关系{relationship:'兄长'}] -> (n1) return n1;

//寻找 刘备 的小弟（兄弟的兄弟）
match (n:人物{name:'刘备'}) - [:关系*2{relationship:'兄长'}] -> (n1) return n1;

//寻找 刘备 的所有社会关系，关系可以是双向
match (n:人物{name:'刘备'}) - [:关系] -> (n1) return n1;

如果说“兄弟的敌人是我的敌人”，试试看，能不能找到谁是张飞的敌人？
match (n:人物{name:'张飞'})-[:关系{relationship:'兄长'}]-(n1)-[:关系{relationship:'对手'}]-(n2) return n2;
```



### 3.更改和显示节点标签

```cypher
// 为 刘备 增加两个标签
match (n:人物{name:'刘备'}) set n:皇帝:好人;

// 显示 刘备 节点的标签和属性
match (n:人物{name:'刘备'}) return labels(n) AS nodeLabel ,keys(n) AS nodeProperties, n.name;

// 删除 刘备的 好人 标签
match (n:人物{name:'刘备'}) remove n:好人;

// 显示节点的标签和属性，以及关系的类型和属性，并以表状结构返回结果
match (n:人物{name:'刘备'}) -[r] -(n1) 
return type(r) AS relationshipType, keys(r) AS relationshipProperties, labels(n1) AS nodeLabel,
Keys(n1) AS  nodeProperties,n1.name;
```



### 4.更改属性

```cypher
// 为 刘备 增加性别属性
match (n:人物{name:'刘备'}) set n.genre = '男';

// 删除 刘备 的性别标签，当属性值为空（null） 时s'j'k数据库会删除该属性
match (n:人物{name:'刘备'}) set n.genre = null;
```



### 5.删除节点和关系

```cypher
// 如果节点没有关系
match (n:人物{name:'刘备'}) delete n;
        
// 然后节点有关系，使用 detach delete 会先删除连接到该节点的所有关系，然后再删除节点
match (n:人物{name:'刘备'}) detach delete n;
             
// 删除所有节点，如果节点有关系，也删除节点
match (n) detach delete n;

// 仅删除关系，保留节点
match (n:人物{name:'刘备'}) -[r] -() delete r;
```

**提示：**删除全库也可以去删除 neo4j 安装目录下面的 **data/databases/graph.db文件夹**。



### 6.关于 Id 的节点

```cypher
// 根据数据库中的节点 Id 寻找节点
match (n) where id(n) = 0 return n;
```

