### MyBatis

#### 简介

MyBatis是一个基于“ORM”的框架, ORM的全称是**对象关系映射**（Object Relational Mapping）

对象（Object）就是指Java中的对象, 关系（Relational）就是指数据库中的数据表, 基于“ORM"的框架就是在对象和关系之间实现数据的双向转换

MyBatis就是一个基于“ORM”的实现操作数据库的框架, 封装了数据库操作的基本方法, 提供了通用的方法, 不需要开发者再去重复开发

**关于MyBatis的优势**

- MyBatis是一款优秀的持久层框架
- MyBatis支持自定义SQL、存储过程以及高级映射
- MyBatis的使用免除了几乎所有的JDBC代码以及设置参数和获取结果集的工作
- MyBatis能够通过简单的XML或注解来配置和映射原始类型、接口和Java的POJO为数据库中的记录

**历史信息**

- MyBatis原本是Apache的一个开源项目[iBatis](https://ibatis.apache.org/), 2010年这个项目被Apache Software Foundation迁移到了Google code, 并且更名为MyBatis
- 2013年11月迁移到[Github](https://github.com/mybatis)

**maven项目获取MyBatis**

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.2</version>
</dependency>
```

 [mybatis – MyBatis 3 | 简介中文文档](https://mybatis.org/mybatis-3/zh/) 

##### 持久化

数据持久化的概念

- 持久化就是将程序数据在持久状态和瞬时状态间转换的机制
- 持久状态: 保存在数据库中, 电脑或服务器正常关闭重启后数据依然存在, 能够长久保存
- 瞬时状态: 保存在内存中, 电脑或服务器一旦关闭数据就会丢失, 不能长久保存
- 很多重要的数据不能够保存在内存中, 一方面内存本身就是稀缺资源, 另一方面内存的数据也容易丢失

##### 持久层

持久层的概念: 持久层又称数据访问层(DAO层), 是直接对数据库进行操作的层

持久层完成了所有的持久化工作, 并与Service层、Controller层有清晰的层界限, 有利于后期维护

##### MyBatis相对于JDBC

- 传统的JDBC代码相对于MyBatis比较复杂, MyBatis能够简化开发
- MyBatis易于上手, 灵活, 分离了SQL和代码
- MyBatis提供映射标签, 支持对象与数据库的数据表字段的映射
- MyBatis提供xml标签, 支持编写动态SQL

#### 简单使用MyBatis

##### 环境搭建

搭建数据库

```sql
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

##### 创建项目

1.创建普通maven项目

2.删除src目录(用于多模块项目的搭建, 去除掉父模块的src, 用子模块的src来替代)

3.导入maven依赖(父模块的pom文件)

```xml
<!--导入依赖-->
    <dependencies>
        <!--mysql驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>

        <!--mybatis-->
        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.2</version>
        </dependency>

        <!--junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        
    </dependencies>


 <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.4.2</version>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>
        </plugins>
</build>

<!--在build中配置resources  来防止我们资源导出失败的问题-->
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>

```

创建子模块

1.编写mybatis[核心配置文件](MyBatis_01/src/main/resources/mybatis-config.xml)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--configuration: 核心配置文件-->
<configuration>
    <environments default="development">
        <environment id="development">
            <!--transactionManager: 事务管理-->
            <transactionManager type="JDBC"></transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <!--如果出现不能连接数据库问题, 设置useSSL=false-->
                <property name="url" value="jdbc:mysql://localhost:3306/2_mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
</configuration>
```

2.编写MyBatis[工具类](MyBatis_01/src/main/java/com/entropy/utils/MyBatisUtils.java)

```java
public class MyBatisUtils {

    //SqlSessionFactory是专门用来创建SqlSession对象(即操作数据库的对象)
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //获取配置文件信息
            InputStream resourceStream = Resources.getResourceAsStream("mybatis-config.xml");
            //通过配置文件获取一个初始化的SqlSessionFactory对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取SqlSession对象
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
```

##### 编写主要代码

[实体类](MyBatis_01/src/main/java/com/entropy/pojo/User.java)

[Dao接口](MyBatis_01/src/main/java/com/entropy/dao/UserMapper.java)

[实现Dao接口的xml文件](MyBatis_01/src/main/java/com/entropy/dao/UserMapper.xml)

##### 测试

[测试代码](MyBatis_01/src/test/java/com/entropy/DaoTest.java)

##### 常见问题

**org.apache.ibatis.binding.BindingException: Type interface com.entropy.dao.UserMapper is not known to the MapperRegistry.**

未在核心配置文件中注册xml文件, 在[核心配置文件](MyBatis_01/src/main/resources/mybatis-config.xml)中添加以下配置信息即可

```xml
    <!--每一个Mapper.XML都需要在Mybatis核心配置文件中注册！-->
    <mappers>
        <mapper resource="com/entropy/dao/UserMapper.xml"/>
    </mappers>
```

**Cause: java.io.IOException: Could not find resource com.entropy.dao.UserMapper.xml**

文件未被IDEA识别并导出, 在pom.xml文件(子父模块均可)中配置resources

```xml
<build>
        <!--在build中配置resources  防止资源导出失败的问题-->
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
</build>
```

**Cause: com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure**

旧版数据库不能使用SSL问题, 设置SSL为false即可

**主要问题汇总**

1.配置文件没有注册

2.绑定接口错误

3.方法名不对

4.返回类型不对

5.Maven导出资源问题

#### CRUD

##### namespace命名空间

需要确保namespace中的值与对应的dao层的mapper接口名称一致

##### 相关标签的属性数说明

- **id**: 对应mapper接口中的**方法名**
- **resultType**: 对应mapper接口中的**返回类型**
- **parameterType**: 对应mapper接口中的**参数类型**

**注意: 增删改数据需要手动提交事务才能实现对数据库数据的操作（SqlSession默认关闭自动提交机制）**

##### mapper接口参数过多的情况

使用map集合存储并传递数据, 测试类在map中存储数据并将map传递给xml文件, xml文件根据map的key获取到存储的数据, 实现对数据库的数据操作

##### 模糊查询

使用通配符%%

#### 配置解析文件的说明

##### 1.核心配置文件[mybatis-config.xml](MyBatis_01/src/main/resources/mybatis-config.xml)

mybatis的配置文件包含了mybatis行为的设置和属性信息

配置文件的结构: 

configuration（配置）
properties（属性）
settings（设置）
typeAliases（类型别名）
typeHandlers（类型处理器）
objectFactory（对象工厂）
plugins（插件）
environments（环境配置）
environment（环境变量）
transactionManager（事务管理器）
dataSource（数据源）
databaseIdProvider（数据库厂商标识）
mappers（映射器）

##### 2.环境配置（environments）

MyBatis默认的事务管理器就是JDBC，连接池：POOLED

MyBatis可以适配多种环境, 但每个SqlSessionFactory实例只能使用一种环境

##### 3.属性（properties）

通过properties属性能够引用外部的配置文件, 并能够进行动态替换, 在properties的子标签property标签里设置具体的属性值

外部配置文件编写

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/2_mybatis?useSSL=false&useUnicode=true&characterEncoding=UTF-8
username=root
password=root
```

核心配置文件引入

```xml
<properties resource="db.properties">
  <property name="username" value="root"/>
  <property name="password" value="root"/>
</properties>

<environments default="development">
        <environment id="development">
            <!--transactionManager: 事务管理-->
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
</environments>
```

直接引入外部文件, 在文件增加属性配置, 遇到同一个字段的重复配置时, 优先使用外部配置文件的配置

##### 4.类型别名（typeAliases）

类型别名能够为java类型设置一个缩写的命名, 仅能在xml配置中使用, 简化冗余的全限定类名书写

```xml
<!--可以给实体类起别名-->
    <typeAliases>
        <!--typeAlias能够自定义命名-->
        <!--<typeAlias type="com.entropy.pojo.User" alias="User"/>-->

        <!--package默认直接以类名作为全限定类命名-->
        <!--注意name值的范围是包名, 该包名下的类均以类名作为全限定类命名-->
        <package name="com.entropy.pojo"/>
    </typeAliases>
```

**typeAlias**适合在实体类比较少的情况下使用, **package**适合在实体类比较多的情况下使用

**typeAlias**能够自定义命名, **package**需要自定义命名还需要在对应的实体类上添加注解

```java
@Alias("user")
public class User {}
```

##### 5.设置（settings）

**相关属性说明**

- **cacheEnabled**: 全局性地开启或关闭所有映射器配置文件中已配置的任何缓存(默认true)
- **lazyLoadingEnabled**: 延迟加载的全局开关。开启时, 所有关联对象都会延迟加载。特定关联关系中可通过设置`fetchType`属性来覆盖设置(默认false)
- **logImpl**: 指定MyBatis所用日志的具体实现, 未指定时自动查找(常见日志有SLF4J、LOG4J等)

##### 6.其他配置

- [typeHandlers(类型处理器)](https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers)
- [objectFactory(对象工厂)](https://mybatis.org/mybatis-3/zh/configuration.html#objectFactory)
- plugins插件
  - mybatis-generator-core
  - mybatis-plus
  - 通用mapper

##### 7.映射器（mappers）

MapperRegistry: 注册并绑定mapper.xml文件

方式一(推荐): 直接绑定xml文件

```xml
<!--每一个Mapper.XML都需要在Mybatis核心配置文件中注册！-->
    <mappers>
        <mapper resource="com/entropy/dao/UserMapper.xml"/>
    </mappers>
```

注意: 这种方式是以 / 来书写路径的, 不能使用 . 来区分上下级目录

方式二: 使用class文件绑定

```xml
    <mappers>
        <mapper class="com.entropy.dao.UserMapper"/>
    </mappers>
```

注意: 使用这种方式请确保接口文件名与xml文件名一致, 并确保接口文件和xml文件处于同一个包下, 否则就无法识别

方式三: 扫描包进行注入并绑定

```xml
	<mappers>
        <package name="com.entropy.dao"/>
    </mappers>
```

注意事项同上面的第二种方式

##### 8.生命周期与作用域

###### SqlSessionFactoryBuilder

- 主要就是用于创建一个**SqlSessionFactory**, 创建完成后就不再使用
- 是一个局部变量

###### SqlSessionFactory

- 实际上就是一个MyBatis默认配置的数据库连接池, 可以通过修改配置使用其他的连接池技术
- **SqlSessionFactory**一旦被创建就一直存在于应用的整个运行期间, 因此它的作用域是**应用作用域**
- 简易开发一般通过单例模式或者静态单例模式来使用**SqlSessionFactory**

###### SqlSession

- 相当于一个连接请求, 从连接池中获取连接
- **SqlSession**的实例不是线程安全的, 不能用来共享, 因此它的最佳的作用域是**请求或方法作用域**
- 使用完之后就需要归还连接资源

#### Java对象属性名与数据库字段名不一致的解决方案

##### 1.起别名

通过修改sql语句实现

```xml
 <select id="" resultType="">
     <!--使用as关键字取别名, 别名就是java对象的属性名-->
     <!--password是数据库字段名, pass是java对象属性名-->
     select id,name,password as pass from user where id = #{id}
 </select>
```

##### 2.resultMap

通过结果集映射

```xml
 <!--结果集映射-->
    <resultMap id="UserMap" type="User">
        <!--column:数据库中的字段, property:实体类中的属性-->
        <result column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="password" property="pass"/>
    </resultMap>

<!--根据ID查询用户-->
    <select id="" resultMap="UserMap">
        select * from user where id = #{id}
    </select>
```

- **resultMap**在MyBatis中具有很重要的作用
- **resultMap**的设计思想就是, 对简单的语句做到零配置, 对复杂的语句, 只需要描述语句之间的关系

#### 日志

##### 日志工厂

-  SLF4J 

-  LOG4J

-  LOG4J2 

-  JDK_LOGGIN: Gjava自带的日志输出

-  COMMONS_LOGGING: 工具包

-  STDOUT_LOGGING: 控制台输出

-  NO_LOGGING: 不输出

##### 配置日志

在核心配置文件[mybatis-config.xml](MyBatis_01/src/main/resources/mybatis-config.xml)中配置

 **STDOUT_LOGGING标准日志输出**

```xml
<settings>
    <!--标准的日志工厂实现-->
    <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```

**Log4j日志输出**

 Log4j是Apache的一个开源项目, 通过使用Log4j可以控制日志信息输送的目的地、每一条日志的输出格式, 通过定义每一条日志信息的级别, 能够更加细致地控制日志的生成过程

1.导入Log4j的依赖

```xml
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

2.编写配置文件log4j.properties

```properties
log4j.rootLogger=DEBUG,console,file

#控制台输出的相关设置
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=[%c] -%m%n

#文件输出的相关设置
log4j.appender.file=org.apache.log4j.DailyRollingFileAppender
#输出的log文件的路径, 可在log文件中查看输出信息
log4j.appender.file.File=./log/entropy.log
log4j.appender.file.Threshold = DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql = DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```

3.配置日志为Log4j

```xml
<settings>
    <setting name="logImpl" value="LOG4J"/>
</settings>
```

4.直接运行测试代码就能在控制台看到输出信息



**在类中简易配置log4j**

```java
//在需要使用log4j的类中, 导入包 import org.apache.log4j.Logger

//日志对象, 参数为当前类的class
static Logger logger = Logger.getLogger(DaoTest.class);
//日志级别
logger.info("level:info");
logger.debug("level:debug");
logger.error("level:error");
```







