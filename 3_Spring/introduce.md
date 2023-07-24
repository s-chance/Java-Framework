### Spring

#### 简介

**Spring框架**是Java在**服务器开发领域**的**核心**, 是目前任何基于Java的服务器开发项目不可或缺的**核心框架**

**Spring理念**: 使现有的技术更加实用, Spring本身就整合了大量现有的框架技术

**SSH集成框架**: **Structs2+Spring+Hibernate**, 但**SSH**目前基本只有少部分老项目还在使用, 基本上已经被淘汰

**SSM集成框架**: **Spring+SpringMVC+MyBatis**, 相对于**SSH**, **SSM**使用得更多, 但目前也趋向于老项目的形势(由于**SpringBoot**的出现), 但学习**SSM**, 有助于理解Spring的运行机制

[Spring官网](http://spring.io/)

[Github仓库地址](https://github.com/spring-projects)

maven依赖引入

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.5.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.2.5.RELEASE</version>
</dependency>
```

#### 优点

- Spring是开源的免费的框架(容器)
- Spring是一种非入侵式的轻量级框架
- 控制反转(IOC), 面向切面编程(AOP)
- 支持事务处理以及对多个框架的整合

#### 组成

Spring 框架是一个分层架构, 由**七大模块**组成。Spring模块构建在核心容器之上, 核心容器定义了创建、配置和管理**bean**的方式

七大模块的简介

> 组成 Spring 框架的每个模块（或组件）都可以单独存在，或者与其他一个或多个模块联合实现。每个模块的功能如下：
>
> - **Spring Core核心容器**：核心容器提供 Spring 框架的基本功能。核心容器的主要组件是 BeanFactory，它是工厂模式的实现。BeanFactory 使用*控制反转*（IOC） 模式将应用程序的配置和依赖性规范与实际的应用程序代码分开。
> - **Spring Context**：Spring Context是一个配置文件，向 Spring 框架提供上下文信息。Spring 上下文包括企业服务，例如 JNDI、EJB、电子邮件、国际化、校验和调度功能。
> - **Spring AOP**：通过配置管理特性，Spring AOP 模块直接将面向切面的编程功能 , 集成到了 Spring 框架中。所以，可以很容易地使 Spring 框架管理任何支持 AOP的对象。Spring AOP 模块为基于 Spring 的应用程序中的对象提供了事务管理服务。通过使用 Spring AOP，不用依赖组件，就可以将声明性事务管理集成到应用程序中。
> - **Spring DAO**：JDBC DAO 抽象层提供了有意义的异常层次结构，可用该结构来管理异常处理和不同数据库供应商抛出的错误消息。异常层次结构简化了错误处理，并且极大地降低了需要编写的异常代码数量（例如打开和关闭连接）。Spring DAO 的面向 JDBC 的异常遵从通用的 DAO 异常层次结构。
> - **Spring ORM**：Spring 框架插入了若干个 ORM 框架，从而提供了 ORM 的对象关系工具，其中包括 JDO、Hibernate 和 iBatis SQL Map。所有这些都遵从 Spring 的通用事务和 DAO 异常层次结构。
> - **Spring Web 模块**：Web 上下文模块建立在应用程序上下文模块之上，为基于 Web 的应用程序提供了上下文。所以，Spring 框架支持与 Jakarta Struts 的集成。Web 模块还简化了处理多部分请求以及将请求参数绑定到域对象的工作。
> - **Spring MVC 框架**：MVC 框架是一个全功能的构建 Web 应用程序的 MVC 实现。通过策略接口，MVC 框架变成为高度可配置的，MVC 容纳了大量视图技术，其中包括 JSP、Velocity、Tiles、iText 和 POI。

#### 拓展

**Spring Boot**

- 一个快速开发的脚手架
-  基于SpringBoot可以快速的开发单个微服务
-  约定大于配置

**Spring Cloud**

-  本身就是基于SpringBoot实现的

#### 缺点

- 发展了很久之后, 使用Spring时配置过于繁琐, 违背了原来的理念

### IOC理论

#### 模拟实现原理

1.创建一个空白maven项目

2.编写Dao接口

```java
public interface UserDao {
    //这里作为模拟演示, 不需要返回值, 在实现方法内部输出即可
    void getUser();
}
```

3.编写Dao实现类

```java
public class MySQLImpl implements UserDao {
    public void getUser() {
        System.out.println("getUser...By MySQL");
    }
}
```

4.编写Service接口

```java
public interface UserService {
    void getUser();
}
```

5.编写Service实现类

```java
public class UserServiceImpl implements UserService {
    private UserDao userDao = new MySQLImpl();
    public void getUser() {
        userDao.getUser();
    }
}
```

6.测试

```java
public class IOCTest {

    @Test
    public void test() {
        UserService service = new UserServiceImpl();   
    	service.getUser();
    }
}
```

**引出问题**

增加一个Dao实现类

```java
public class OracleImpl implements UserDao {
    public void getUser() {
        System.out.println("getUser...By Oracle");
    }
}
```

在Service里进行修改

```java
public class UserServiceImpl implements UserService {
    private UserDao mysqlDao = new MySQLImpl();
    private UserDao oracleDao = new OracleImpl();
    
    public void getUserByMysql() {
        mysqlDao.getUser();
    }
    
    public void getUserByOracle() {
        oracleDao.getUser();
    }
}
```

可见当UserDao接口的实现类增加时, 需要相应地对UserServiceImpl进行修改。如果UserDao接口存在大量的实现类, 那每次变动就需要修改大量代码, 代码的**耦合性过高**

**解决思路**

在需要使用到Dao实现类的地方, 不直接去实例化, 而是利用**set方法注入**。这个时候, Dao接口的实现类不再是原来固定的实现形式, 而是能够灵活地注入指定的Dao实现类对象, 大大降低了代码的耦合性

```java
	private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
```

这就是IOC的原型

#### IOC本质

**控制反转IOC(Inversion of Control)**, 是一种**设计思想**, **依赖注入DI**(Dependency Injection)是实现IOC的一种方式。在没有控制反转的程序中, 对象的创建与对象间的依赖关系是固定编写的, 由程序本身决定。引入控制反转后, 对象的创建转移给了第三方。

**IOC是Spring框架的核心内容**, 可以通过XML配置, 注解配置实现, 最新版的Spring能够零配置实现IOC。采用XML方式配置Bean的时候, Bean的定义信息是和实现分离的, 而采用注解的方式可以把两者合为一体, Bean的定义信息直接以注解的形式定义在实现类中, 从而达到了零配置的目的

Spring容器在初始化时先读取配置文件, 根据配置文件或者元数据创建并组织对象存入容器中, 在使用时从容器中获取对象

**控制反转是一种通过描述（XML或注解）借助第三方去生产或获取特定对象的方式**

### Spring简单使用

#### 步骤

**1.引入依赖**

```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.5.RELEASE</version>
        </dependency>
```

**2.编写实体类**

```java
public class User {

    private String name;

    public void show() {
        System.out.println("Hello!This is " + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

**3.编写Spring配置文件, 命名为beans.xml, 位于resources目录下**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--使用Spring来创建对象, 在Spring中对象称为Bean-->

    <!--id属性: 用于标识单个bean, 类似于变量名-->
    <!--class属性: 用于定义bean的数据类型, 使用全限定类名-->
    <!--property标签: 给对象中指定的属性赋值-->
    <!--注意: property标签中的指定的name属性值在其类中必须存在对应的set方法-->
    <bean id="user" class="com.entropy.pojo.User">
        <property name="name" value="Spring"/>
    </bean>
</beans>
```

**4.测试**

```java
public class BeanTest {
    public static void main(String[] args) {
        //获取ApplicationContext, 即Spring中的内容
        ApplicationContext beans = new ClassPathXmlApplicationContext("beans.xml");

        //通过Spring创建对象
        User user = (User) beans.getBean("user");

        System.out.println(user);
        user.show();
    }
}
```

对象通过Spring创建, 对象的属性通过Spring容器进行设置。程序本身不再去主动创建对象, 而是由Spring进行注入

依赖注入就是以set方法为原理实现的, 更详细的可以去浏览ClassPathXmlApplicationContext的底层源码

#### Spring实现控制反转

beans.xml中完成对象的创建

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--使用Spring来创建对象, 在Spring中对象称为Bean-->

    <!--id属性: 用于标识单个bean, 类似于变量名-->
    <!--class属性: 用于定义bean的数据类型, 使用全限定类名-->
    <!--property标签: 给对象中指定的属性赋值-->
    <!--注意: property标签中的指定的name属性值在其类中必须存在对应的set方法-->
    <bean id="user" class="com.entropy.pojo.User">
        <property name="name" value="Spring"/>
    </bean>

    <bean id="mysqlImpl" class="com.entropy.dao.Impl.MySQLImpl"/>
    <bean id="oracleImpl" class="com.entropy.dao.Impl.OracleImpl"/>

    <bean id="userService" class="com.entropy.service.Impl.UserServiceImpl">
        <!--这里的name属性值在其具体类中必须存在对应的set方法-->
        <!--value属性的属性值是基本数据类型, 是具体值, 用于直接赋值-->
        <!--ref属性的属性值是bean类型, 是Spring容器中的对象, 用于在容器中引用另一个bean对象-->
        <property name="userDao" ref="mysqlImpl"/>
    </bean>
</beans>
```

测试

```java
public class BeanTest {
    public static void main(String[] args) {
        //获取ApplicationContext, 即Spring中的内容
        ApplicationContext beans = new ClassPathXmlApplicationContext("beans.xml");

        //通过Spring创建对象
        User user = (User) beans.getBean("user");

        System.out.println(user);
        user.show();

        //通过Spring实现控制反转
        UserService userService = (UserService) beans.getBean("userService");
        //如果想修改具体实现类, 只需要在xml文件中修改ref的属性值即可
        userService.getUser();

    }
}
```

### IOC创建对象的方式

#### 1.无参构造(默认自动调用)

在User类中添加调用无参构造的提示信息

```java
    public User() {
        System.out.println("NoArgsConstructor...");
    }
```

在测试类中通过bean获取对象时即可看到无参构造的提示信息

#### 2.有参构造

```xml
    <!--有参构造, 通过下标赋值-->
    <bean name="userConstructorByIndex" class="com.entropy.pojo.User">
        <constructor-arg index="0" value="index"/>
    </bean>

    <!--有参构造, 通过类型创建, 不推荐-->
    <bean name="userConstructorByType" class="com.entropy.pojo.User">
        <constructor-arg type="java.lang.String" value="type"/>
    </bean>

    <!--有参构造, 通过参数名创建-->
    <bean name="userConstructorByName" class="com.entropy.pojo.User">
        <constructor-arg name="name" value="name"/>
    </bean>
```

实际上, 在配置文件加载时, 容器中的对象就已经初始化。可以在获取beans.xml时看见无参构造被调用的提示信息

### Spring配置

#### 1.别名

```xml
<!--取别名-->
<alias name="user" alias="user2"/>
```

#### 2.Bean的配置

```xml
<!--id属性: 用于标识单个bean, 类似于变量名-->
<!--class属性: 用于定义bean的数据类型, 使用全限定类名-->
<!--name属性: 别名, 可以取多个-->
<bean id="user" class="com.entropy.pojo.User" name="user">
    <property name="name" value="Spring"/>
</bean>
```

#### 3.import

**import主要是将多个xml配置文件导入合并到一个文件中, 常用于团队开发**

创建一个合并多个配置的applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <import resource="beans.xml"/>
    <import resource="beans2.xml"/>
</beans>
```

使用时, 直接通过applicationContext.xml获取所有配置即可

### 依赖注入

#### 1.构造器注入

即无参构造与有参构造的实现

#### 2.set方法注入

依赖注入主要就是set注入, Bean对象的创建依赖于Spring容器, Bean对象的属性由容器来注入

各种数据类型的注入示例

实体类

```java
public class People {
    private String name;
    private Address address;
    private String[] books;
    private List<String> works;
    private Map<String, String> cards;
    private Set<String> games;
    private String money;
    private Properties info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String[] getBooks() {
        return books;
    }

    public void setBooks(String[] books) {
        this.books = books;
    }

    public List<String> getWorks() {
        return works;
    }

    public void setWorks(List<String> works) {
        this.works = works;
    }

    public Map<String, String> getCards() {
        return cards;
    }

    public void setCards(Map<String, String> cards) {
        this.cards = cards;
    }

    public Set<String> getGames() {
        return games;
    }

    public void setGames(Set<String> games) {
        this.games = games;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Properties getInfo() {
        return info;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", books=" + Arrays.toString(books) +
                ", works=" + works +
                ", cards=" + cards +
                ", games=" + games +
                ", money='" + money + '\'' +
                ", info=" + info +
                '}';
    }
}

```

Address类

```java
public class Address {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                '}';
    }
}
```

xml配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <import resource="beans.xml"/>
    <import resource="beans2.xml"/>

    <bean id="address" class="com.entropy.pojo.Address">
        <property name="address" value="countryside"/>
    </bean>

    <bean id="people" class="com.entropy.pojo.People">
        <property name="name" value="element"/>
        <!--address不属于基本数据类型, 需要通过ref的方式注入-->
        <property name="address" ref="address"/>
        <!--数组注入-->
        <property name="books">
            <array>
                <value>math</value>
                <value>English</value>
                <value>History</value>
            </array>
        </property>

        <!--list数组-->
        <property name="works">
            <list>
                <value>music</value>
                <value>travel</value>
                <value>fishing</value>
            </list>
        </property>

        <!--map-->
        <property name="cards">
            <map>
                <entry key="ID_Card" value="123456789"/>
                <entry key="Work_Card" value="112233"/>
            </map>
        </property>

        <!--set-->
        <property name="games">
            <set>
                <value>escape</value>
                <value>block</value>
                <value>speed</value>
            </set>
        </property>

        <!--null-->
        <property name="money">
            <null/>
        </property>

        <!--Properties-->
        <property name="info">
            <props>
                <prop key="name">Mike</prop>
                <prop key="age">22</prop>
                <prop key="email">123@123.com</prop>
            </props>
        </property>
    </bean>
</beans>
```

#### 3.其他方式注入

**p命名空间注入和c命名空间注入**

1. p命名空间注入: 需要引入约束文件, 在配置文件最上面的beans标签中加入以下语句

   ```xml
   xmlns:p="http://www.springframework.org/schema/p"
   ```

   最终效果如下

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:p="http://www.springframework.org/schema/p"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">
       
       <!--P命名空间注入, 通过set注入-->
       <bean id="p" class="com.entropy.pojo.User" p:name="point"/>
   </beans>
   ```
   
   测试
   
   ```java
   public class BeanTest {
       public static void main(String[] args) {
           //获取ApplicationContext, 即Spring中的内容
           ApplicationContext beans = new ClassPathXmlApplicationContext("beans.xml");
           //通过p命名空间获取对象
           User p = (User) beans.getBean("p");
           p.show();
       }
   }
   ```

2. c命名空间注入: 需要引入约束文件, 在配置文件最上面的beans标签中加入以下语句

   ```xml
   xmlns:c="http://www.springframework.org/schema/c"
   ```

   最终效果如下

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:p="http://www.springframework.org/schema/p"
          xmlns:c="http://www.springframework.org/schema/c"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">
       
       <!--C命名空间注入, 通过构造器construct-args注入-->
       <bean id="c" class="com.entropy.pojo.User" c:name="construct"/>
   </beans>
   ```

   测试

   ```java
   public class BeanTest {
       public static void main(String[] args) {
           //获取ApplicationContext, 即Spring中的内容
           ApplicationContext beans = new ClassPathXmlApplicationContext("beans.xml");
           //通过c命名空间获取对象
           User c = (User) beans.getBean("c");
           c.show();
       }
   }
   ```

**p命名空间注入与c命名空间注入的区别**

- p命名空间注入必须要求对象里存在对应的**set方法**以及**无参构造方法**, 但可以不用**有参构造方法**

- c命名空间注入必须要求对象里存在对应的**构造方法**, 但可以不用**set方法**


#### 4.Bean的作用域

组成应用程序主体以及由Spring IOC容器管理的对象, 称为Bean。Bean就是由IOC容器初始化、装配、管理的对象

##### 1.Singleton

Spring IOC容器中**仅存在一个共享的bean实例**, 并且所有对bean的请求, 只要id匹配, 就会只返回bean的同一实例

[单例模式 | 菜鸟教程 (runoob.com)](https://www.runoob.com/design-pattern/singleton-pattern.html)

**Singleton就是单例模式的作用域**, 在创建容器时就自动创建了一个bean对象, 每次获取到的对象都是同一个对象。Singleton作用域是Spring中的缺省作用域(即默认的作用域), 也可以显式地在xml中设置

```xml
<bean id="c" class="com.entropy.pojo.User" c:name="construct" scope="singleton"/>
```

测试

```java
public class BeanTest {
    public static void main(String[] args) {
        //获取ApplicationContext, 即Spring中的内容
        ApplicationContext beans = new ClassPathXmlApplicationContext("beans.xml");
        //通过c命名空间获取对象
        User c = (User) beans.getBean("c");
        c.show();
        User c1 = beans.getBean("c", User.class);
        System.out.println(c == c1);
    }
}
```

##### 2.Prototype

**一个bean对应多个对象实例**。Prototype作用域会导致在每次对该bean请求(将其注入另一个bean或者以程序的方式调用容器的getBean方法)时都会创建一个新的bean实例

[原型模式 | 菜鸟教程 (runoob.com)](https://www.runoob.com/design-pattern/prototype-pattern.html)

**Prototype就是原型模式的作用域**, 在创建容器时没有进行实例化, 当获取bean时才会创建一个对象, 并且每次获取bean对象时都会重新创建对象

原型模式配置

```xml
<bean id="c" class="com.entropy.pojo.User" c:name="construct" scope="prototype"/>
```

测试

```java
public class BeanTest {
    public static void main(String[] args) {
        //获取ApplicationContext, 即Spring中的内容
        ApplicationContext beans = new ClassPathXmlApplicationContext("beans.xml");
        //通过c命名空间获取对象
        User c = (User) beans.getBean("c");
        c.show();
        User c1 = beans.getBean("c", User.class);
        System.out.println(c == c1);
    }
}
```

##### 3.其他的request，session，application专门用于web开发

### Bean自动装配

- 自动装配是Spring实现bean依赖的一种方法
- 自动装配会在上下文中为bean寻找bean的依赖

**Spring中bean的装配机制**

- xml文件显式配置
- java文件显式配置
- **隐式的bean发现机制和自动装配**

#### 1.xml文件配置示例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="cat" class="com.entropy.pojo.Cat"/>
    <bean id="dog" class="com.entropy.pojo.Dog"/>

    <bean id="owner" class="com.entropy.pojo.Owner">
        <property name="cat" ref="cat"/>
        <property name="dog" ref="dog"/>
        <property name="name" value="petro"/>
    </bean>
</beans>
```

#### 2.byName自动装配

根据id名称来自动装配, 主要是避免编写过多的名称容易出现疏忽以及字母大小写不一致的问题

修改配置即可

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="cat" class="com.entropy.pojo.Cat"/>
    <bean id="dog" class="com.entropy.pojo.Dog"/>

    <!--byName自动装配, 需要注意依赖的bean的id需要与该bean对象的set方法对应-->
    <bean id="ownerByName" class="com.entropy.pojo.Owner" autowire="byName">
        <property name="name" value="autoByName"/>
    </bean>
</beans>
```

依赖的bean的id(如cat)需要与该bean对象内的set方法(setCat)对应, 否则以byName的规则无法找到对应的id, 无法初始化对象, 就会出现java.lang.NullPointerException的异常

**byName规则**

1. 先从带有autowire="byName"的bean对象中寻找对应的set方法, 获取set方法后面的全小写名称
2. 在Spring容器中查找对应全小写名称的id的bean对象
3. 进行注入

#### 3.byType自动装配

根据数据类型自动装配, 需要确保**bean对象的类型唯一**, 否则会出现异常

配置如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--由于byType是根据数据类型来自动装配, 因此这里的id属性可有可无, 但class属性必须唯一-->
    <bean class="com.entropy.pojo.Cat"/>
    <bean class="com.entropy.pojo.Dog"/>

    <!--byType自动装配, 需要注意对象类型唯一-->
    <bean id="ownerByType" class="com.entropy.pojo.Owner" autowire="byType">
        <property name="name" value="autoByType"/>
    </bean>
</beans>
```

如果存在多个同一数据类型的bean对象, 则会出现NoUniqueBeanDefinitionException的异常

**小结**

- **使用byName自动装配时, 确保所有bean对象的id与自动注入的属性的set方法后面的全小写名称唯一对应**
- **使用byType自动装配时, 确保所有bean对象的class与自动注入的属性的数据类型唯一对应**

#### 4.注解式自动装配

要求jdk版本1.5及以上, Spring版本2.5就已经支持注解

前提准备

导入约束, 配置注解支持

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!--注解式自动装配输入不需要手动实现注入, 但需要在Spring容器中注册-->
    <bean class="com.entropy.pojo.Cat"/>
    <bean class="com.entropy.pojo.Dog"/>
    <bean id="ownerByAnnotation" class="com.entropy.pojo.Owner"/>
    <!--注解式自动装配支持-->
    <context:annotation-config/>
</beans>
```

**@Autowired注解**

可以直接在属性上使用, 也可以写在set方法上面

在属性上使用Autowired注解则可以省略其对应的set方法的编写, 但一般推荐编写set方法

不过所有使用到的对象仍需要先注册到Spring容器中

```java
public class Owner {
    @Autowired
    private Cat cat;
    @Autowired
    private Dog dog;
    private String name;
}
```

补充: **@Qualifier**注解, 专门搭配**@Autowired**注解使用。默认情况下**@Autowired**注解是根据**byType规则**来实现自动注入的, 增加**@Qualifier**注解指定id名称后就可以根据**byName规则**来实现自动注入

自动装配环境比较复杂时, 如存在很多同一类型的bean对象, 可以通过**@Qualifier**注解指定唯一的bean对象

```java
public class Owner {
    @Autowired
    @Qualifier(value = "cat") //需要spring容器中存在id为cat的bean对象
    private Cat cat;
    @Autowired
    private Dog dog;
    private String name;
}
```

**@Resource注解**

与**@Autowired注解**的作用基本相同, 不同的是**@Resource注解**默认根据**byName规则**来实现自动注入, 若未指定name时则根据**byType规则**来实现自动注入。**@Resource能够指定name, type或者同时指定name和type**

**@Resource和@Autowired对比**

- @Resource是由J2EE提供的, 但有Spring的支持。相对@Autowired这个由Spring提供的注解, @Resource与Spring框架的耦合性更低
- 但二者在实际使用中并没有什么区别

### 注解开发

#### 1.Bean的注入

**@Component注解**

1.配置xml文件

```xml
    <!--通过扫描包的方式使注解生效-->
    <context:component-scan base-package="com.entropy.pojo"/>
```

2.在指定包下的类上增加注解

```java
@Component // 相当于<bean id="user" class="com.entropy.pojo.User"/>
public class User {
    public String name = "123";
}
```

3.测试

#### 2.属性的注入

**@Value注解**

1.可以不提供set方法, 直接添加注解实现

```java
@Component // 相当于<bean id="user" class="com.entropy.pojo.User"/>
public class User {
    @Value("123") // 相当于<property name="name" value="123"/>
    public String name;
}
```

2.如果提供了set方法, 就在set方法上添加注解

```java
@Component // 相当于<bean id="user" class="com.entropy.pojo.User"/>
public class User {

    public String name;

    @Value("123") // 相当于<property name="name" value="123"/>
    public void setName(String name) {
        this.name = name;
    }
}
```

#### 3.衍生的一些注解

以**@Component注解**为基础衍生出了其它三个注解

在MVC三层架构中: 

**@Repository对应dao层**

**@Service对应service层**

**@Controller对应web(controller)层**

这三个注解以及@Component注解的功能都是为了将某个类注册到Spring中并装配Bean, 而不需要编写大量的xml配置, 简化xml

#### 4.自动装配

具体内容见上文

简要回顾

@Autowired: 通过类型(默认), 名字自动装配

如果@Autowired不能唯一自动装配上属性, 则需要借助@Qualifier(value="xxx")

@Nullable: 标记了这个注解的字段可以为null

@Resource: 通过名字, 类型自动装配

#### 5.作用域

**@scope**

- singleton(默认的): 以单例模式创建对象。关闭工厂时, 所有对象都会销毁

- prototype: 多例模式。关闭工厂时不会销毁对象, 由内部的垃圾回收机制回收

  ```java
  @Controller
  @Scope("prototype")
  public class User {
      @Value("123")
      private String name;
  
      public String getName() {
          return name;
      }
  
      @Value("345")
      public void setName(String name) {
          this.name = name;
      }
  }
  ```

#### 6.xml与注解的对比

- xml更加万能, 适用于任何场景, 结构清晰, 易于维护
- 注解无法使用除了它自身以外的类, 维护相对复杂, 但开发方便

**建议xml与注解整合开发**

- xml负责管理Bean
- 注解负责完成属性注入
- 注解开发需要在xml中配置扫描以及注解驱动, 如果不配置扫描就需要手动配置Bean, 如果没有配置注解驱动, 注入的值都会变成null

### 通过原生Java配置Spring

完全使用Java代码的方式来实现对Spring的配置, 相当于**简单模拟SpringBoot**(但实际开发中SpringBoot还是会整合xml)

1.创建实体类

```java
@Component // 注册到Spring容器中
public class User {

    private String name;

    public String getName() {
        return name;
    }

    @Value("boot")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

2.创建配置类

```java
@Configuration // @Configuration专门用于指定配置类, 即用来替代xml配置的类
@ComponentScan("com.entropy.pojo") // 在配置类中指定扫描包
public class MyConfig {
    // 通过@Bean注解注册Bean, 相当于在xml中编写bean标签
    // 方法名相当于bean标签中的id属性
    // 返回值相当于bean标签中的class属性
    @Bean
    public User getUser() {
        return new User();
    }
}
```

3.测试

```java
public class MyTest {
    public static void main(String[] args) {
        // 使用配置类的方式来实现, 就需要通过AnnotationConfigApplicationContext来获取容器
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        User user = context.getBean("getUser", User.class);

        System.out.println(user.getName());
    }
}
```

**导入其它配置**

1.另外创建一个配置类

```java
@Configuration
public class OtherConfig {
}
```

2.在之前的配置类中导入这个配置类

```java
@Configuration // @Configuration专门用于指定配置类, 即用来替代xml配置的类
@ComponentScan("com.entropy.pojo") // 在配置类中指定扫描包
@Import(OtherConfig.class) // 导入OtherConfig配置类
public class MyConfig {
    // 通过@Bean注解注册Bean, 相当于在xml中编写bean标签
    // 方法名相当于bean标签中的id属性
    // 返回值相当于bean标签中的class属性
    @Bean
    public User getUser() {
        return new User();
    }
}
```

这种配置方式在**SpringBoot**中广泛使用

### 代理模式

**AOP底层机制**的实现就是基于**动态代理**

 代理模式分为**静态代理**和**动态代理**

#### 1.静态代理

- 抽象角色: 一般使用接口或者抽象类实现
- 真实角色: 被代理的角色
- 代理角色: 代理真实角色接收请求完成具体操作
- 客户: 访问代理角色发送请求

这里以租客表示客户、以中间商表示代理角色、以房东表示真实角色、租赁的行为表示抽象角色

```java
// 抽象角色: 租赁的行为
public interface Rent {
    public void rent();
}
```

```java
// 真实角色: 房东
public class Host implements Rent {
    @Override
    public void rent() {
        System.out.println("price: 100");
    }
}
```

```java
// 代理角色: 中间商
public class MyProxy implements Rent {

    // 真实角色委托代理角色去完成任务
    private Host host;

    public MyProxy() {
    }

    public MyProxy(Host host) {
        this.host = host;
    }

    @Override
    public void rent() {
        show();
        trade();
        pay();
    }

    public void show() {
        System.out.println("come and take a look");
    }

    public void trade() {
        System.out.println("buy it");
    }

    public void pay() {
        System.out.println("price: 120");
    }
}
```

```java
// 客户: 租客
public class Client {
    public static void main(String[] args) {

        Host host = new Host();
//        host.rent(); // 如果不找代理就是直接从房东这边交易
        MyProxy proxy = new MyProxy(host); // 如果房东选择代理, 则由代理人(中间商)来接待租客
        proxy.rent();
    }
}
```

**静态代理的优势**

- 使真实角色不需要去管理具体的复杂业务, 从原本复杂的逻辑关系中分离出一个"纯粹的模块"
- 公共业务模块则由代理角色来完成, 实现业务的分工
- 由代理角色来执行的公共业务, 可以直接通过各种代理角色进行灵活地扩展, 而不需要再去重构真实角色, 方便通过

**静态代理的缺点**

- 从结构层面上来看, 增加了对代理类的开发, 加大了工作量
- 每存在一个真实角色就会增加一个代理角色, 需要合理分工

**为了弥补静态代理的不足, 于是动态代理应运而生**

#### 2.动态代理

- 动态代理的各个角色和静态代理一样, 并没有发生改变
- 动态代理的代理类是动态生成的, 静态代理的代理类则需要提前编写好
- 动态代理分为两类: 一类是**基于接口**的动态代理, 一类是**基于类**的动态代理
- 基于接口的动态代理: jdk动态代理
- 基于类的动态代理: [cglib动态代理](https://github.com/cglib/cglib) 
- java字节码动态代理: [javassist动态代理](https://github.com/jboss-javassist/javassist) 能够动态编辑java的字节码文件来实现代理

这里以jdk动态代理为例

抽象对象

```java
// 抽象角色: 租赁的行为
public interface Rent {
    public void rent();
}
```

真实角色

```java
// 真实角色: 房东
public class Host implements Rent {
    @Override
    public void rent() {
        System.out.println("price: 100");
    }
}
```

代理角色

```java
public class ProxyInvocationHandler implements InvocationHandler {

    private Rent rent; // 基于接口实现代理

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    // 生成代理类
    public Object getProxy() {
        // 加载此类时生成Rent接口的代理类, 由于代理的是接口, 因此可以代理一类角色而不是单个角色
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), rent.getClass().getInterfaces(), this);
    }

    // 处理代理实例上的方法调用并返回结果
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 参数说明 proxy: 被代理的类  method: 用于调用代理类的处理方法实现代理
        // 核心机制是反射
        show();
        Object invoke = method.invoke(rent, args);
        pay();
        return invoke;
    }

    public void show() {
        System.out.println("show a big house");
    }

    public void pay() {
        System.out.println("pay a lot of money");
    }
}
```

客户

```java
    @Test
    public void test() {
        Host host = new Host();
        // 实例化代理实例的处理类, 即动态代理类
        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler();
        // 设置真实角色
        proxyInvocationHandler.setRent(host);
        // 动态生成代理类
        Rent proxy = (Rent) proxyInvocationHandler.getProxy();
        proxy.rent();
    }
```

**一个动态代理, 一般代理某一类业务, 可以代理多个类, 一定程度上弥补了静态代理工作量大的不足**

通用动态代理类模板

```java
public class ObjectProxyInvocationHandler implements InvocationHandler {

    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }
}
```

**动态代理的优势**

- 拥有静态代理的所有优势
- 实现了一个代理类通过接口代理多个类, 减少了工作量
- 动态代理的灵活性更强, 静态代理只能代理它所实现的接口, 而动态代理通过**InvocationHandler**的反射机制能够实现任意类型的接口

### AOP理论

#### 1.AOP的概念

AOP(Aspect Oriented Programming)意为: 面向切面编程, 通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP是OOP的延续, 是软件开发中的一个热点, 也是Spring框架中的一个重要内容, 是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离, 从而使得业务逻辑各部分之间的耦合度降低, 提高程序的可重用性, 同时提高了开发的效率

#### 2.AOP在Spring中的应用

**提供声明式事务, 允许用户自定义切面**

一些概念

- 横切关注点: 跨越应用程序多个模块的方法或功能。与业务逻辑无关, 但是需要开发者关注的部分, 就是横切关注点, 如日志、安全、缓存、事务等
- 切面(Aspect): 横切关注点被模块化的特殊对象, 即具体的实现类
- 通知(Advice): 切面所负责执行的工作, 即具体的方法
- 目标(Target): 被通知的对象, 即**抽象对象**
- 代理(Proxy): 向目标对象增加**通知**之后创建的**增强型代理类**
- 切入点(PointCut): 切面通知执行的时刻或地点
- 连接点(JointPoint): 与切入点匹配的执行点

在AOP中,  通过Advice定义横切逻辑, 能够在不改变源代码的情况下, 增加新的方法和功能。Advice有5种类型

|   通知类型   |        连接点        |                    实现接口                     |
| :----------: | :------------------: | :---------------------------------------------: |
|   前置通知   |        方法前        |   org.springframework.aop.MethodBeforeAdvice    |
|   后置通知   |        方法后        |  org.springframework.aop.AfterReturningAdvice   |
|   环绕通知   |       方法前后       |   org.aopalliance.intercept.MethodInterceptor   |
| 异常抛出通知 |     方法抛出异常     |      org.springframework.aop.ThrowsAdvice       |
|   引介通知   | 类中增加新的方法属性 | org.springframework.aop.IntroductionInterceptor |

#### 3.使用Spring实现AOP

**要使用AOP, 需要先导入依赖**

```xml
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
   <groupId>org.aspectj</groupId>
   <artifactId>aspectjweaver</artifactId>
   <version>1.9.4</version>
</dependency>
```

##### 方式一: 通过Spring API接口实现

编写业务接口与实现类

```java
public interface UserService {
    void add();
    void delete();
    void update();
    void query();
}
```

```java
public class UserServiceImpl implements UserService {

    @Override
    public void add() {
        System.out.println("add...");
    }

    @Override
    public void delete() {
        System.out.println("delete...");
    }

    @Override
    public void update() {
        System.out.println("update...");
    }

    @Override
    public void query() {
        System.out.println("query...");
    }
}
```

编写增强类: 前置增强和后置增强

```java
public class BeforeLog implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        // method: 目标对象要执行的方法
        // objects: 被调用方法的参数
        // o: 目标对象
        System.out.println(o.getClass().getName() + ": " + method.getName() + "...");
    }
}
```

```java
public class AfterLog implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        // o: 返回值
        // method: 被调用的方法
        // objects: 被调用方法的参数
        // o1: 目标对象
        System.out.println(o1.getClass().getName() + ": " + method.getName() + " return " + o);
    }
}
```

在applicationContext.xml中配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--注册Bean-->
    <bean id="userService" class="com.entropy.proxy.service.UserServiceImpl"/>
    <bean id="beforeLog" class="com.entropy.proxy.service.aop.BeforeLog"/>
    <bean id="afterLog" class="com.entropy.proxy.service.aop.AfterLog"/>

    <!--方式一: 使用原生Spring API接口-->
    <!--配置aop, 导入aop的约束-->
    <aop:config>
        <!--pointcut切入点, expression="execution(* 全限定类名.*(..))" 固定写法-->
        <aop:pointcut id="pointcut" expression="execution(* com.entropy.proxy.service.UserServiceImpl.*(..))"/>
        <!--前置通知加上后置通知相当于环绕通知-->
        <aop:advisor advice-ref="beforeLog" pointcut-ref="pointcut"/>
        <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
    </aop:config>
</beans>
```

**关于切入点表达式**

以execution (* org.example.service.impl..*. *(..))为例

1、execution(): 表达式主体

2、第一个*号: 表示返回类型, *号表示所有的类型

3、包名: 表示需要拦截的包名, 后面的两个句点表示当前包和当前包的所有子包下所有类的方法

4、第二个*号: 表示类名, *号表示所有的类

5、*(..): 最后这个星号表示方法名, *号表示所有的方法, 后面括弧里面表示方法的参数, 两个句点表示任何参数

测试

```java
    // 通过Spring API实现
    @Test
    public void test1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        // aop动态代理返回的是接口, 接口的代理已经在xml中配置实现
        UserService userService = applicationContext.getBean("userService", UserService.class);

        userService.delete();
    }
}
```

AOP就是将公共的业务 (日志, 安全等) 和领域业务结合起来, 当执行领域业务时, 将会把公共业务加进来, 实现公共业务的重复利用。领域业务更加纯粹, 方便开发者专注领域业务, 其本质就是动态代理

##### 方式二: 自定义AOP实现类

编写切入类

```java
public class MyPointcut {
    public void before() {
        System.out.println("自定义切入: 方法执行前");
    }

    public void after() {
        System.out.println("自定义切入: 方法执行后");
    }
}
```

在applicationContext.xml中配置

```xml
    <!--方式二: 自定义类-->
    <bean id="MyPointcut" class="com.entropy.proxy.service.aop.MyPointcut"/>
    <aop:config>
        <!--自定义切面, ref引用自定义类-->
        <aop:aspect ref="MyPointcut">
            <!--切入点-->
            <aop:pointcut id="point" expression="execution(* com.entropy.proxy.service.UserServiceImpl.*(..))"/>
            <!--通知-->
            <aop:before method="before" pointcut-ref="point"/>
            <aop:after method="after" pointcut-ref="point"/>
        </aop:aspect>
    </aop:config>
```

测试

```java
    @Test
    public void test2() {
        // 测试自定义类配置时, 需要先注释掉xml中的其它aop配置, 反之同理
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.add();
    }
```

##### 方式三: 通过注解实现

编写一个注解实现的增强类

```java
@Aspect // 通过@Aspect注解标记该类为切面
public class AnnotationPointcut {
    @Before("execution(* com.entropy.proxy.service.UserServiceImpl.*(..))")
    public void before() {
        System.out.println("注解式aop: 方法执行前");
    }

    @After("execution(* com.entropy.proxy.service.UserServiceImpl.*(..))")
    public void after() {
        System.out.println("注解式aop: 方法执行后");
    }

    @Around("execution(* com.entropy.proxy.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕前");
        // 获取签名
        System.out.println(joinPoint.getSignature());
        // 执行目标方法
        Object proceed = joinPoint.proceed();
        System.out.println("环绕后");
        System.out.println(proceed);
    }
}
```

在applicationContext.xml中配置

```xml
    <!--方式三: 注解式-->
    <bean id="annotationPointcut" class="com.entropy.proxy.service.aop.AnnotationPointcut"/>
    <!--开启注解支持-->
    <aop:aspectj-autoproxy/>
```

**关于aop:aspectj-autoproxy**

通过aop命名空间的<aop:aspectj-autoproxy />声明自动为spring容器中那些配置@aspectJ切面的bean创建代理, 织入切面

Spring在内部依旧采用AnnotationAwareAspectJAutoProxyCreator进行自动代理的创建工作, 但具体的细节已经通过<aop:aspectj-autoproxy />实现

<aop:aspectj-autoproxy />有一个proxy-target-class属性, 默认为false, 表示使用jdk动态代理织入增强。当配置为<aop:aspectj-autoproxy  poxy-target-class="true"/>时, 表示使用CGLib动态代理技术织入进行增强。不过即使proxy-target-class设置为false, 如果目标类没有声明接口, 则Spring将自动使用CGLib动态代理

测试

```java
    @Test
    public void test3() {
        // 测试注解配置时, 需要先注释掉xml中的其它aop配置, 反之同理
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.add();
    }
```

### 集成MyBatis

#### 1.导入相关依赖

```xml
    <dependencies>
        <!--junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!--mybatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.2</version>
        </dependency>
        <!--mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!--spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.2.5.RELEASE</version>
        </dependency>
        <!--aop-->
        <!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.4</version>
        </dependency>

        <!--spring-mybatis集成包-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>
        <!--lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.16</version>
        </dependency>        
    </dependencies>
```

配置maven静态资源打包问题

```xml
    <build>
        <resources>
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

#### 2.回顾MyBatis配置

实体类

```java
@Data
public class User {
    private Integer id;
    private String name;
    private String password;
}
```

核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--configuration：核心配置文件-->

    <!--给实体类起别名-->
    <typeAliases>
        <package name="com.entropy.pojo"/>
    </typeAliases>
    
    <environments default="development">
        <environment id="development">
            <!--transactionManager: 事务管理-->
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/2_mybatis?
                useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>

    <!--在MyBatis核心配置文件中注册mapper.xml-->
    <mappers>
        <mapper class="com.entropy.mapper.UserMapper"/>
    </mappers>
</configuration>
```

接口

```java
public interface UserMapper {
    public List<User> queryUser();
}
```

Mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.entropy.mapper.UserMapper">
    <select id="queryUser" resultType="user">
        select * from user;
    </select>
</mapper>
```

测试

```java
    @Test
    public void test1() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession(true);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> users = mapper.queryUser();
        for (User user : users) {
            System.out.println(user);
        }

        sqlSession.close();
    }
```

#### 3.MyBatis-Spring

MyBatis-Spring能够帮助你将MyBatis代码无缝地整合到Spring中

##### 1.基础准备

如果使用Maven作为构建工具, 仅需要在pom.xml中加入以下代码即可

```xml
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.2</version>
</dependency>
```

要在Spring中使用MyBatis, 需要在Spring应用上下文(applicationContext)中定义至少两个东西: 一个**SqlSessionFactory**和一个**数据映射器类**

在基础的MyBatis用法中, 是通过**SqlSessionFactoryBuilder**来创建**SqlSessionFactory**。在MyBatis-Spring中，可使用**SqlSessionFactoryBean**来创建 **SqlSessionFactory**

配置**SqlSessionFactoryBean**需要一个**DataSource**数据源, 这个数据源可以是任意的, 只要按照原本的规范配置即可, 另外还需要通过**configLocation属性**来指定**MyBatis的xml配置文件**路径。这里的xml主要是用于进行一些简单的配置, 如<settings\>配置日志或<typeAlises\>取别名。其它的任何环境配置、数据源、Myatis的事务管理器都不需要再配置, 由**SqlSessionFactoryBean**自动配置或者手动配置

**SqlSessionTemplate**是MyBatis的核心, 用于实现**SqlSession**, 且能够无缝替代已经使用的**基于MyBatis原生的SqlSession**, 但建议不要混合使用基于不同框架配置的SqlSession, 以免引起数据操作的问题。使用**SqlSessionFactoryBean**作为构造方法的参数来创建

##### 2.配置实现

spring-dao.xml最终配置如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--DataSource: 使用spring的数据源替换Mybatis的配置(druid, c3p0, 使用spring提供的JDBC)-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/2_mybatis?
        useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
    </bean>

    <!--配置SqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--导入MyBatis的配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/entropy/mapper/*.xml"/>
    </bean>

    <!--配置SqlSessionTemplate-->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>
</beans>
```

编写Mapper实现类

```java
public class UserMapperImpl implements UserMapper {

//    private SqlSession sqlSession; // 基于MyBatis的SqlSession, 使用SqlSessionTemplate替代
    private SqlSessionTemplate sqlSessionTemplate;

    // 编写set方法用于Spring注入
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public List<User> queryUser() {
        UserMapper mapper = sqlSessionTemplate.getMapper(UserMapper.class);
        return mapper.queryUser();
    }
}
```

在applicationContext.xml中注册实现类

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <import resource="spring-dao.xml"/>

    <bean id="userMapper" class="com.entropy.mapper.UserMapperImpl">
        <property name="sqlSessionTemplate" ref="sqlSession"/>
    </bean>
</beans>
```

测试

```java
    @Test
    public void test2() {
        // 测试时需要注释掉原来mybatis-config的配置, 否则会冲突
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        for (User user : userMapper.queryUser()) {
            System.out.println(user);
        }
    }
```

至此, Spring完成了对MyBatis的整合

##### 3.优化版配置

需要spring-mybatis整合包版本1.2.3以上

直接利用**getSqlSession**获取, 然后直接注入**SqlSessionFactory**, 相对于原来省去了对**SqlSessionTemplate**的配置, 而且对事务的支持更加强大

新创建一个实现类, 需要继承**SqlSessionDaoSupport**

```java
public class UserMapperImplPro extends SqlSessionDaoSupport implements UserMapper {
    @Override
    public List<User> queryUser() {
        SqlSession sqlSession = getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.queryUser();
    }
}
```

### 声明式事务

#### 1.回顾事务

- 把一组相关联的逻辑操作(动作)视为一个业务, 要么全部成功, 要么全部失败
- 事务在项目开发过程中非常重要, 涉及到数据一致性的问题, 不允许出现差错
- 事务管理是企业级应用程序开发中必备技术, 用来确保数据完整性和一致性

**事务四个属性(ACID原则)**

- 原子性（atomicity）

  事务是原子性操作, 由一系列动作组成, 事务的原子性确保这些动作只能同时成功或同时失败

- 一致性（consistency）

  一旦所有事务完成, 事务就要进行提交。数据和资源保持一致性

- 隔离性（isolation）

  实际开发过程中可能出现多个事务同时处理相同数据的情况, 因此每个事务都会与其它事务隔离, 防止数据损坏

- 持久性（durability）

  事务一旦完成, 事务的结果就被写入持久化存储器中

#### 2.模拟缺乏事务管理带来的隐患

修改接口, 增加数据操作的方法

```java
public interface UserMapper {
    public List<User> queryUser();

    int addUser(User user);

    int deleteUser(int id);
}
```

编写mapper.xml文件, 故意编写错误的语句

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.entropy.mapper.UserMapper">
    <select id="queryUser" resultType="user">
        select * from user
    </select>
    
    <insert id="addUser" parameterType="user">
        insert into user (id,name,password) values (#{id},#{name},#{password})
    </insert>

    <!--故意编写错误的delete语句测试事务管理-->
    <delete id="deleteUser" parameterType="int">
        deletes from user where id = #{id}
    </delete>
</mapper>
```

修改接口实现类

```java
public class UserMapperImplPro extends SqlSessionDaoSupport implements UserMapper {
    @Override
    public List<User> queryUser() {
        SqlSession sqlSession = getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        int id = 6;
        User user = new User(id, "linTa", "7787890");

        int i = userMapper.addUser(user);
        System.out.println("增加用户: " + i);

        System.out.println("异常的删除操作");
        int deleteUser = userMapper.deleteUser(id);

        return userMapper.queryUser();
    }

    @Override
    public int addUser(User user) {
        return getSqlSession().getMapper(UserMapper.class).addUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return getSqlSession().getMapper(UserMapper.class).deleteUser(id);
    }
}
```

测试

```java
    // 事务管理异常测试
    @Test
    public void test4() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapperPro", UserMapper.class);
        List<User> users = userMapper.queryUser(); // 这里的方法进行了改造, 不仅仅是查询
        for (User user : users) {
            System.out.println(user);
        }
    }
```

最终结果是**增加用户成功**了, 但是**删除用户失败**了, 产生了脏数据, 埋下隐患。通过事务管理就能使这一系列动作同时成功或者同时失败, 而不会出现**部分成功, 部分失败的危险操作**。传统的方式需要手动管理事务, 但Spring提供了便于配置的事务管理

#### 3.Spring实现事务管理

Spring在不同的事务管理API之上定义了一个抽象层, 使得开发人员不必了解底层的事务管理API就可以使用Spring的事务管理机制。Spring支持编程式事务管理和声明式事务管理

**编程式事务管理**

将事务管理代码嵌到业务方法中来控制事务的提交和回滚, 但必须在每个事务操作业务逻辑中包含额外的事务管理代码, 不是很方便

**声明式事务管理**

将事务管理代码从业务方法中分离出来, 以声明的方式来实现事务管理。将事务管理作为横切关注点, 通过aop方法模块化

在Spring中通过Spring AOP框架支持声明式事务管理, 使用Spring管理事务, 需要在xml中导入头文件约束: tx

**事务管理器**

事务管理器是Spring的核心事务管理对象, 封装了一组独立于技术的方法。无论使用哪种Spring的事务管理策略(编程式或声明式)都需要用到事务管理器

1.具体配置如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd 
        http://www.springframework.org/schema/tx 
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--DataSource: 使用spring的数据源替换Mybatis的配置(druid, c3p0, 使用spring提供的JDBC)-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/2_mybatis?
        useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
    </bean>

    <!--配置SqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--导入MyBatis的配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/entropy/mapper/*.xml"/>
    </bean>

    <!--配置SqlSessionTemplate-->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>

    <!--配置声明式事务-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配合AOP实现事务的织入-->

    <!--配置事务通知-->
    <tx:advice id="transactionInterceptor" transaction-manager="transactionManager">
        <!--配置事务的传播特性, 默认propagation="REQUIRED"-->
        <tx:attributes>
            <tx:method name="add" propagation="REQUIRED"/>
            <tx:method name="delete" propagation="REQUIRED"/>
            <tx:method name="update" propagation="REQUIRED"/>
            <tx:method name="query" read-only="true"/>

            <!--或者-->
          <!--  <tx:method name="*" propagation="REQUIRED"/>-->
        </tx:attributes>
    </tx:advice>
    <!--配置AOP-->
    <aop:config>
        <!--切入点-->
        <aop:pointcut id="txPointcut" expression="execution(* com.entropy.mapper.*.*(..))"/>
        <!--切入事务通知-->
        <aop:advisor advice-ref="transactionInterceptor" pointcut-ref="txPointcut"/>
    </aop:config>
</beans>
```

**关于事务传播特性**

事务传播行为就是多个事务方法相互调用时, 事务如何在这些方法间传播。Spring支持7种事务传播行为, 通过propagation属性配置

**REQUIRED**: 支持当前事务, 如果当前没有事务, 就新建一个事务.如果已存在一个事务, 就加入到这个事务中, 这是最常见的选择。

**SUPPORTS**: 支持当前事务, 如果没有当前事务, 就以非事务方法执行。

**MANDATORY**: 支持当前事务, 如果没有当前事务, 就抛出异常。

**REQUIRES_NEW**: 新建事务, 如果当前存在事务, 把当前事务挂起。

**NOT_SUPPORTED**: 以非事务方式执行操作, 如果当前存在事务, 就把当前事务挂起。

**NEVER**: 以非事务方式执行操作, 如果当前存在事务, 则抛出异常。

**NESTED**: 支持当前事务, 如果当前存在事务, 则在嵌套事务内执行。如果当前没有事务, 就新建一个事务。

Spring默认的事务传播行为是**REQUIRED**, 它适合于绝大多数的情况。

2.重新测试

```java
    // 事务管理异常测试
    @Test
    public void test4() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapperPro", UserMapper.class);
        List<User> users = userMapper.queryUser(); // 这里的方法进行了改造, 不仅仅是查询
        for (User user : users) {
            System.out.println(user);
        }
    }
```

重新编写正确的sql语句, 重新测试。最终结果要么都失败(用户不增加, 也不删除), 要么都成功(用户增加且删除)

#### 4.事务的意义

- 为了避免数据一致性破环的问题

- 如果不用Spring配置, 则需要在代码中手动配置事务
- 事务关系到数据的一致性和完整性, 在开发中具有非常重要的地位, 尤其是在web领域大量的数据操作下确保数据本身的安全