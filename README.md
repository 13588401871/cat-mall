# 分布式基础篇-全栈开发

这是一个跟随 尚硅谷《谷粒商城》- 2020版课程学习开发的分布式电商项目，主要分为三个阶段。

- 第一阶段：分布式基础篇-全栈开发
  - 快速地开发一个前后端分离的电商系统
  - Spring Boot + Spring Cloud + Vue + Docker + MyBatis Plus
- 第二阶段：分布式高级-微服务架构
  - 打通分布式开发中的所有技术栈
  - 实现一整套的微服务整合，包括秒杀，结算，库存...
- 第三阶段：高可用集群-架构师提升
  - 搭建 Kubernetes 集群，实现全流程 DevOps

![谷粒商城-微服务架构图](https://tva1.sinaimg.cn/large/007S8ZIlly1geblwvpadsj31f10u07dn.jpg)

## 基础环境

### CentOS虚拟机

- 购买云服务器
- 本地虚拟机

### Docker环境

- 安装 MySQL

```shell
$ docker pull mysql:5.7
$ docker run -p 3306:3306 --name mysql \
-v /mydata/mysql/log:/var/log/mysql \
-v /mydata/mysql/data:/var/lib/mysql \
-v /mydata/mysql/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql:5.7

$ docker ps
```

- 配置MySQL编码


```
[client]
default-character-set=utf8

[mysql]
default-character-set=utf8

[mysqld]
init_connect='SET collation_connection = utf8_unicode_ci'
init_connect='SET NAMES utf8'
character-set-server=utf8
collation-server=utf8_unicode_ci
skip-character-set-client-handshake
skip-name-resolve
```

```shell
$ docker restart mysql
$ docker exec -it mysql /bin/bash
```

- 安装 Redis

```shell
$ docker pull redis
$ mkdir -p /mydata/redis/conf
$ touch /mydata/redis/conf/redis.conf
$ docker run -p 6379:6379 --name redis -v /mydata/redis/data:/data \
-v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf \
-d redis redis-server /etc/redis/redis.conf
$ docker ps
$ docker exec -it redis redis-cli
```

- 增加 Redis 持久化

```shell
$ vi /mydata/redis/conf/redis.conf
# appendonly yes
$ docker restart redis
```

- 免费的 mac/windows redis 客户端

[AnotherRedisDesktopManager - GitHub](https://github.com/qishibo/AnotherRedisDesktopManager/releases)

## 开发环境

### 微服务模块

- 项目基础模块: `Product/Ware/Member/Coupon/Order`
- 公共依赖: `common`
- 后台管理模块: `renren-fast`

### 初始化数据库

### 逆向工程

[人人开源主页 - 码云](https://gitee.com/renrenio/)

[人人开源后台管理 - 码云](https://gitee.com/renrenio/renren-fast)

[人人开源前台Vue - 码云](https://gitee.com/renrenio/renren-fast-vue)

[人人开源代码生成器 - 码云](https://gitee.com/renrenio/renren-generator)

### Maven

```xml
<!-- 阿里云镜像 -->
<mirror>
    <id>aliyunmaven</id>
    <mirrorOf>*</mirrorOf>
    <name>阿里云公共仓库</name>
    <url>https://maven.aliyun.com/repository/public</url>
</mirror>
<mirror>
    <id>nexus-aliyun</id>
    <mirrorOf>central</mirrorOf>
    <name>Nexus Aliyun</name>
    <url>https://maven.aliyun.com/nexus/content/groups/public</url>
</mirror>
<mirror>
    <id>aliyunmaven</id>
    <mirrorOf>*</mirrorOf>
    <name>阿里云Google仓库</name>
    <url>https://maven.aliyun.com/repository/google</url>
</mirror>
<mirror>
    <id>aliyunmaven</id>
    <mirrorOf>*</mirrorOf>
    <name>阿里云Apache仓库</name>
    <url>https://maven.aliyun.com/repository/apache-snapshots</url>
</mirror>
<mirror>
    <id>aliyunmaven</id>
    <mirrorOf>*</mirrorOf>
    <name>阿里云Spring仓库</name>
    <url>https://maven.aliyun.com/repository/spring</url>
</mirror>
<mirror>
    <id>aliyunmaven</id>
    <mirrorOf>*</mirrorOf>
    <name>阿里云Spring插件仓库</name>
    <url>https://maven.aliyun.com/repository/spring-plugin</url>
</mirror>
```

```xml
<!-- 编译环境 -->
<profile>
    <id>jdk-1.8</id>
    <activation>
        <activeByDefault>true</activeByDefault>
        <jdk>1.8</jdk>
    </activation>
    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
    </properties>
</profile>
```

### 修改NPM源

```shell
$ npm config set registry http://registry.npm.taobao.org/
$ npm config get registry
$ npm config set registry https://registry.npmjs.org/
```

### 视频16出现的坑 node-sass

```
Module build failed: Error: Node Sass does not yet support your current environment: OS X 64-bit with Unsupported runtime (72)
```

解决办法：

```shell
$ npm uninstall node-sass
$ npm i node-sass --sass_binary_site=https://npm.taobao.org/mirrors/node-sass/
$ npm run dev # 此时可成功
```

## 生成基本CRUD代码

## Spring Cloud Alibaba

- [Spring Cloud Alibaba - GitHub](https://github.com/alibaba/spring-cloud-alibaba)

- `Spring Cloud Alibaba Nacos`: 注册中心(服务发现/注册)，配置中心(动态配置管理)
- `Spring Cloud Ribbon`: 负载均衡
- `Spring Cloud OpenFeign`: 声明式 `HTTP` 客户端，远程服务调用。
- `Spring Cloud Alibaba Sentinel`: 服务容错(限流、降级、熔断)
- `Spring Cloud Sleuth`: 调用链监控
- `Spring Cloud Alibaba Seata`: 分布式事务解决方案

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>2.2.1.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Nacos服务注册与发现

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

```properties
spring.application.name: catmall-coupon # 微服务名
spring.cloud.nacos.discovery.server-addr: localhost:8848 # 注册地址
```

```java
// 主启动类
@EnableDiscoveryClient
```

### OpenFeign使用

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

```java
// 编写接口
@FeignClient("catmall-coupon") # 微服务名
public interface CouponFeign {
    @GetMapping("/coupon/coupon/member/list")
    R memberList();
}

// 主启动类
@EnableFeignClients(basePackages = "edu.dlut.catmall.member.feign")
```

### Nacos配置中心

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

```properties
# bootstrap.properties 启动优先级高于
spring.application.name=catmall-coupon
spring.cloud.nacos.config.server-addr=localhost:8848
```

```java
// Controller 动态刷新
@RefreshScope
```

在`nacos`配置中心添加配置文件 `servicename.properties`

- 命名空间、配置集、配置集ID、配置分组

### Spring Cloud Gateway

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

