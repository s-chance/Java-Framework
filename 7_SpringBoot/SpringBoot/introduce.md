## SpringBoot概述

### 1.简介

**Spring框架**基本成为了Java在Web开发领域不可或缺的核心, 使得Java在互联网开发中发挥了相当大的作用。而随着发展以及需求, 在Spring框架的基础上又开发了**SpringBoot框架**

**SpringBoot框架**是Spring官方的子项目, 是基于Spring框架开发的全新框架, 进一步简化了Spring的初始化配置及环境搭建过程, 极大提高了开发效率。

**SpringBoot框架**整合了许多框架和第三方库配置, 几乎能够实现开箱即用

### 2.优势

参考[原文](https://zhuanlan.zhihu.com/p/373263378)

- 能够快速构建独立的Spring应用程序
- 本身内嵌Tomcat、Jetty和Undertow服务器
- 提供依赖启动器简化构建配置
- 极大程度上自动化配置Spring和第三方库
- 提供生产就绪功能
- 极少的代码生成和xml配置

### 3.简化开发的方面

1. 基于POJO的轻量级和最小侵入式编程, 所有的对象方法都表示为bean
2. 通过IOC、DI和面向接口实现松耦合
3. 基于切面AOP和惯例进行声明式编程
4. 通过切面和模板减少重复性代码

###  4.创建SpringBoot项目

#### 1.开发环境

- **Jdk1.8**
- **Maven3.5.2**
- **SpringBoot2.7.6**(2.x版本即可, 3.x版本需要jdk17, 本文档不考虑3.x版本)

#### 2.新建项目

方式一: 通过普通maven项目改造为SpringBoot项目

方式二: 可以直接使用Spring官方工具Spring Initializr：https://start.spring.io/

使用Spring Initializr的Web页面创建项目

1.打开  https://start.spring.io/ 并填写项目信息

2.点击”Generate Project“按钮生成项目, 并下载此项目

4.解压项目包, 并用IDEA以Maven项目导入, 一路next即可, 直到项目导入完毕

5.初次使用，可能速度会比较慢，包比较多、需要时间等待本地仓库下载完成(aliyun镜像maven配置初次下载耗时在15分钟左右, 以个人情况来看)

**也可以通过IDEA内置的Spring Initializr来实现, 原理和上面一致**

**通过普通的maven项目来搭建SpringBoot项目**

1.创建普通maven项目

2.在pom.xml中添加相关依赖, 这些依赖内部已经包含了对应开发场景所需的依赖

```xml
    <!--引入SpringBoot父依赖-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.6</version>
    </parent>

    <dependencies>
        <!--引入Web场景依赖启动器-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
```

3.编写主启动类, 注意主启动类所在的目录以及路径

```java
@SpringBootApplication //主启动类
public class StartApplication {
    public static void main(String[] args) {
        // springboot项目的启动方法
        SpringApplication.run(StartApplication.class);
    }
}
```

4.创建一个Controller

```java
@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "springboot maven";
    }
}
```

5.通过主启动类直接运行项目

访问[localhost:8080/test](http://localhost:8080/test), 查看浏览器页面

**通过IDEA内置Spring Initializr直接生成SpringBoot项目**

1.在创建页面填好项目相关信息, 选择好依赖(也可以手动去pom.xml中添加)

pom.xml参考

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<!--父依赖-->
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.7.6</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.entropy</groupId>
	<artifactId>springboot_01_initializr</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>springboot_01_initializr</name>
	<description>springboot_01_initializr</description>
	<properties>
		<java.version>1.8</java.version>
	</properties>
	<dependencies>
		<!--web依赖-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<!--单元测试-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<!--打包插件-->
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

2.创建一个Controller

```java
@RestController
public class TestController {
    @GetMapping("/initializr")
    public String initializr() {
        return "spring initializr";
    }
}
```

3.启动项目访问[localhost:8080/initializr](http://localhost:8080/initializr), 查看浏览器页面

4.添加常用依赖

```xml
	<dependencies>
		<!--web依赖-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<!--单元测试-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<!--热部署-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>
		<!--处理器依赖(提示自定义对象属性)-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-configuration-processor</artifactId>
		</dependency>
	</dependencies>
```

#### 3.打包项目

使用maven Lifecycle中的package即可在输出目录生成整个项目的jar包

有时候会遇到打包错误, 可能是测试用例的问题, 设置跳过测试用例即可

```xml
<!--
    在开发中, 很多情况下打包是不希望执行测试用例的
    可能是测试用例不完整, 或是测试用例会影响数据库数据
    需要跳过测试用例可配置以下插件
    -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <!--跳过项目运行测试用例-->
        <skipTests>true</skipTests>
    </configuration>
</plugin>
```

生成的jar包可在任何有对应版本Java环境的地方运行

**关于更改启动时显示的字符拼成的字母, 即 banner 图案**

> 只需要到项目下的 resources 目录下新建一个banner.txt 即可
>
> 图案可以到 https://www.bootschool.net/ascii 这个网站生成，然后拷贝到文件中即可！

## SpringBoot核心配置

**SpringBoot加载配置文件**

SpringBoot支持2种配置文件格式: **properties**和**yml/yaml**

默认配置文件名称: application

同级目录下配置优先级: properties > yml > yaml

全局配置文件(名称固定)

- application.properties

  格式: key=value

- application.yml

  格式: key: value

配置文件的作用主要就是修改SpringBoot自动配置的默认值(SpringBoot底层已经实现自动默认配置), 例如修改tomcat默认启动端口号

```properties
server.port=8081
```

### YAML概述

> YAML是 "YAML Ain't a Markup Language" （YAML不是一种标记语言）的递归缩写。在开发这种语言时，YAML 的意思其实是："Yet Another Markup Language"（仍是一种标记语言）

> **这种语言以数据作为中心，而不是以标记语言为重点！**

以前的配置文件大多都是使用xml, 以一个端口配置为例, 对比传统xml与yaml

传统xml配置

```xml
<server>
    <port>8081</port>
</server>
```

yaml配置

```yaml
server:
	port: 8081
```

**YAML基本规则**

- 区分大小写, 每个属性和值都需要注意大小写
- 使用缩进表示层级关系
- 缩进不能使用tab, 只能使用空格
- 缩进无空格数要求(习惯上一个层级2个空格), 保持同层级元素左对齐即可
- 注释使用'#'
- 键值对形式, **冒号后必须有一个空格**。key: value
- 短横线用于表示**列表项**, **短横线后也必须有一个空格**

注意: 字面量的值为字符串默认不需要加上单双引号。但字符串含有特殊字符时, 双引号不会进行转义, 特殊字符如\n换行符会执行; 单引号会进行转义, 特殊字符会被视为普通字符, 原封不动地输出

```yaml
# 对象、map写法
student:
  name: apple
  age: 2
# 行内写法
teacher: {name: tree,age: 200}

# 数组、list、set写法
pets:
  - cat
  - dog
  - pig
# 行内写法
host: [cat,dog,pig]
```

yaml修改端口号(可直接将原来的application.properties后缀改为yml或yaml)

```yaml
server:
  port: 8081
```

### 配置文件注入

yaml文件能够直接将匹配值注入实体类

1.在resources目录下创建application.yml

2.编写一个实体类

```java
@Component //注册bean到容器中
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cat {
    private String name;
    private Integer age;
}
```

使用注解需要添加lombok依赖

```xml
<!--lombok-->
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
</dependency>
```

3.原来注入属性值的方式@Value

```java
public class Cat {
    @Value("tom")
    private String name;
    @Value("3")
    private Integer age;
}
```

4.在SpringBoot的测试类下测试

```java
@SpringBootTest
class Springboot01InitializrApplicationTests {

	@Autowired
	private Cat cat;

	@Test
	void contextLoads() {
		System.out.println(cat);
	}

}
```

5.编写一个较为复杂的实体类

```java
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    private String name;
    private Integer age;
    private Boolean holiday;
    private Date birth;
    private Map<String, Object> map;
    private List<Object> list;
    private Cat cat;
}
```

6.使用yaml配置注入, 编写yaml文件

```yaml
person:
  name: sky
  age: 22
  holiday: true
  birth: 2000/1/1
  map: {k1: v1, k2: v2}
  list:
    - code
    - music
    - game
  cat:
    name: tomas
    age: 2
```

7.在实体类中使用@ConfigurationProperties实现注入

```java
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
/*
@ConfigurationProperties作用：
将配置文件中配置的每一个属性的值，映射到这个组件中；
告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定
参数 prefix = “person” : 将配置文件中的person下面的所有属性一一对应
*/
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private Integer age;
    private Boolean holiday;
    private Date birth;
    private Map<String, Object> map;
    private List<Object> list;
    private Cat cat;
}
```

注意需要导入配置文件处理器的依赖(前面的配置中已经导入相关依赖)

```xml
<!-- 导入配置文件处理器，配置文件进行绑定就会有提示，需要重启 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

8.在测试类中进行测试

```java
@SpringBootTest
class Springboot01InitializrApplicationTests {
	@Autowired
	private Person person;
	@Test
	void contextLoads() {
		System.out.println(person);
	}
}
```

注意: 若yaml文件中的key和属性名不一致则不会进行注入

### 加载指定的配置文件

**@PropertySource ：**加载指定的配置文件，需要配合@Value获取值；

**@configurationProperties**：默认从全局配置文件中获取值，也可指定配置文件；

1.在resources目录下新建person.properties

```properties
name=kate
```

2.在Person实体类中指定加载person.properties文件, 注意删除@ConfigurationProperties注解避免冲突

```java
...
@PropertySource(value = "classpath:person.properties")
public class Person {
    @Value("${name}")
    private String name;
    ...
}
```

3.在测试类中测试输出结果

### 配置文件占位符

```yaml
person:
  name: sky${random.uuid} # 随机uuid
  age: ${random.int} # 随机int
  holiday: true
  birth: 2000/1/1
  map: {k1: v1, k2: v2}
  list:
    - code
    - music
    - game
  cat:
    name: tomas
    age: 2
```

**上面采用的yaml方法都是最简单的方式, 是开发中最常用的, 也是springboot所推荐的**

### 回顾properties配置

properties配置文件在写中文的时会有乱码, 需要去IDEA中设置编码格式为UTF-8, 在settings --> File Encodings 中配置

**测试**

1.创建实体类, 注意实现构造方法, Getter和Setter方法以及ToString方法

```java
@Component
public class User {
    private String name;
    private Integer age;
    private String passwd;
}
```

2.编写user.properties

```properties
user.name=entropy
user.age=123
user.passwd=entropy123
```

3.注入实体类

```java
@Component
@PropertySource(value = "classpath:user.properties")
public class User {
    @Value("${user.name}")
    private String name;
    @Value("#{132*2}") //spring表达式
    private Integer age;
    @Value("1231234") //字面量
    private String passwd;
}
```

4.测试类测试

```java
@SpringBootTest
class Springboot01InitializrApplicationTests {
	@Autowired
	private User user;
	@Test
	void contextLoads() {
		System.out.println(user);
	}
}
```

### 对比两种注入方式

- @ConfigurationProperties只需要在类上编写一次就能实现注入, @Value需要在每个属性上编写才能实现完整的注入

- @ConfigurationProperties支持松散绑定, @Value不支持

  松散绑定, 即在配置文件中**last-name**和**lastName**两种写法的效果等同, 在-后面的字母默认大写, 注意这里没有空格

- @ConfigurationProperties支持JSR303数据校验, @Value不支持

  JSR303数据校验, 即过滤器验证, 用于保证数据的合法性

- @ConfigurationProperties支持复杂类型封装, @Value不支持

  复杂类型封装, 即在配置文件中编写对象类型, 对象嵌套对象的类型等

**配置yml和配置properties都可以获取到值, 推荐使用yml**

**大多数情况下, 推荐使用@ConfigurationProperties, @Value在仅需要测试少量数据时可以使用**

## 多环境切换

profile是Spring对不同环境提供不同配置功能的支持, 可以通过激活不同的环境版本, 实现快速切换环境

### 多配置文件

在编写主配置文件时, 文件名称可以命名为application-{profile}.properties/yml, 原来指定多个环境版本

例如:

application-test.properties 代表测试环境配置

application-dev.properties 代表开发环境配置

SpringBoot不会直接使用这些配置文件, 默认使用application.properties主配置文件。需要配置激活环境

application.yml

```yaml
# 使用dev或test环境
spring:
  profiles:
    active: dev
```

application-test.yml

```yaml
server:
  port: 8082
```

application-dev.yml

```yaml
server:
  port: 8084
```

使用不同的环境, 用不同的端口访问

**注意: 存在同名的properties和yml文件时, properties的优先级高于yml**

### 配置文件加载位置

外部加载配置文件的方式有很多, 选择最常用的方式即可

SpringBoot会扫描以下位置的application.properties或者application.yml作为默认配置文件

优先级: **项目路径下config文件夹配置文件 > 项目路径下配置文件 > 资源路径下config文件夹配置文件 > 资源路径下配置文件**

**优先级高的配置会覆盖优先级低的配置, SpringBoot会从这四个位置加载主配置文件**

利用优先级高低进行互补配置

优先级低的配置访问路径

```yaml
# 配置项目的访问路径
server:
  servlet:
    context-path: /entropy
```

优先级高的配置端口号

```yaml
sever:
  port: 8899
```

这个时候项目的url是[localhost:8899/entropy/initializr](http://localhost:8899/entropy/initializr)

## SpringBoot热部署

热部署是指在项目正处于运行的情况下, 修改代码能够即时生效, 而不需要事先停止项目进行修改才能生效

### 1.在pom.xml中添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
    <scope>true</scope>
</dependency>
```

>（1） devtools可以实现页面热部署（即页面修改后会立即生效，这个可以直接在application.properties文件中配置spring.thymeleaf.cache=false来实现, 其中thymeleaf是springboot默认的模板引擎），实现类文件热部署（修改后不会立即生效需要等待），实现对属性文件的热部署。即devtools会监听classpath下的文件变动，并且会立即重启应用（发生在保存时机）
>
>注意：因为其采用的虚拟机机制，该项重启是很快的
>（2）配置了true后在修改java文件后也就支持了热启动，不过这种方式是属于项目重启（速度比较快的项目重启），会清空session中的值，也就是如果有用户登陆的话，项目重启后需要重新登陆。

### 2.在application.yml中配置devtools

```yaml
# 配置热部署
spring:
  devtools:
    restart:
      enabled: true # 开启热部署
      additional-paths: src/main/java # 重启目录u
      exclude: WEB-INF/**
  # thymeleaf和freemarker模板引擎
  thymeleaf:
    cache: false # 禁用页面缓存
  freemarker:
    cache: false # 禁用页面缓存
```

### 3.修改IDEA配置

> 当我们修改了类文件后，idea不会自动编译，得修改idea设置
>
> 以下为IDEA2021.2版本以后的配置
>
> （1）File-Settings-Compiler-Build Project automatically
>
> （2）File-Settings-Advanced Settings-Compiler-allow auto-make...
>
> （3）ctrl + shift + alt + / ,选择Registry,勾上 actionSystem.assertFocusAccessFromEdit
>
> IDEA2021.2版本之前的配置
>
> （1）File-Settings-Compiler-Build Project automatically
> （2）ctrl + shift + alt + / ,选择Registry,勾上 Compiler autoMake allow when app running

### 4.测试

1.修改类文件, 保存重启

2.修改配置文件, 保存重启

3.修改页面等静态资源文件, 不会重启但会重新加载, 页面会刷新

### 5.补充

devtools的体验感并不好, 后期可考虑使用体验较好的需要付费的Jrebel插件 

## SpringBoot集成JDBC

### 环境搭建

创建新的SpringBoot项目, 选择JDBC API和MySQL依赖

pom.xml

```xml
	<dependencies>
		<!--web-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<!--jdbc-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<!--mysql-->
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
```

application.yml(请确认自己要连接的数据库名称)

```yaml
spring:
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/2_mybatis?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.jdbc.Driver
```

2_mybatis.sql参考

```mysql
create database 2_mybatis;

use 2_mybatis;

create table user(
	id int(20) not null primary key,
    name varchar(30) default null,
    password varchar(30) default null
)engine=InnoDB default charset=utf8;

insert into user(id,name,password) values
(1,'apple',123),
(2,'orange',456),
(3,'grape',789);
```

测试

```java
@SpringBootTest
class Springboot02JdbcApplicationTests {

	@Autowired
	private DataSource dataSource;
	@Test
	void contextLoads() throws SQLException {
		// 查看默认的数据源
		System.out.println(dataSource.getClass());

		Connection connection = dataSource.getConnection();
		System.out.println(connection);
		// 归还连接
		connection.close();
	}
}
```

### 数据库操作CRUD

编写TestController

```java
@RestController
public class TestController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询所有信息
    // 直接使用map接收数据
    @GetMapping("/userList")
    public List<Map<String, Object>> userList() {
        String sql = "select * from user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }
}
```

启动springboot项目, 访问[localhost:8080/userList](http://localhost:8080/userList)

完善方法

```java
    // 新增
    @GetMapping("addUser")
    public String addUser() {
        String sql = "insert into user(id,name,password) values (6,'entropy','rt33')";
        jdbcTemplate.update(sql);
        return "OK";
    }

    // 修改
    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") int id) {
        String sql = "update user set name=?,password=? where id=" + id;
        Object[] objects = new Object[2];
        objects[0] = "admin";
        objects[1] = "sudoers";
        jdbcTemplate.update(sql, objects);
        return "update OK";
    }

    // 删除
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        String sql = "delete from user where id=?";
        jdbcTemplate.update(sql, id);
        return "delete OK";
    }
```

## SpringBoot集成JPA

- JPA是`The Java Persistence API`标准，Java持久层API，是一种能让对象能够快速映射到关系型数据库的技术规范。
- JPA只是一种规范，它需要第三方自行实现其功能，在众多框架中`Hibernate`是最为强大的一个。
- `Spring Data JPA` 是采用基于JPA规范的`Hibernate`框架基础下提供了`Repository`层的实现。**`Spring Data Repository`极大地简化了实现各种持久层的数据库访问而写的样板代码量，同时`CrudRepository`提供了丰富的CRUD功能去管理实体类。**SpringBoot框架为Spring Data JPA提供了整合，`spring-boot-starter-data-jpa`能够让你快速使用这门技术，它提供了以下依赖。
  - Hibernate：最流行的JPA实现之一。
  - Spring Data JPA：帮助你去实现JPA-based repositories。
  - Spring ORM：Spring Framework提供的核心ORM支持。

### 环境搭建

1.创建新的SpringBoot项目, 选择JPA和MySQL依赖

2.配置application.yml(JPA能够自动创建数据表, 因此确保数据库存在即可)

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test
    hikari:
      username: root
      password: root
  jpa:
    hibernate:
      ddl-auto: update # 第一次建表create  后面用update，否则每次重启都会重新建表
    show-sql: true
```

3.创建entity(注意添加lombok依赖)

```java
@Data
@Entity // 实体类注解
public class User {
    @Id
    @GeneratedValue //自动生成Id
    private Long id;

    private String name;
}
```

4.创建DAO层UserMapper

```java
@Component
public interface UserMapper extends JpaRepository<User, Long> {
    /**
     * 这里直接继承JpaRepository
     * 很多方法已经由Jpa实现
     * **/
}
```

5.创建Service层UserService

```java
public interface UserService {
    List<User> findAll();
}
```

UserServiceImpl

```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
```

6.创建Controller层UserController

```java
@RestController
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/list")
    public List<User> findAll() {
        return userService.findAll();
    }
}
```

7.启动项目测试, 访问[localhost:8080/list](http://localhost:8080/list)

观察日志可以发现JPA自动创建了数据表。手动添加数据后再访问[localhost:8080/list](http://localhost:8080/list)

```mysql
use test;
insert into user values (1,'tom'),(2,'tiger');
```

JPA整合完成

## SpringBoot集成MyBatis

MyBatis是一款优秀的持久层框架,Spring Boot官方并没有提供MyBatis依赖,但是MyBatis团队自行适配Spring Boot,提供了mybatis-spring-boot-starter依赖启动器实现数据访问操作.

### 环境搭建

> 步骤:
> 	数据准备：创建数据库、数据表并插入一定的数据
> 	创建项目，引入相应的启动器：使用Spring Initializr的方式构建项目，选择MySQL和MyBatis依赖,编写实体类
> 	编写配置文件：在配置文件中进行数据库连接配置以及进行第三方数据源的默认参数覆盖

1.数据库环境搭建

```mysql
# 创建test数据库
create database if not exists test;
# 创建t_article数据表
CREATE TABLE `t_article` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(200) DEFAULT NULL COMMENT '文章标题',
  `content` longtext COMMENT '文章内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
# 添加相关数据
INSERT INTO `t_article` VALUES ('1', 'Spring Boot', 'nice');
INSERT INTO `t_article` VALUES ('2', 'Spring Cloud', 'OK');
# 创建t_comment数据表
  CREATE TABLE `t_comment` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `content` longtext COMMENT '评论内容',
  `author` varchar(200) DEFAULT NULL COMMENT '评论作者',
  `a_id` int(20) DEFAULT NULL COMMENT '关联的文章id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
# 添加相关数据
INSERT INTO `t_comment` VALUES ('1', '很全、很详细', 'apple', '1');
INSERT INTO `t_comment` VALUES ('2', '赞一个', 'tom', '1');
INSERT INTO `t_comment` VALUES ('3', '很详细', 'kitty', '1');
INSERT INTO `t_comment` VALUES ('4', '很好，非常详细', 'tim', '1');
INSERT INTO `t_comment` VALUES ('5', '很不错', 'mark', '2');
```

2.创建项目, 选择MySQL和MyBatis依赖

添加Druid依赖

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.8</version>
</dependency>
```

3.编写实体类Article和Comment

Article

```java
public class Article {
    private Integer id;
    private String title;
    private String content;
    private List<Comment> commentList;
    // getter和setter方法编写、toString方法编写
    ...
}
```

Comment

```java
public class Comment {
    private Integer id;
    private String content;
    private String author;
    private Integer aId;
    // getter和setter方法编写、toString方法编写
    ...
}
```

4.编写application.properties

```properties
# 应用名称
spring.application.name=springbootMybatis
# 服务端口
server.port=8080

# MyBatis配置
# 指定Mapper文件
mybatis.mapper-locations=classpath:mappers/*xml
# 指定MyBatis实体目录
mybatis.type-aliases-package=com.entropy.springboot03mybatis.pojo
# 开启驼峰命名
mybatis.configuration.map-underscore-to-camel-case=true
# 数据库驱动
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
# 数据库连接
spring.datasource.url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC
# 数据库用户名
spring.datasource.username=root
# 数据库密码
spring.datasource.password=root
# 配置第三方数据源
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.druid.initial-size=10
spring.datasource.druid.max-active=20
spring.datasource.druid.min-idle=3
```

### 注解式整合

1.创建Mapper接口

```java
public interface CommentMapper {
    @Select("select * from t_comment")
    List<Comment> findAll();
}
```

注意: 每个Mapper接口上都需要添加@Mapper注解, 或者在主启动类添加@MapperScan注解

2.测试

```java
@SpringBootTest
class Springboot03MybatisApplicationTests {

    @Autowired
    private CommentMapper commentMapper;
    @Test
    void contextLoads() {
        List<Comment> comments = commentMapper.findAll();
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }
}
```

3.使用xml整合mybatis

创建Mapper接口

```java
public interface CommentXmlMapper {
    List<Comment> findAll();
}
```

在resources/mappers目录下创建对应的xml文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.entropy.springboot03mybatis.dao.CommentXmlMapper">
    <select id="findAll" resultType="comment">
        select * from t_comment
    </select>
</mapper>
```

mappers目录是在之前的application.properties或application.yml文件中所指定的, 需要更换目录可修改配置

测试

```java
    @Autowired
    private CommentXmlMapper commentXmlMapper;
    @Test
    public void test() {
        List<Comment> comments = commentXmlMapper.findAll();
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }
```

## SpringBoot集成Redis

> 实现步骤
>
> 1.搭建SpringBoot工程
>
> 2.引入redis起步依赖
>
> 3.配置redis相关属性
>
> 4.注入RedisTemplate模板
>
> 5.编写测试方法，测试

### 1.搭建SpringBoot工程

创建一个新的SpringBoot项目, 选择web、redis相关依赖

### 2.相关依赖

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-redis</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-devtools</artifactId>
		<scope>runtime</scope>
		<optional>true</optional>
	</dependency>
	<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<optional>true</optional>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
</dependencies>
```

### 3.配置application.yml

```yaml
spring:
  redis:
    host: 127.0.0.1 # redis主机IP
    port: 6379 # 默认端口号
```

### 4.测试

测试前请确保redis服务端启动, redis在GitHub上的[Releases · microsoftarchive/redis](https://github.com/microsoftarchive/redis/releases)下载zip压缩包找到内部的release目录下含有redis主要的exe文件的压缩包后解压即可（这里是以2.8.9版本为例）

```java
@SpringBootTest
class Springboot03RedisApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;
	@Test
	void contextLoads() {
		// 存入数据
		redisTemplate.boundValueOps("name").set("entropy");
	}
	@Test
	void testGet() {
		Object name = redisTemplate.boundValueOps("name").get();
		System.out.println(name);
	}
}
```

创建User

```java
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    // 实现序列化接口, 否则无法使用redis存储
    private Integer id;
    private String name;
}
```

依赖(前面已经给出)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

测试

```java
	@Test
	public void testUser() throws JsonProcessingException {
		User user = new User(12, "api");
		// 实际开发中传递的都是json数据
//		String s = new ObjectMapper().writeValueAsString(user);
		redisTemplate.opsForValue().set("user", user);
		System.out.println(redisTemplate.opsForValue().get("user"));
	}
```

### 5.RedisTemplate的使用

> opsForValue() 操作字符串
>
> opsForList() 操作List数据类型 
>
> opsForSet() 操作Set数据类型
>
> opsForHash() 操作Hash数据类型
>
> opsForZSet() 操作ZSet数据类型
>
> opsForGeo()
>
> opsForHyperLogLog() 

### 6.Redis配置类

```java
@Configuration
public class RedisConfig {

    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // json序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key采用jackson的序列化方式
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        // value采用jackson的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value采用jackson的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }
}
```

### 7.RedisUtils工具类

```java
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 根据 key 获取过期时间
     *
     * @param key 键(不能为 Null)
     * @return 时间(秒) 返回0代表永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    /**
     * 判断 key 是否存在
     *
     * @param key 键(不能为 Null)
     * @return true 存在 false 不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void delete(String...key) {
        if (key != null && key.length > 0) {
            redisTemplate.delete(key[0]);
        } else {
            redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
        }
    }
    /**==================================String====================================*/

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }
    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true 成功 false 失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time > 0 若 time <= 0 将设置无限期
     * @return true 成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long increment(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }
    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decrement(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }
    /**===============================list=================================*/

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public List<Object> listGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public long listGetSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public Object listGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public boolean listSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将list放入缓存并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean listSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将list放入缓存(批量操作)
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean listSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将list放入缓存并设置时间(批量操作)
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean listSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean listUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long listRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**============================set=============================*/

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    public Set<Object> setGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean setHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long setSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 将set数据放入缓存并设置过期时间
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long setSetWithTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public long setGetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**================================Map=================================*/

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     */
    public Object hashGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }
    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hashMapGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public boolean hashMapSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hashMapSet(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hashSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hashSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hashDelete(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }
    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
     public boolean hashHasKey(String key, String item) {
         return redisTemplate.opsForHash().hasKey(key, item);
     }
    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     */
    public double hashIncrement(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }
    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     */
    public double hashDecrement(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    /**===============================HyperLogLog=================================*/

    public long hllAdd(String key, String value) {
        return redisTemplate.opsForHyperLogLog().add(key, value);
    }

    public long hllCount(String key) {
        return redisTemplate.opsForHyperLogLog().size(key);
    }

    public void hllDelete(String key) {
        redisTemplate.opsForHyperLogLog().delete(key);
    }

    public void hllMerge(String key1, String key2) {
        redisTemplate.opsForHyperLogLog().union(key1, key2);
    }
}
```

测试工具类

```java
	@Autowired
	private RedisUtils redisUtils;
	@Test
	public void testUtils() {
		redisUtils.set("name", "utils");
		System.out.println(redisUtils.get("name"));
	}
```

## SpringBoot使用分页插件

### SSM中使用PageHelper分页插件

1.添加依赖

```xml
    <!-- 添加pagehelper依赖-->
    <dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper</artifactId>
      <version>5.2.0</version>
    </dependency>
```

2.在SqlSessionFactory 工厂中传入 PageHelper 插件

```xml
	<!-- 配置MyBatis工厂 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="classpath:sqlMapConfig.xml"/>
        <!-- 传入pagehelper插件 -->
        <property name="plugins">
            <array>
                <!-- 传入插件对象 -->
                <bean class="com.github.pagehelper.PageInterceptor">
                    <property name="properties">
                        <props>
                            <prop key="helperDialect">mysql</prop>
                            <prop key="reasonable">true</prop>
                        </props>
                    </property>
                </bean>
            </array>
        </property>
    </bean>
```

或者在MyBatis配置文件中配置

```xml
    <!-- 引入 pageHelper插件 -->
    <!--注意这里要写成PageInterceptor, 5.0之前的版本都是写PageHelper, 5.0之后要换成PageInterceptor -->
    <plugins>
        <plugin interceptor="com.github.pagehelper.PageInterceptor">
            <!-- reasonable：分页合理化参数，默认值为false。 当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页。
默认false 时，直接根据参数进行查询。 -->
            <property name="helperDialect" value="mysql"/>
            <property name="reasonable" value="true" />
        </plugin>
    </plugins>
```

3.Mapper接口提供查询所有数据的方法

```java
public interface FileMapper {
    //查询所有数据
    @Select("select * from tb_file")
    List<File> findAll();
}
```

4.Service接口提供分页查询方法

```java
 public interface FileService {   
	//分页查询
    PageInfo<File> findAll(int pageNum,int size);
 }
```

Service实现类

```java
@Service
public class FileServiceImpl implements FileService {
    @Override
    public PageInfo<File> findAll(int pageNum, int size) {
        PageHelper.startPage(pageNum,size);
        List<File> all = fileMapper.findAll();
        PageInfo<File> pageInfo = new PageInfo(all);
        return pageInfo;
    }    
}
```

### SpringBoot整合MyBatis使用分页插件

1.添加依赖(1.4.0版本存在依赖循环问题, 使用最新版本即可)

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.4.6</version>
</dependency>
```

2.application.properties中配置分页插件信息

```properties
# 配置pageHelper插件(官方推荐配置)
#指定方言,使用mysql数据库
pagehelper.helper-dialect=mysql
#参数合理化
pagehelper.reasonable=true
#支持通过 Mapper 接口参数来传递分页参数
pagehelper.support-methods-arguments=true
#为了支持startPage(Object params)方法，增加了该参数来配置参数映射
pagehelper.params=countSql
```

> 参数说明：
> 1)helperDialect：
> 	分页插件会自动检测当前的数据库链接，自动选择合适的分页方式。 你可以配置helperDialect属性来指定分页插件使用哪种方言。配置时，可以使用下面的缩写值：　   
>    oracle,mysql,mariadb,sqlite,hsqldb,postgresql,db2,sqlserver,informix,h2,sqlserver2012,derby 
> 特别注意：
> 	使用 SqlServer2012 数据库时，需要手动指定为sqlserver2012，否则会使用 SqlServer2005 的方式进行分页。
>     你也可以实现AbstractHelperDialect，然后配置该属性为实现类的全限定名称即可使用自定义的实现方法。
> 2)offsetAsPageNum：
> 	默认值为false，该参数对使用RowBounds作为分页参数时有效。 
> 	当该参数设置为true时，会将RowBounds中的offset参数当成pageNum使用，可以用页码和页面大小两个参数进行分页。
> 3)rowBoundsWithCount：
> 	默认值为false，该参数对使用RowBounds作为分页参数时有效。 
> 	当该参数设置为true时，使用RowBounds分页会进行 count 查询。
> 4)pageSizeZero：
> 	默认值为false，当该参数设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果（相当于没有执行分页查询，但是返回结果仍然是Page类型）。
> 5)reasonable：
> 	分页合理化参数，默认值为false。
> 	当该参数设置为true时，pageNum<=0时会查询第一页，pageNum>pages（超过总数时），会查询最后一页。默认false时，直接根据参数进行查询。
> 6)params：
> 	为了支持startPage(Object params)方法，增加了该参数来配置参数映射，用于从对象中根据属性名取值， 可以配置pageNum,pageSize,count,pageSizeZero,reasonable，不配置映射的用默认值， 默认值为：pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero。
> 7)、supportMethodsArguments：
> 	支持通过 Mapper 接口参数来传递分页参数，默认值false，分页插件会从查询方法的参数值中，自动根据上面params配置的字段中取值，查找到合适的值时就会自动分页。
>     使用方法可以参考测试代码中的com.github.pagehelper.test.basic包下的ArgumentsMapTest和ArgumentsObjTest。
> 8)autoRuntimeDialect：
> 	默认值为false。设置为true时，允许在运行时根据多数据源自动识别对应方言的分页 （不支持自动选择sqlserver2012，只能使用sqlserver）。
> 9)、closeConn：
> 	默认值为true。当使用运行时动态数据源或没有设置helperDialect属性自动获取数据库类型时，会自动获取一个数据库连接， 通过该属性来设置是否关闭获取的这个连接，默认true关闭，设置为false后，不会关闭获取的连接，这个参数的设置要根据自己选择的数据源来决定。

3.在Mapper接口中添加查询方法

```java
public interface CommentMapper {
    @Select("select * from t_comment")
    List<Comment> findAll();
}
```

4.在Service接口中添加分页查询方法

```java
public interface CommentService {
    // 分页查询方法
    PageInfo<Comment> findAll(int pageNum, int pageSize);
}
```

Service实现类

```java
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public PageInfo<Comment> findAll(int pageNum, int pageSize) {
        // 使用PageHelper指定当前页和每页条数
        PageHelper.startPage(pageNum, pageSize);
        // 查询数据
        List<Comment> all = commentMapper.findAll();
        // 把查询结果封装到PageInfo中
        PageInfo<Comment> pageInfo = new PageInfo(all);
        return pageInfo;
    }
}
```

5.测试

```java
    @Autowired
    private CommentService commentService;
    @Test
    public void testPage() {
        // 查询第二页, 每页3条数据
        PageInfo<Comment> info = commentService.findAll(2, 3);
        List<Comment> comments = info.getList();
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }
```

**可能遇到的问题: 依赖循环问题, 使用最新版本的pagehelper即可**, [pagehelper官方仓库](https://github.com/pagehelper/pagehelper-spring-boot)

## SpringBoot使用lombok插件

### lombok的使用

#### 1.在IDEA插件中安装lombok

#### 2.添加lombok的依赖

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.12</version>
    <scope>provided</scope>
</dependency>
```

#### 3.常用的lombok注解

> @Data：注解在类上，将类提供的所有属性都添加get、set方法，并添加、equals、canEquals、hashCode、toString方法
> @Setter：注解在类上，为所有属性添加set方法、注解在属性上为该属性提供set方法
> @Getter：注解在类上，为所有的属性添加get方法、注解在属性上为该属性提供get方法
> @NotNull：在参数中使用时，如果调用时传了null值，就会抛出空指针异常
> @Synchronized 用于方法，可以锁定指定的对象，如果不指定，则默认创建一个对象锁定
> @Log作用于类，创建一个log属性
> @Builder：使用builder模式创建对象
> @NoArgsConstructor：创建一个无参构造函数
> @AllArgsConstructor：创建一个全参构造函数
> @ToStirng：创建一个toString方法
> @Accessors(chain = true)使用链式设置属性，set方法返回的是this对象。
> @RequiredArgsConstructor：创建对象
> @UtilityClass:工具类
> @ExtensionMethod:设置父类
> @FieldDefaults：设置属性的使用范围，如private、public等，也可以设置属性是否被final修饰。
> @Cleanup: 关闭流、连接点。
> @EqualsAndHashCode：重写equals和hashcode方法。
> @toString：创建toString方法。

#### 4.示例

实体类(请确认数据库环境已经搭建完成)

```java
@Entity(name = "t_comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "content")
    private String content;
    private String author;
    private Integer aId;
}
```

Mapper接口

```java
@Component
public interface CommentMapper extends JpaRepository<Comment, Integer> {
}
```

测试

```java
@SpringBootTest
class Springboot02JpaApplicationTests {

	@Autowired
	private CommentMapper commentMapper;
	@Test
	void contextLoads() {
		Optional<Comment> optional = commentMapper.findById(1);
		Comment comment = optional.get();
		System.out.println(comment);
	}
}
```

