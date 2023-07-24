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

- MyBatis原本是Apache的一个开源项目[iBatis](https://ibatis.apache.org/), 2010年这个项目被Apache Software
  Foundation迁移到了Google code, 并且更名为MyBatis
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
create
database 2_mybatis;

use
2_mybatis;

create table user
(
    id       int(20) not null primary key,
    name     varchar(30) default null,
    password varchar(30) default null
)engine=InnoDB default charset=utf8;

insert into user(id, name, password)
values (1, 'apple', 123),
       (2, 'orange', 456),
       (3, 'grape', 789);
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
                <property name="url"
                          value="jdbc:mysql://localhost:3306/2_mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
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

> 注意xml中的id字段要和接口中的方法名一一对应

##### 测试

[测试代码](MyBatis_01/src/test/java/com/entropy/DaoTest.java)

##### 常见问题

**org.apache.ibatis.binding.BindingException: Type interface com.entropy.dao.UserMapper is not known to the
MapperRegistry.**

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

- configuration（配置）

- properties（属性）

- settings（设置）

- typeAliases（类型别名）

- typeHandlers（类型处理器）

- objectFactory（对象工厂）

- plugins（插件）

- environments（环境配置）

- environment（环境变量）

- transactionManager（事务管理器）

- dataSource（数据源）

- databaseIdProvider（数据库厂商标识）

- mappers（映射器）

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
public class User {
}
```

> 自定义命名是可选的，不是必须的，不自定义命名就默认使用类名

##### 5.设置（settings）

**相关属性说明**

- **cacheEnabled**: 全局性地开启或关闭所有映射器配置文件中已配置的任何缓存(默认true)
- **lazyLoadingEnabled**: 延迟加载的全局开关。开启时, 所有关联对象都会延迟加载。特定关联关系中可通过设置`fetchType`
  属性来覆盖设置(默认false)
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

方式一: 直接绑定xml文件

```xml
<!--每一个Mapper.XML都需要在Mybatis核心配置文件中注册！-->
<mappers>
    <mapper resource="com/entropy/dao/UserMapper.xml"/>
</mappers>
```

注意: 这种方式是以 / 来书写路径的, 不能使用 . 来区分上下级目录。xml文件可以放在任意位置，习惯上是放在resources目录下。

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

- SLF4J

- LOG4J

- LOG4J2

- JDK_LOGGIN: Gjava自带的日志输出

- COMMONS_LOGGING: 工具包

- STDOUT_LOGGING: 控制台输出

- NO_LOGGING: 不输出

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

Log4j是Apache的一个开源项目, 通过使用Log4j可以控制日志信息输送的目的地、每一条日志的输出格式, 通过定义每一条日志信息的级别,
能够更加细致地控制日志的生成过程

1.导入Log4j的依赖(存在安全漏洞，建议使用最新版或其他替代)

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
log4j.appender.console.Target=System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=[%c] -%m%n
#文件输出的相关设置
log4j.appender.file=org.apache.log4j.DailyRollingFileAppender
#输出的log文件的路径, 可在log文件中查看输出信息
log4j.appender.file.File=./log/entropy.log
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n
#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
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
static Logger logger=Logger.getLogger(DaoTest.class);
//日志级别
        logger.info("level:info");
        logger.debug("level:debug");
        logger.error("level:error");
```

#### 分页

数据分页, 即在网页上一次性只显示一部分数据, 减少数据的处理量

##### 1.limit实现分页

sql语句

```mysql
select *
from user
limit startIndex,pageSize;
```

在MyBatis中实现: 接口、mapper.xml、测试

##### 2.RowBounds分页

RowBounds分页是由MyBatis提供的方法, 用于快速实现分页查询, 不需要额外修改sql语句

##### 3.GitHub提供的MyBatis分页插件

[PageHelper插件](https://pagehelper.github.io/)

#### 注解式开发

##### 1.面向接口编程思想

**优点：解耦, 可拓展, 提高复用性,。分层开发中, 上层不需要管具体的实现, 只需要遵守共同的标准。使得开发变得容易, 规范性更好**

面向接口编程是面向对象编程思想体系中的重要内容, 它不关注实现类对象本身是如何自我实现的, 而是关注各个对象之间的协作关系

**深入理解接口**

接口从深层次理解应该是定义与实现的分离

接口本身反映了系统设计者对系统的抽象理解

接口应有两类

- 第一类是对一个体的抽象, 它可对应为一个抽象体
- 第二类是对一个体某一方面的抽象, 即形成一个抽象面
- 一个体有可能有多个抽象面
- 抽象体与抽象面是有区别的

**面向对象是以对象为单位, 具体考虑对象本身的实现**

**面向过程是以一个具体的流程为单位, 具体考虑流程的实现**

##### 2.注解开发过程

注解在接口上实现

```java
public interface UserMapper {

    @Select("select * from user")
    List<User> getAll();
}
```

在核心配置文件中绑定接口

```xml

<mappers>
    <mapper class="com.entropy.dao.UserMapper"/>
</mappers>
```

测试

```java
//注解实现查询
@Test
public void test01(){
        SqlSession sqlSession=MyBatisUtils.getSqlSession();

        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);

        List<User> all=userMapper.getAll();

        for(User user:all){
        System.out.println(user);
        }

        sqlSession.close();
        }
```

**本质上是通过底层的反射机制实现, 底层使用了动态代理**

##### 3.注解下CRUD操作的实现

为了方便测试, 在MyBatis工具类中默认开启事务自动提交机制

```java
public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession(true);
        }
```

在接口上增加注解

```java
//@Param注解的作用是给参数命名, 参数命名后就能根据名字得到参数值, 准确地将参数传递给sql语句
//当只有一个参数时可以不写@Param注解, 当存在多个参数时则必须在所有参数前面加@Param注解
@Select("select * from user where id = #{id}")
User getById(@Param("id") Integer id);

@Insert("insert into user(id,name,password) values (#{id},#{name},#{password})")
int add(User user);

@Update("update user set name=#{name},password=#{password} where id=#{id}")
int update(User user);

@Delete("delete from user where id = #{id}")
int delete(@Param("id") Integer id);
```

**关于@Param()注解**

- 基本类型的参数或String类型需要添加注解
- 引用类型不需要添加注解
- 如果只有一个基本类型的话, 可以不添加注解, 但是建议添加上
- SQL中#{}引用的的内容就是@Param中设定的属性名

#### Lombok插件

Lombok能够极大简化冗长的样板式代码(例如构造方法, Getter和Setter等方法)

**1.在IDEA中安装Lombok插件**

**2.在项目中引入Lombok的依赖**

**3.在实体类上添加相应的注解**

```java
@Data //自动生成Getter和Setter方法
@AllArgsConstructor //自动生成有参构造方法
@NoArgsConstructor //自动生成无参构造方法
@ToString //自动生成ToString方法
@EqualsAndHashCode //自动生成equals和hashCode方法
```

#### 多对一逻辑关系处理

案例: 多位学生与一位教师

- 对于学生, 多位学生对应一位教师

##### 模拟案例进行处理

1.测试数据sql语句

```mysql
create table teacher
(
    id   int(10) not null,
    name varchar(30) default null,
    primary key (id)
) engine = InnoDB
  default charset = utf8;

insert into teacher(id, name)
values ('1', 'life');

create table student
(
    id         int(10) not null,
    name       varchar(30) default null,
    teacher_id int(10)     default null,
    primary key (id),
    foreign key (teacher_id) references teacher (id)
) engine = InnoDB
  default charset = utf8;

insert into student(id, name, teacher_id)
values (1, 'happy', 1);
insert into student(id, name, teacher_id)
values (2, 'worry', 1);
insert into student(id, name, teacher_id)
values (3, 'sad', 1);
insert into student(id, name, teacher_id)
values (4, 'angry', 1);
insert into student(id, name, teacher_id)
values (5, 'belief', 1);
insert into student(id, name, teacher_id)
values (6, 'friendship', 1);
```

2.引入lombok依赖(非必要)

```xml

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.16</version>
</dependency>
```

3.创建实体类[Teacher](MyBatis_02/src/main/java/com/entropy/pojo/Teacher.java)
和[Student](MyBatis_02/src/main/java/com/entropy/pojo/Student.java)

4.创建接口[TeacherMapper](MyBatis_02/src/main/java/com/entropy/dao/TeacherMapper.java)
和[StudentMapper](MyBatis_02/src/main/java/com/entropy/dao/StudentMapper.java)

5.创建xml文件[TeacherMapper.xml](MyBatis_02/src/main/resources/com/entropy/dao/TeacherMapper.xml)
和[StudentMapper.xml](MyBatis_02/src/main/resources/com/entropy/dao/StudentMapper.xml)

6.在核心配置文件中扫描包绑定接口

7.测试

#### 一对多逻辑关系处理

案例: 多位学生与一位教师

- 对于教师, 一位教师对应多位学生

##### 模拟案例进行处理

基本步骤同上

**小结**

1.多对一关系中对象引用类型使用**association**标签映射

2.一对多关系中集合类型使用**collection**标签映射

3.**javaType**属性一般用于**association**标签中指定具体的引用类型

4.**ofType**属性一般用于**collection**标签中指定具体的泛型

#### 动态SQL

MyBatis支持的动态SQL, 能够根据不同的情况生成不同的SQL语句

##### 1.主要标签

**if**、**choose(搭配when、otherwise使用)**、**trim(搭配where、set使用)**、**foreach**

##### 2.环境搭建

```mysql
create table blog
(
    id          varchar(50)  not null comment '博客id',
    title       varchar(100) not null comment '博客标题',
    author      varchar(30)  not null comment '博客作者',
    create_time datetime     not null comment '创建时间',
    views       int(30)      not null comment '浏览量'
) engine = InnoDB
  default charset = utf8;
```

##### 3.编写配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--configuration: 核心配置文件-->
<configuration>
    <!--引入外部配置文件-->
    <properties resource="db.properties"/>

    <settings>
        <!--标准的日志工厂实现-->
        <setting name="logImpl" value="STDOUT_LOGGING"/>
        <!--是否开启驼峰命名自动映射, 即从经典数据库列名 A_COLUMN 映射到经典 Java属性名 aColumn-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>

    <!--可以给实体类起别名-->
    <typeAliases>
        <!--typeAlias能够自定义命名-->
        <!--<typeAlias type="com.entropy.pojo.User" alias="User"/>-->

        <!--package默认直接以类名作为全限定类命名-->
        <!--注意name值的范围是包名, 该包名下的类均以类名作为全限定类命名-->
        <package name="com.entropy.pojo"/>
    </typeAliases>

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

    <!--每一个Mapper.XML都需要在Mybatis核心配置文件中注册！-->
    <mappers>
        <mapper class="com.entropy.dao.BlogMapper"/>
    </mappers>
</configuration>
```

##### 4.编写实体类

```java

@Data
public class Blog {
    private String id;
    private String title;
    private String author;
    private Date createTime; //属性名与字段名不一致, 开启驼峰命名自动映射
    private Integer views;
}
```

##### 5.编写Mapper接口和Mapper.xml文件

```java
public interface BlogMapper {
    //新增数据
    int addBlog(Blog blog);

    //查询博客
    List<Blog> getBlogByIf(Map map);

    List<Blog> getBlogByChoose(Map map);

    //更新博客
    int updateBlog(Map map);

    //遍历查询博客
    List<Blog> getBlogByForeach(Map map);
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.entropy.dao.BlogMapper">

    <insert id="addBlog" parameterType="Blog">
        insert into blog (id,title,author,create_time,views)
        values (#{id},#{title},#{author},#{createTime},#{views});
    </insert>

    <!--查询博客-->
    <!--if标签具有与(and)的逻辑关系-->
    <select id="getBlogByIf" parameterType="map" resultType="Blog">
        select * from blog
        <where>
            <if test="title != null">
                title = #{title}
            </if>
        </where>
    </select>

    <!--choose标签相当于switch, when标签相当于case, otherwise标签相当于default-->
    <select id="getBlogByChoose" parameterType="map" resultType="Blog">
        select * from blog
        <where>
            <choose>
                <when test="title != null">
                    title = #{title}
                </when>
                <when test="author != null">
                    and author = #{author}
                </when>
                <otherwise>
                    and views = #{views}
                </otherwise>
            </choose>
        </where>
    </select>

    <!--更新博客-->
    <!--where标签和set标签类似, 能够自动去除在使用了if标签后留下的一些不需要的关键字-->
    <!--where标签能够去除if标签判断后留下的多余的and关键字-->
    <!--set标签能够去除if标签判断后留下的多余的 , 逗号-->
    <!--trim标签是一个自定义标签, 能够实现自定义where标签和set标签的功能-->
    <update id="updateBlog" parameterType="map">
        update blog
        <set>
            <if test="title != null">
                title = #{title},
            </if>
            <if test="author != null">
                author = #{author}
            </if>
        </set>
        where id = #{id}
    </update>

    <!--遍历查询-->
    <select id="getBlogByForeach" parameterType="map" resultType="Blog">
        select * from blog
        <where>
            <foreach collection="views" item="view" open="and (" close=")" separator="or">
                views=#{view}
            </foreach>
        </where>
    </select>

    <sql id="add">
        <if test="title != null">
            title = #{title}
        </if>
        <if test="author != null">
            and author = #{author}
        </if>
    </sql>
</mapper>
```

##### 6.UUID通用唯一识别码生成工具类

```java
public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Test
    public void test() {
        //每次获得的UUID都是全局唯一的, 适用于分布式系统设计
        System.out.println(UUIDUtils.getUUID());
        System.out.println(UUIDUtils.getUUID());
    }
}
```

##### 7.测试

```java
public class BlogTest {

    //新增数据
    @Test
    public void addBlog() throws ParseException {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);

        Blog blog = new Blog();
        blog.setId(UUIDUtils.getUUID());
        blog.setTitle("linux");
        blog.setAuthor("unknown");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        date = simpleDateFormat.parse(format);
        blog.setCreateTime(date);
        blog.setViews(123);

        blogMapper.addBlog(blog);

        blog.setId(UUIDUtils.getUUID());
        blog.setTitle("web6.0");
        blog.setAuthor("future");
        blog.setCreateTime(date);
        blog.setViews(111);

        blogMapper.addBlog(blog);

        blog.setId(UUIDUtils.getUUID());
        blog.setTitle("jdk24");
        blog.setAuthor("future");
        blog.setCreateTime(date);
        blog.setViews(100);

        blogMapper.addBlog(blog);

        blog.setId(UUIDUtils.getUUID());
        blog.setTitle("IDE");
        blog.setAuthor("null");
        blog.setCreateTime(date);
        blog.setViews(2351);

        blogMapper.addBlog(blog);

        sqlSession.close();
    }

    //查询博客数据
    @Test
    public void getBlogByIf() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Map map = new HashMap();
        map.put("title", "web6.0"); //输出日志中自动拼接if标签里的内容
//        map.put("author", "future");
        List<Blog> blogByMap = blogMapper.getBlogByIf(map);

        for (Blog blog : blogByMap) {
            System.out.println(blog);
        }

        sqlSession.close();
    }

    @Test
    public void getBlogByChoose() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Map map = new HashMap();
//        map.put("title", "web6.0"); //若title的值传递过去, 则不再判断author的值
//        map.put("author", "future"); //若title的值未传递过去, 则判断author的值
        map.put("views", 111); //若title和author的值均未传递过去, 则默认判断views的值
        List<Blog> blogByMap = blogMapper.getBlogByChoose(map);

        for (Blog blog : blogByMap) {
            System.out.println(blog);
        }

        sqlSession.close();
    }

    //更新博客
    @Test
    public void updateBlog() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);

        Map map = new HashMap();
        map.put("title", "funny");
        map.put("author", "Fun");
        map.put("id", "add58848b6844c99911191c57890f4ea");

        blogMapper.updateBlog(map);

        sqlSession.close();
    }

    //遍历查询
    @Test
    public void getBlogByForeach() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);

        Map map = new HashMap();
        ArrayList<Integer> views = new ArrayList<Integer>();
        views.add(123);
        views.add(111);
        views.add(2351);
        map.put("views", views);

        List<Blog> blogs = blogMapper.getBlogByForeach(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }
}
```

##### 关于trim标签

**where标签等价的trim标签**

```xml

<trim prefix="WHERE" prefixOverrides="AND |OR ">
    ...
</trim>
```

**set标签等价的trim标签**

```xml

<trim prefix="SET" suffixOverrides=",">
    ...
</trim>
```

##### 关于SQL片段

SQL片段是从SQL语句中抽取的公共部分

```xml
    <!--SQL片段-->
<sql id="add">
    <if test="title != null">
        title = #{title}
    </if>
    <if test="author != null">
        and author = #{author}
    </if>
</sql>

        <!--通过include标签引用SQL片段-->
<select id="getBlogByIf" parameterType="map" resultType="Blog">
select * from blog
<where>
    <include refid="add"/>
</where>
</select>
```

**注意: SQL片段最好是在单表的基础上进行抽取, 且SQL片段本身不能使用where标签**

#### 缓存

##### 1.简介

- 缓存是一种临时存储在内存中的数据
- 对于需要经常查询的数据, 可以临时存储在内存中, 从内存中进行查询, 而不需要再从磁盘中查询, 能够大大提高查询效率,
  解决高并发系统性能问题
- 使用缓存减少了与数据交互的次数, 减轻系统负担, 提高效率
- 缓存的数据一般是经常查询但不会频繁修改的数据

##### 2.MyBatis缓存

- MyBatis框架也提供了缓存的功能, 具有一个强大的查询缓存的特性, 能够自定义配置缓存, 提高效率
- MyBatis框架中定义了两级缓存: **一级缓存**和**二级缓存**
    - 默认情况下, 只启用一级缓存, SqlSession级别的缓存, 也称为本地缓存
    - MyBatis定义了缓存接口Cache, 可以通过Cache接口来定义二级缓存, Mapper级别的缓存

##### 3.一级缓存

- 同一次会话期间, 从数据库查询到的数据会存储在本地缓存中
- 之后需要再次查询相同的数据, 就能够直接从缓存中获取

```java
    //一级缓存测试
@Test
public void localCache(){
        SqlSession sqlSession=MyBatisUtils.getSqlSession();

        BlogMapper blogMapper=sqlSession.getMapper(BlogMapper.class);

        Map map=new HashMap();
        map.put("title","web6.0");
        List<Blog> blog=blogMapper.getBlogByIf(map);

//        sqlSession.clearCache(); //清理之后, 下一次相同的查询需要重新查询数据库
//        System.out.println("手动清理缓存");

        System.out.println("分割线1......");

        //本地缓存, 一级缓存, 从输出日志中可以看到这里实际上并没有执行SQL语句
        //它的数据是从缓存中获取的
        List<Blog> localCache=blogMapper.getBlogByIf(map);

        System.out.println("分割线2......");

        System.out.println(blog==localCache); //==比较的是地址, 不是内容

        sqlSession.close();
        }
```

**缓存失效的情况**

- 首次查询不同的数据, 查询之前数据还未被缓存
- 增删改操作改变了原来的数据, 需要重新缓存
- Mapper级别的缓存, 一级缓存不支持
- 使用方法手动清理了缓存
- 一级缓存的有效范围, 仅在获取Sqlsession实例和关闭Sqlsession实例这一期间

##### 4.二级缓存

- 二级缓存也称全局缓存, 其作用域远大于一级缓存

- Mapper级别缓存, 一个mapper.xml文件对应一个二级缓存

- 工作机制

    - 一次会话查询的数据会先存储在当前会话的一级缓存中
    - 当前会话关闭后, 如果启用了二级缓存, 则当前会话一级缓存的数据会迁移到二级缓存中
    - 下一次会话或者另一个SqlSession实例就能从二级缓存中获取数据
    - 不同的mapper都有各自对应的二级缓存, 但同一个mapper的多个SqlSession实例都能够共享二级缓存数据

  **二级缓存配置**

  二级缓存需要在配置文件中手动进行配置

  核心配置文件(这一步可以忽略, 按照默认配置即可)

  ```xml
  <!--显式地开启全局缓存, 默认是开启的-->
  <setting name="cacheEnabled" value="true"/>
  <!--如果之前手动设置了false, 则需要删除、注释掉该配置或者改为true-->
  ```

  mapper.xml文件(不可忽略)

  ```xml
  <!--在当前Mapper.xml中使用二级缓存-->
  <cache/>
  ```

  最简单的配置只需要添加cache标签即可, 也可以自行添加属性参数

  ```xml
  <!--在当前Mapper.xml中使用二级缓存-->
  <cache
      eviction="FIFO"
      flushInterval="60000"
      size="512"
      readOnly="true"/>
  <!--
  相关属性说明
  eviction: 缓存的收回策略
  LRU(最近最少使用的)移除最长时间不被使用的对象, 这是默认值
  FIFO(先进先出)按对象进入缓存的顺序来移除它们
  SOFT(软引用)移除基于垃圾回收器状态和软引用规则的对象
  WEAK(弱引用)更积极地移除基于垃圾收集器状态和弱引用规则的对象
  
  flushinterval: 刷新间隔
  设置缓存多长时间清空一次, 单位为毫秒值, 默认不清空
  
  size: 引用数目
  设置缓存可以存放的引用数目, 可以被设置为任意正整数, 默认值是1024
  
  readOnly: 是否只读
  true: 只读, 设置为true后, mybatis认为所有从缓存中获取数据的操作都是只读操作, 不会修改数据, 因此为了加快获取速度, 一般会直接将数据在缓存中的引用交给用户, 虽然速度快, 但不安全, 实际上数据仍可在后台修改
  false: 非只读, 设置为false后, mybatis认为获取的数据可能会被修改, 因此会利用序列化和反序列化的技术克隆一份新的数据给用户, 虽然速度慢, 但安全
  默认是false
  -->
  ```

  **注意: 当需要对缓存的数据进行持久化操作时, 则必须要在实体类实现序列化, 否则会出现Cause:
  java.io.NotSerializableException的错误**

  实体类实现序列化, 只需要添加`implements Serializable`即可

   ```java
  @Data
  public class Blog implements Serializable {
      private String id;
      private String title;
      private String author;
      private Date createTime; //属性名与字段名不一致, 开启驼峰命名自动映射
      private Integer views;
  }
   ```

  测试

  ```java
      //二级缓存测试
      @Test
      public void mapperCache() {
          SqlSession sqlSession = MyBatisUtils.getSqlSession();
          BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
          Map map = new HashMap();
          map.put("title", "web6.0");
          List<Blog> blogByIf = blogMapper.getBlogByIf(map);
          System.out.println("第一个实例: " + blogByIf);
  
          sqlSession.close(); //需要注意的是二级缓存需要先有一个会话关闭, 才会存储数据
  
          System.out.println("分割线......");
  
          //另外重新创建一个SqlSession实例, 模拟下一次会话
          sqlSession = MyBatisUtils.getSqlSession();
          blogMapper = sqlSession.getMapper(BlogMapper.class);
          //从输出日志中可以看到这里并没有执行SQL语句
          //它的数据是从上一次会话的二级缓存中获取的
          List<Blog> blogAnother = blogMapper.getBlogByIf(map);
          System.out.println("第二个实例: " + blogAnother);
  
          sqlSession.close();
      }
  ```

**补充**

- **二级缓存是与mapper.xml相对应的, 但复杂查询如果涉及到多表的逻辑关系, 则需要注意相同的数据被多次缓存, 修改数据时只是修改了其中一个缓存**

- **首次使用二级缓存时, 二级缓存的数据需要先关闭一个会话, 才会存储数据, 即当整个程序运行的生命周期里仅存在一次会话,
  二级缓存也就不会发挥任何作用**
- **二级缓存一般用于具有多个会话的开发项目中, 实际上分布式系统项目基本都会存在多个会话**

##### 5.自定义缓存ehcache

MyBatis框架还提供自定义缓存的功能**ehcache**

**Ehcache**是一种广泛使用的开源Java分布式缓存, 主要面向通用缓存, Java EE和轻量级容器

**配置自定义缓存**

在pom.xml中引入依赖

```xml

<dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.1.0</version>
</dependency>
```

在mapper.xml中指定ehcache缓存

```xml

<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
```

在resources目录下创建并编写ehcache.xml(名称固定)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false">
    <!--
    diskStore：为缓存路径，ehcache分为内存和磁盘两级，此属性定义磁盘的缓存位置。参数解释如下：
    user.home – 用户主目录
    user.dir – 用户当前工作目录
    java.io.tmpdir – 默认临时文件路径
    -->
    <diskStore path="java.io.tmpdir/Tmp_EhCache"/>
    <!--
    defaultCache：默认缓存策略，当ehcache找不到定义的缓存时，则使用这个缓存策略。只能定义一个。
    -->
    <!--
    name:缓存名称。
    maxElementsInMemory:缓存最大数目
    maxElementsOnDisk：硬盘最大缓存个数。
    eternal:对象是否永久有效，一但设置了，timeout将不起作用。
    overflowToDisk:是否保存到磁盘，当系统当机时
    timeToIdleSeconds:设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
    timeToLiveSeconds:设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
    diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
    diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
    diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
    memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
    clearOnFlush：内存数量最大时是否清除。
    memoryStoreEvictionPolicy:可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。
    FIFO，first in first out，这个是大家最熟的，先进先出。
    LFU， Less Frequently Used，就是上面例子中使用的策略，直白一点就是讲一直以来最少被使用的。如上面所讲，缓存的元素有一个hit属性，hit值最小的将会被清出缓存。
    LRU，Least Recently Used，最近最少使用的，缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。
    -->
    <defaultCache
            eternal="false"
            maxElementsInMemory="10000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="259200"
            memoryStoreEvictionPolicy="LRU"/>
    <cache
            name="cloud_user"
            eternal="false"
            maxElementsInMemory="5000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="1800"
            memoryStoreEvictionPolicy="LRU"/>
</ehcache>
```

测试

```java
    //自定义ehcache缓存测试
@Test
public void ehcache(){
        //创建缓存管理器
        CacheManager cacheManager=CacheManager.create("./src/main/resources/ehcache.xml");
        //获取缓存对象
        Cache cache=cacheManager.getCache("cloud_user");

        //创建元素
        SqlSession sqlSession=MyBatisUtils.getSqlSession();
        BlogMapper blogMapper=sqlSession.getMapper(BlogMapper.class);
        Map map=new HashMap();
        map.put("title","web6.0");
        List<Blog> blogByIf=blogMapper.getBlogByIf(map);
        System.out.println("blogByIf = "+blogByIf);
        sqlSession.close();

        Element element=new Element("title",blogByIf);

        //将元素添加到缓存
        cache.put(element);
        //获取缓存
        Element value=cache.get("title");
        System.out.println(value);
        System.out.println(value.getObjectValue());


        //删除元素
        cache.remove("title");
        System.out.println("删除元素");
        System.out.println(cache.get("title"));

        //刷新缓存
        cache.flush();

        //关闭缓存管理器
        cacheManager.shutdown();
        }
```
