### MVC框架

#### 1.回顾MVC的概念

- MVC是模型(Model)、视图(View)、控制器(Controller)的简写, 是一种软件设计规范
- 通过分离**业务逻辑**、**数据**、**显示**的方法来组织代码
- MVC的主要作用是**降低视图与业务逻辑间的双向耦合**
- MVC不是一种**设计模式**, 而是一种**架构模式**

Model（模型）：数据模型, 提供要展示的数据, 因此包含数据和行为, 可以认为是领域模型或JavaBean组件(包含数据和行为), 不过现在一般都分离为**Value Object(数据Dao)**和**服务层(行为Service)**, 也就是模型提供了模型数据查询和模型数据的状态更新等功能, 包括数据和业务

View（视图）：负责进行模型的展示, 一般就是指能够直观看到的用户交互界面

Controller（控制器）：接收用户请求, 委托给模型进行处理(状态改变), 处理完毕后把返回的模型数据返回给视图, 由视图负责展示

**最典型的MVC就是JPS+Servlet+JavaBean的模式（这里的MVC不同于现在的MVC）**

#### 2.Model一代

- 在web早期的开发中, 通常采用的都是Model1
- Model1主要分为两层, **视图层和模型层**
- 优点: 架构简单, 比较适合小型项目开发
- 缺点: 大量代码集中于JSP, JSP职责过重, 不便于维护

#### 3.Model二代

- Model2把一个项目分成三部分, 分别是**视图、控制、模型**

- 用户发请求

  **Servlet**接收请求的数据, 并调用对应的业务逻辑方法

  业务处理完成, 返回更新后的数据给Servlet

  Servlet返回给JSP, 由JSP来渲染页面

  响应给前端更新后的页面

- 功能划分

  **Controller：控制器**

   1、取得表单数据

   2、调用业务逻辑

   3、转向指定的页面

  **Model：模型**

   1、业务逻辑

   2、保存数据的状态

  **View模型**

   1、显示页面

- Model2不仅提高了代码的复用率与项目的扩展性, 且大大降低了项目的维护成本
  Model1的实现比较简单, 适用于快速开发小规模项目, Model1中jsp页面兼备View和Controller两种角色的功能, 将控制逻辑和表现逻辑混杂在一起, 从而导致代码的重用性非常低, 增加了应用的扩展性和维护的难度, 而Model2的使用消除了Model1的缺点

#### 4.回顾Servlet

1.创建maven项目作为父工程, 添加Springframework相关依赖

```xml
<!--依赖-->
    <dependencies>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.12</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-webmvc</artifactId>
                <version>5.2.12.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>javax.servlet</groupId>
                <artifactId>servlet-api</artifactId>
                <version>2.5</version>
            </dependency>
            <dependency>
                <groupId>javax.servlet.jsp</groupId>
                <artifactId>jsp-api</artifactId>
                <version>2.2</version>
            </dependency>
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.22</version>
            </dependency>
            <dependency>
                <groupId>javax.servlet.jsp.jstl</groupId>
                <artifactId>jstl-api</artifactId>
                <version>1.2</version>
            </dependency>
    </dependencies>

    <build>
       <resources>
           <resource>
               <directory>src/main/java</directory>
               <includes>
                   <include>**/*.properties</include>
                   <include>**/*.xml</include>
               </includes>
               <filtering>false</filtering>
           </resource>
           <resource>
               <directory>src/main/resources</directory>
               <includes>
                   <include>**/*.properties</include>
                   <include>**/*.xml</include>
               </includes>
               <filtering>false</filtering>
           </resource>
       </resources>
    </build>
```

2.创建module, 添加webapp支持(右击module进行添加, 之后会自动创建web.xml和index.jsp等文件)

3.导入jsp和servlet的依赖, 这里已经在父级pom.xml中导入, 不需要再重复导入

4.编写Servlet类, 用于处理用户请求

```java
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.获取前端参数
        String method = req.getParameter("method");
        if (method.equals("add")) {
            req.getSession().setAttribute("msg", "执行" + method + "方法");
        }
        if (method.equals("delete")) {
            req.getSession().setAttribute("msg", "执行" + method + "方法");
        }
        // 2.调用业务层
        // 3.视图转发或重定向
        req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
```

5.编写hello.jsp文件, 在WEB-INF目录下新建jsp文件夹, 在jsp文件夹下创建

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    ${msg}
</body>
</html>
```

6.在web.xml中注册Servlet

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <servlet>
        <servlet-name>hello</servlet-name>
        <servlet-class>com.entropy.servlet.HelloServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>hello</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>

    <!--超时时间-->
   <!-- <session-config>
        <session-timeout>15</session-timeout>
    </session-config>-->
    <!--初始页面-->
    <!--<welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>-->
</web-app>
```

7.在web目录下新建form.jsp用于提交表单数据

```xml
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <form action="hello" method="get">
    <input type="text" name="method">
    <input type="submit">
  </form>
</body>
</html>
```

8.配置tomcat并运行, 在form.jsp中输入数据进行测试

**MVC框架的作用**

1. 将url映射到java类或java类的方法
2. 封装用户提交的数据
3. 处理请求, 并调用相关的业务处理、封装响应数据
4. 将响应的数据进行渲染, 如jsp、html等表示层数据

常见的服务器端MVC框架有: Struts、Spring MVC、ASP.NET MVC、Zend Framework、JSF; 常见前端MVC框架: vue、angularjs、react、backbone; 由MVC演化出了另外一些模式, 如MVP、MVVM 等等....

### SpringMVC介绍

#### 1.概述

Spring MVC是Spring Framework的一部分, 是基于Java实现MVC的轻量级Web框架 [Web on Servlet Stack (spring.io)](https://docs.spring.io/spring-framework/docs/5.2.0.RELEASE/spring-framework-reference/web.html#spring-web) **5.2.0.RELEASE官方发行版文档**

#### 2.特点

1. 轻量级, 简单易学
2. 高效, 基于请求响应的MVC框架
3. 与Spring兼容性好, 无缝结合
4. 约定优于配置
5. 功能强大: 数据验证、格式化、本地化、主题、Restful架构(REST风格)、异常处理、本地化、国际化、类型转换、拦截器等
6. 简洁灵活

#### 3.中心控制器

**简要说明**

Spring的web框架围绕**DispatcherServlet** [ 调度Servlet ] 设计

**DispatcherServlet**的作用是将请求分发到不同的处理器。从Spring 2.5开始, 使用Java 5或者以上版本的用户可以采用基于注解的形式进行开发, 十分简洁

Spring MVC框架像许多其他MVC框架一样, **以请求为驱动, 围绕一个中心Servlet分派请求及提供其他功能, DispatcherServlet是一个实际的Servlet (它继承自HttpServlet 基类)**

**原理**

当发起请求时被**前置的控制器(中心控制器)拦截到请求**, 根据请求参数**生成代理请求**, 找到请求对应的**实际控制器处理请求**, **创建数据模型**, **访问数据库**, 将**模型数据返回给实际控制器**, 再将**模型与视图一并传给前置控制器**,前置控制器**使用模型与视图渲染出视图结果**, 将结果返回给前置控制器, 再将结果响应给请求者

**简要分析执行流程**

1.**DispatcherServlet表示前置控制器**, 是整个SpringMVC的控制中心。用户发出请求, DispatcherServlet接收请求并拦截请求

假设请求的URL为http://localhost:8080/SpringMVC/hello

如上url拆分成三部分

- http://localhost:8080(服务器域名)


- SpringMVC(部署在服务器上的web站点)


- hello(表示控制器)

则该URL可表示为: 请求位于**服务器localhost:8080**上的**SpringMVC站点**的**hello控制器**

2.**HandlerMapping为处理器映射器**。DispatcherServlet调用HandlerMapping, HandlerMapping根据请求url查找Handler

3.**HandlerExecution表示具体的Handler**, 其主要作用是根据url查找控制器

4.HandlerExecution将解析后的信息传递给DispatcherServlet, 如解析控制器映射等

5.**HandlerAdapter表示处理器适配器**, 表示以特定的规则去执行Handler

6.**Handler由具体的Controller执行**

7.Controller将具体的执行信息返回给**HandlerAdapter**, 如ModelAndView

8.HandlerAdapter将视图逻辑名或模型传递给DispatcherServlet

9.DispatcherServlet调用视图解析器(ViewResolver)来解析HandlerAdapter传递的逻辑视图名

10.视图解析器将解析的逻辑视图名传给DispatcherServlet

11.DispatcherServlet根据视图解析器解析的视图结果, 调用具体的视图

12.最终视图呈现给用户

### SpringMVC的简单使用

#### 1.基于xml配置

1.创建Module, 添加web支持

2.导入SpringMVC依赖

3.配置web.xml, 注册**DispatcherServlet**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联SpringMVC配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别 1 服务器启动时该程序就启动-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    
    <!--请求拦截配置-->
    <!-- / 匹配所有请求(不包括jsp)
        /* 匹配所有请求(包括jsp) -->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

**/ 和 /* 的区别**

< url-pattern > / </ url-pattern > 不会匹配到.jsp, 只针对我们编写的请求, 即.jsp不会进入spring的DispatcherServlet类

< url-pattern > /* </ url-pattern > 会匹配 *.jsp, 会出现返回jsp视图时**再次进入spring的DispatcherServlet类**, 导致找不到对应的controller所以会出现**404错误**

4.编写SpringMVC的配置文件, 官方指定格式: [servlet-name]-servlet.xml, 如这里的servlet-name标签下设置的名字就是springmvc, 则该配置文件的全名为springmvc-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--添加处理器映射器HandlerMapping-->
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>

    <!--添加处理器适配器HandlerAdapter-->
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>

    <!--添加视图解析器ViewResolver-->
    <!--处理DispatcherServlet接收的ModelAndView
        1.获取ModelAndView数据
        2.解析ModelAndView视图名称
        3.拼接视图名称, 找到对应的视图, 如/WEB-INF/jsp/hello.jsp
        4.将数据渲染到视图上
        -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="InternalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--将自己编写的Controller类(Handler)注册到Spring中-->
    <bean id="/hello" class="com.entropy.controller.HelloController"/>
</beans>
```

5.编写业务操作Controller类, 需要实现Controller接口或使用注解。Controller一般都会返回ModelAndView的数据

```java
public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        // 创建ModelAndView模型和视图
        ModelAndView modelAndView = new ModelAndView();
        // 封装对象到ModelAndView中
        modelAndView.addObject("msg", "Hello SpringMVC");
        // 封装要跳转的视图, 指定视图名称
        modelAndView.setViewName("hello"); // /WEB-INF/jsp/hello.jsp
        return modelAndView;
    }
}
```

6.将Controller类注册到Spring中

```xml
<!--将自己编写的Controller类(Handler)注册到Spring中-->
<bean id="/hello" class="com.entropy.controller.HelloController"/>
```

7.在WEB-INF/jsp目录下编写hello.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    ${msg}
</body>
</html>
```

8.配置tomcat运行测试

**如果遇到404的问题, 请尝试在IDEA的Project Structure->Artifacts中选中已经配置好的虚拟路径, 在WEB-INF下新建lib目录(名称固定是lib, 不能更改), 然后手动在lib目录下添加jar包依赖, apply之后再重启tomcat测试**

实际开发中基于xml的配置相对繁琐, 因此后面将采用**注解的方式**来编写

#### 2.基于注解配置

**1.新建Module, 添加web支持**

**2.配置pom.xml**

如果父级pom.xml已经配置过了, 这里就不需要重新配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>4_SpringMVC</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>springmvc_01_servlet</module>
        <module>springmvc_02_hello</module>
    </modules>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <!--依赖-->
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp.jstl</groupId>
            <artifactId>jstl-api</artifactId>
            <version>1.2</version>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
</project>
```

**3.配置web.xml**

注意映射路径为 / 而不是 /*

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联SpringMVC配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别 1 服务器启动时该程序就启动-->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!--请求拦截配置-->
    <!-- / 匹配所有请求(不包括jsp)
        /* 匹配所有请求(包括jsp) -->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

**4.配置springmvc-servlet.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--自动扫描包, 让指定包下的注解生效, 由IOC容器统一管理-->
    <context:component-scan base-package="com.entropy.controller"/>

    <!--配置SpringMVC不处理静态资源  .css   .js   .html-->
    <mvc:default-servlet-handler/>

    <!--通过注解自动生成映射器和适配器-->
    <!--配置mvc注解驱动支持
      在spring中一般采用@RequestMapping注解来完成映射关系
      要想使@RequestMapping注解生效
      必须向上下文中注册DefaultAnnotationHandlerMapping
      和一个AnnotationMethodHandlerAdapter实例
      这两个实例分别在类级别和方法级别处理
      而annotation-driven配置帮助我们自动完成上述两个实例的注入-->
    <mvc:annotation-driven/>


<!--    &lt;!&ndash;添加处理器映射HandlerMapping&ndash;&gt;
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>

    &lt;!&ndash;添加处理适配器HandlerAdapter&ndash;&gt;
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>-->

    <!--添加视图解析器ViewResolver-->
    <!--处理DispatcherServlet接收的ModelAndView
        1.获取ModelAndView数据
        2.解析ModelAndView视图名称
        3.拼接视图名称, 找到对应的视图, 如/WEB-INF/jsp/hello.jsp
        4.将数据渲染到视图上
        -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="InternalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--将自己编写的Controller类(Handler)注册到Spring中-->
    <bean id="/hello" class="com.entropy.controller.HelloController"/>
</beans>
```

在视图解析器中所有的视图都存放在/WEB-INF/目录下, 这样可以保证视图安全, 因为这个目录下的文件, 客户端不能直接访问

**5.创建新的Controller类**

```java
@Controller // 注册到Spring中
@RequestMapping("/hello")
// 实际地址: 项目名/hello/h1(下面有@RequestMapping)
// 实际地址: 项目名/hello(下面没有@RequestMapping)
public class Hello2Controller {
    // 实际地址: 项目名/hello/h1(上面有@RequestMapping)
    // 实际地址: 项目名/h1(上面没有@RequestMapping)
    @RequestMapping("/h1")
    public String test(Model model) {
        // 向模型中添加属性msg与值, 可以在JSP页面中取出并渲染
        model.addAttribute("msg", "annotation springmvc");
        return "hello"; // 返回给视图解析器, 最终得到/WEB-INF/jsp/hello.jsp
    }
}
```

- @Controller是为了让Spring IOC容器初始化时自动扫描到该类
- @RequestMapping是映射请求路径, 这里类与方法上都有映射所以访问的真实路径是/hello/h1
- 方法中声明Model类型的参数是为了**把Action中的数据带到视图中**
- 方法返回的结果是视图的名称hello, 加上配置文件中的前后缀变成/WEB-INF/jsp/hello.jsp, 正好**与项目文件路径对应匹配**

**6.创建视图层**

在WEB-INF/jsp目录中创建hello.jsp, 通过EL表达式获取视图从Controller携带的信息

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    ${msg}
</body>
</html>
```

**7.配置tomcat运行测试**

#### 3.总结

**基本步骤**

1. 新建一个web项目
2. 导入相关jar包
3. 编写web.xml, 注册DispatcherServlet
4. 编写springmvc配置文件
5. 创建对应的控制类controller
6. 完善前端视图和controller之间的对应
7. 测试运行调试

SpringMVC三大组件: **处理器映射器、处理器适配器、视图解析器**

除了视图解析器需要手动配置, 处理器映射器、处理器适配器都只需要开启注解支持即可

### Controller和Restful

#### 控制器Controller

- 控制器负责提供访问应用程序的行为, 通常通过接口定义或注解定义两种方法实现
- 控制器负责解析用户的请求并将其转换为一个**模型**
- 在Spring MVC中一个控制器类可以包含多个方法
- 在Spring MVC中, 对于Controller的配置方式有很多种

#### 实现Controller接口

查看Controller接口源码, 里面只有一个**handleRequest**方法, 用于处理请求并返回一个**模型与视图**对象

1.创建Controller类

```java
public class TestController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "test first");
        modelAndView.setViewName("test");
        return modelAndView;
    }
}
```

2.在Spring配置文件中注册

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--添加视图解析器ViewResolver-->
    <!--处理DispatcherServlet接收的ModelAndView
        1.获取ModelAndView数据
        2.解析ModelAndView视图名称
        3.拼接视图名称, 找到对应的视图, 如/WEB-INF/jsp/hello.jsp
        4.将数据渲染到视图上
        -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="InternalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <bean id="/t1" class="com.entropy.controller.TestController"/>
</beans>
```

这里处理器映器和处理器适配器未配置, 则会使用SpringMVC默认的配置

3.编写test.jsp, 存放在/WEB-INF/jsp目录下, 对应视图解析器的路径

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  ${msg}
</body>
</html>
```

4.配置tomcat运行测试

---

通过实现Controller接口来定义控制器是比较旧的方法, 它的局限性是一个控制器中默认只有一个方法, 多个方法则需要手动去定义多个Controller, 定义的方式比较麻烦, **实际开发中一般基于注解去实现**

#### 使用@Controller注解

- @Controller注解用于声明Spring类的实例是一个控制器（在前面的Spring文章中还提到了另外3个注解）

- Spring可以使用扫描机制来找到应用程序中所有基于注解的控制器类, 为了保证Spring能找到控制器, 需要在配置文件中声明组件扫描

  ```xml
  <context:component-scan base-package="com.entropy.controller"/>
  ```

- 编写一个基于注解实现的Controller类

  ```java
  @Controller
  public class Test2Controller {
      // 实际地址: 项目名/t2
      @RequestMapping("/t2")
      public String test(Model model) {
          // 向模型中添加属性msg与值, 可以在JSP页面中取出并渲染
          model.addAttribute("msg", "test second");
          return "test"; // 返回给视图解析器, 最终得到/WEB-INF/jsp/test.jsp
      }
  }
  ```

  **可以发现, 上面一个基于接口一个基于注解实现的两个Controller的请求都指向了同一个视图test.jsp, 但页面的展示结果是不一样, 也就是说视图被复用了, 控制器与视图之间是低耦合关系**

**@RequestMapping注解**

@RequestMapping注解用于**映射url**到**控制器类**或一个特定的处理程序**方法**, 可用于类或方法上。用于类上, 表示类中的所有响应请求的方法都是以该地址作为父路径

#### Restful(REST风格)

**概念**

Restful就是一个资源定位及资源操作的风格, 不是标准也不是协议, 只是一种风格或架构。基于这个风格或架构设计的软件可以更简洁, 更有层次, 更易于实现缓存等机制

**功能**

资源: 互联网所有的事物都可抽象为资源

资源操作: 使用POST(新增)、DELETE(删除)、PUT(修改)、GET(查询)等不同方法对资源进行操作

**传统方式操作资源**: 通过不同的参数来实现不同的效果, 方法单一

http://127.0.0.1/item/queryItem.action?id=1 查询, GET

http://127.0.0.1/item/saveItem.action 新增, POST

http://127.0.0.1/item/updateItem.action 更新, POST

http://127.0.0.1/item/deleteItem.action?id=1 删除, GET或POST

**使用RESTful操作资源**: 可以通过不同的请求方式来实现不同的效果, 请求地址一样, 但是功能可以不同

http://127.0.0.1/item/1 查询, GET

http://127.0.0.1/item 新增, POST

http://127.0.0.1/item 更新, PUT

http://127.0.0.1/item/1 删除, DELETE

**对比测试**

编写一个Controller类, 其中一个方法使用传统风格, 一个方法使用REST风格

```java
@Controller
public class RestfulController {
    // 传统风格
    @RequestMapping("/add") // 访问路径: 项目名/add?a=1&b=2, 参数名需对应匹配
    public String add(int a, int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", res);
        return "test";
    }

    // REST风格
    @RequestMapping("/add/{p1}/{p2}") // 访问路径: 项目名/add/1/2
    public String restfulAdd(@PathVariable int p1, @PathVariable int p2, Model model) {
        int res = p1 + p2;
        //Spring MVC会自动实例化一个Model对象用于向视图中传值
        model.addAttribute("msg", "restful " + res);
        //返回视图位置
        return "test";
    }
}
```

**@PathVariable注解用于映射@RequestMapping中请求路径里的占位符(被{}包围的就是占位符), 将方法参数的值绑定到对应的占位符上, 否则方法参数会丢失数据**

通过对应路径进行访问测试

项目名/add?a=1&b=2

项目名/add/1/2

**Restful的优势**

- Restful中引入了**路径变量**的概念, 使得路径更加简洁, 不需要像传统方式那样将方法参数名写在路径上
- 获取参数值更加方便(框架会自动进行类型转换)
- 通过路径变量的类型可以**约束访问参数**, 如果类型不一样则访问不到对应的请求方法, 如果这里访问是的路径是/add/1/a, 则路径与方法不匹配, 而不会出现参数转换失败的情况

**使用method属性指定请求类型**

用于约束**请求的类型**, 可以缩小请求范围。指定请求的类型如GET, POST,  HEAD, OPTIONS, PUT, PATCH, DELETE, TRACE等

在上面Controller类的基础上增加方法

```java
@Controller
public class RestfulController {
    // 传统风格
    @RequestMapping("/add") // 访问路径: 项目名/add?a=1&b=2, 参数名需对应匹配
    public String add(int a, int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", res);
        return "test";
    }

    // REST风格
    @RequestMapping("/add/{p1}/{p2}") // 访问路径: 项目名/add/1/2
    public String restfulAdd(@PathVariable int p1, @PathVariable int p2, Model model) {
        int res = p1 + p2;
        //Spring MVC会自动实例化一个Model对象用于向视图中传值
        model.addAttribute("msg", "restful " + res);
        //返回视图位置
        return "test";
    }

    // 直接在浏览器地址栏上访问会出现405错误, 因为浏览器地址栏默认为GET请求
    @RequestMapping(value = "/post", method = RequestMethod.POST) // 只匹配POST请求
    public String post(Model model) {
        model.addAttribute("msg", "this is a post request");
        return "test";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET) // 只匹配GET请求
    public String get(Model model) {
        model.addAttribute("msg", "this is a get request");
        return "test";
    }
}
```

**一些衍生注解**

基于@RquestMapping注解衍生出了一些注解或称为组合注解, 因为这些注解相当于**已经指定请求类型的@RquestMapping注解**, 有@GetMapping、@PostMapping、@PutMapping、@DeleteMapping、@PatchMapping

其中@GetMapping就相当于@RequestMapping(method = RequestMethod.GET) 

### 跳转方式与数据处理

#### 跳转方式

##### 1.ModelAndView

设置ModelAndView对象, 根据View的名称 , 通过视图解析器跳转到指定的页面, 页面的路径: 视图解析器前缀+视图名称+视图解析器后缀

在springmvc-servlet.xml中配置视图解析器

```xml
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="InternalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
```

对应的Controller类, 实现了Controller接口

```java
public class TestController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "test first");
        modelAndView.setViewName("test");
        return modelAndView;
    }
}
```

##### 2.ServletAPI

通过使用ServletAPI, 则不需要借助视图解析器

**通过HttpServletResponse实现输出、重定向、转发**

```java
@Controller
public class ResponseController {

    @RequestMapping("/resp/r1")
    public void r1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("servlet api");
    }

    @RequestMapping("/resp/r2")
    public void r2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 重定向
        // 这里不能重定向WEB-INF目录下的文件
        response.sendRedirect("/springmvc_02_hello_war_exploded/index.jsp"); // tomcat配置的项目名/视图文件拓展名
    }

    @RequestMapping("/resp/r3")
    public void r3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 转发
        request.setAttribute("msg", "result/r3");
        request.getRequestDispatcher("/WEB-INF/jsp/test.jsp").forward(request, response);
    }
}
```

##### 3.SpringMVC

通过SpringMVC框架实现转发和重定向

**1.无视图解析器配置**

**测试前需要注释掉springmvc-servlet.xml中的视图解析器配置**

编写Controller类

```java
@Controller
public class mvcController {

    // 测试以下方法需要注释掉springmvc-servlet.xml中的视图解析器配置, 否则会出现404错误
    @RequestMapping("/mvc/m1")
    public String m1(Model model) {
        model.addAttribute("msg", "mvc/m1");
        return "/WEB-INF/jsp/test.jsp"; // 转发
    }

    @RequestMapping("/mvc/m2")
    public String m2(Model model) {
        model.addAttribute("msg", "mvc/m2");
        return "forward:/WEB-INF/jsp/test.jsp"; // 转发, 另一种写法
    }

    @RequestMapping("/mvc/m3")
    public String m3(Model model) {
        model.addAttribute("msg", "mvc/m3");
        return "redirect:/index.jsp"; // 重定向
    }
}
```

**2.有视图解析器配置**

**特别地, 重定向本身就不需要借助视图解析器, 需要额外注意重定向的路径**

重定向不一定是指向某个文件, 也可以指向某个请求，转发同理

```java
@Controller
public class mvc2Controller {

    @RequestMapping("/mvc2/m1")
    public String m1(Model model) {
        model.addAttribute("msg", "mvc2/m1");
        return "test";
    }

    @RequestMapping("/mvc2/m2")
    public String m2(Model model) {
        model.addAttribute("msg", "mvc2/m2");
        return "redirect:/index.jsp";
    }

    @RequestMapping("mvc2/m3")
    public String m3() {
        return "redirect:/mvc2/m1"; // 这里的重定向指向了另外一个请求
    }
}
```

#### 数据处理

##### 处理请求数据

1.请求参数名称与方法参数名称一致 http://localhost:8080/springmvc_02_hello_war_exploded/data/d1?name=abc

```java
    @RequestMapping("/data/d1")
    public String d1(String name) {
        System.out.println(name);
        return "test";
    }
```

2.请求参数名称与方法参数名称不一致 http://localhost:8080/springmvc_02_hello_war_exploded/data/d2?username=123

```java
    @RequestMapping("data/d2")
    public String d2(@RequestParam("username") String name) {
        System.out.println(name);
        return "test";
    }
```

通过@RequestParam注解, 将请求参数username的值赋给name，适用于单个请求参数。

每个请求参数如果自定义名称，则每个请求参数前都需要加上@RequestParam注解，因此不推荐在请求参数较多的情况下使用。

@RequestParam注解不能用于接收对象类型的请求参数。

3.请求的数据是对象类型

实体类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer age;
}
```

按照对象内的变量名填写请求参数即可  http://localhost:8080/springmvc_02_hello_war_exploded/data/d3?id=1&name=re&age=12

Controller类

```java
    @RequestMapping("data/d3")
    public String d3(User user) {
        System.out.println(user);
        return "test";
    }
```

对象数据类型的请求参数名称必须和对象内的成员变量名一致, 否则为null

补充：

1. 关于嵌套对象的请求参数

- 如果请求参数是一个对象类型，且对象内部包含对象类型的成员变量，即嵌套对象。那么请求参数的名称应该是“对象名.成员变量名”的形式，例如“user.address.city”，这样SpringMVC才能够将请求参数封装到对象中。

2. 关于@ModelAttribute注解

- @ModelAttribute注解用于接收请求参数(主要是对象类型的参数)，并将请求参数封装到对象中，然后将对象传递给处理器方法。

- @ModelAttribute注解可以用于处理器方法的形参上，也可以用于处理器方法的返回值上。用于处理器方法的形参上时，表示从模型中获取指定的数据传递给形参；用于处理器方法的返回值上时，表示将处理器方法的返回值传递给模型。

- 写在方法上的@ModelAttribute注解可以自动在其他请求方法之前执行，用于初始化数据。在其他请求参数上再使用对应的@ModelAttribute注解，可以在前端没有传递对应的请求参数时，使用@ModelAttribute注解指定的默认值。

- @ModelAttribute注解可以缺省，缺省时默认使用的是当前类名首字母小写的字符串作为模型数据的key。

##### 数据显示到前端

**ModelAndView**

实现Controller接口, 封装数据, 返回ModelAndView即可, 这里不再重复说明

**ModelMap**

```java
    @RequestMapping("data/d4")
    public String d4(@RequestParam("username") String name, ModelMap modelMap) {
        modelMap.addAttribute("msg", name); // 相当于request.setAttribute("msg",name);
        System.out.println(name);
        return "test";
    }
```

**Model**

```java
    @RequestMapping("data/d5")
    public String d5(@RequestParam("username") String name, Model model) {
        model.addAttribute("msg", name); // 相当于request.setAttribute("msg",name);
        System.out.println(name);
        return "test";
    }
```

**对比**

- Model只有寥寥几个方法, 只适合用于储存数据, 简化了操作和理解

- ModelMap继承了LinkedMap, 除了实现了自身的一些方法, 还拥有LinkedMap的方法和特性

- ModelAndView可以在储存数据的同时设置返回的逻辑视图, 实现控制展示层的跳转

以上是就基础层面而言, 后续考虑性能优化就需要进一步对比

##### 乱码问题

**测试**

创建表单form.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="./encode" method="post">
        <input type="text" name="name">
        <input type="submit">
    </form>
</body>
</html>
```

创建Controller类

```java
@Controller
public class EncodeController {

    @RequestMapping("/encode")
    public String encode(Model model, String name) {
        model.addAttribute("msg", name); // 存放表单提交的数据
        return "test";
    }
}
```

在form.jsp表单中输入中文测试可能会出现乱码

**解决方案**

可以通过配置过滤器解决, SpringMVC中正好提供了一个过滤器, 可以在web.xml中配置

```xml
	<!--配置SpringMVC的乱码过滤-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

但在极端情况下, 这个过滤器对于GET请求的支持不是很好, 可以用以下步骤配置

1.修改tomcat的配置文件server.xml, 在源文件中指定URIEncoding的属性值

```xml
<Connector URIEncoding="utf-8" port="8080" protocol="HTTP/1.1"
          connectionTimeout="20000"
          redirectPort="8443" />
```

2.自定义过滤器

```java
public class GenericEncodeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 处理response的字符编码
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("text/html;charset=UTF-8");
        // 转型为与协议相关对象
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 对request包装增强
        HttpServletRequest myRequest = new MyRequest(request);
        filterChain.doFilter(myRequest, response);
    }

    // 自定义HttpServletRequest的包装类
    class MyRequest extends HttpServletRequestWrapper {

        private HttpServletRequest request;
        private boolean hasEncode; // 是否进行编码
        public MyRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        // 增强原有的方法
        @Override
        public Map getParameterMap() {
            // 获取请求方式
            String method = request.getMethod();
            if (method.equalsIgnoreCase("post")) {
                // post请求乱码处理
                try {
                    request.setCharacterEncoding("utf-8");
                    return request.getParameterMap();
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            } else if (method.equalsIgnoreCase("get")) {
                // get请求乱码处理
                Map<String, String[]> parameterMap = request.getParameterMap();
                if (!hasEncode) { // 手动编码逻辑只需运行一次
                    for (String s : parameterMap.keySet()) {
                        String[] values = parameterMap.get(s);
                        if (values != null) {
                            for (int i = 0; i < values.length; i++) {
                                try {
                                    values[i] = new String(values[i].getBytes("ISO-8859-1"), "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                    hasEncode = true;
                }
                return parameterMap;
            }
            return super.getParameterMap();
        }

        // 只获取一个值
        @Override
        public String getParameter(String name) {
            Map<String, String[]> parameterMap = getParameterMap();
            String[] values = parameterMap.get(name);
            if (values == null) {
                return null;
            }
            return values[0]; // 返回第一个值即可
        }

        // 获取所有值
        @Override
        public String[] getParameterValues(String name) {
            Map<String, String[]> parameterMap = getParameterMap();
            String[] values = parameterMap.get(name);
            return values;
        }
    }

    @Override
    public void destroy() {

    }
}
```

3.在web.xml中配置该自定义过滤器

```xml
    <!--自定义过滤器配置-->
    <filter>
        <filter-name>filter</filter-name>
        <filter-class>com.entropy.filter.GenericEncodeFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>filter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

### JSON交互处理

#### JSON的概念

- JSON(JavaScript Object Notation, JS 对象标记) 是一种轻量级的数据交换格式, 目前使用特别广泛
- 采用完全独立于编程语言的**文本格式**来存储和表示数据
- 简洁和清晰的层次结构使得 JSON 成为理想的数据交换语言
- 易于阅读和编写, 同时也易于机器解析和生成, 并有效地提升网络传输效率

任何JavaScript支持的类型都可以通过JSON来表示, 例如字符串、数字、对象、数组等

**语法格式要求**

- 对象以键值对的形式编写, 由逗号分隔不同的数据
- 花括号{}用于存储对象
- 方括号[]用于保存数组

**JSON键值对**是存储JavaScript对象的一种方式。键值对中, 键和值均使用双引号包围, 使用冒号分隔键和值

```json
{"name": "app"},
{"age": "4"}
```

JSON可以理解为是JavaScript对象的字符串表示方法, 使用特定的文本格式来表示一个JS对象的信息, 本质是一个字符串

```javascript
var obj = {a: 'Hello', b: 'World'}; // JS对象, 键名可加或不加引号
var json = '{"a": "Hello", "b": "World"}'; //JSON字符串
```

**JSON字符串和JavaScript对象相互转换**

JSON字符串转换为JavaScript对象, 使用**JSON.parse()**方法

```javascript
var obj = JSON.parse('{"a": "Hello", "b": "World"}');
// 转换结果为 {a: 'Hello', b: 'World'}
```

JavaScript对象转换为JSON字符串, 使用**JSON.stringify()**方法

```javascript
var json = JSON.stringify({a: 'Hello', b: 'World'});
// 转换结果为 '{"a": "Hello", "b": "World"}'
```

**测试**

1.在web目录下创建html文件

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript">
      // 编写JavaScript对象 ES6
      var user = {
        name: "app",
        age: 18,
        work: "code"
      };
      console.log(user);

      // JS对象转换为JSON字符串
      var json = JSON.stringify(user);
      console.log(json);

      console.log("-----------------------");

      // JSON字符串转换为JS对象
      var obj = JSON.parse(json);
      console.log(obj)
    </script>
</head>
<body>

</body>
</html>
```

2.在浏览器中查看控制台输出

#### Controller返回JSON数据

Jackson解析工具是目前较好的用于解析json数据的工具

1.引入依赖, 并**在Project Structure中手动选中依赖引入**

```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
<dependency>
   <groupId>com.fasterxml.jackson.core</groupId>
   <artifactId>jackson-databind</artifactId>
   <version>2.9.8</version>
</dependency>
```

2.完成SpringMVC所需要的基本配置, web.xml以及springmvc-servlet.xml, 这里不再重复说明, 参考上文给出的配置即可

3.编写实体类、Controller类

实体类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer age;
}
```

Controller类, 这里使用了@ResponseBody注解跳过视图解析器, 不会被解析为跳转路径, 而是直接返回数据

```java
@Controller
public class UserController {
    @RequestMapping("/json/t1")
    @ResponseBody // 跳过视图解析器, 直接返回字符串
    public String test1() throws JsonProcessingException {
        // 创建一个jackson的对象映射器用于解析数据
        ObjectMapper objectMapper = new ObjectMapper();
        // 创建一个对象
        User user = new User(1, "测试", 4);
        // 将Java对象转换为json字符串
        String value = objectMapper.writeValueAsString(user);
        return value;
    }
}
```

中文不显示问题, 直接在@RequestMapping中设置produces属性来指定编码和返回类型即可

```java
@RequestMapping(value = "/json/t1", produces = "application/json;charset=utf-8")
```

**JSON数据乱码全局统一解决方案**

在springmvc-servlet.xml中添加配置

```xml
    <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
```

**统一返回JSON字符串**

在类上直接使用@RestController注解, 该注解下的所有方法返回的都是json字符串, 相当于在每个方法上添加了@ResponseBody注解, 在前后端分离开发中十分便捷

```java
@RestController
public class UserJSONController {
    @RequestMapping("/json/j1")
    public String json1() throws JsonProcessingException {
        // 创建jackson对象映射器用于解析数据
        ObjectMapper objectMapper = new ObjectMapper();
        // 创建Java对象
        User user = new User(234, "tree", 12);
        // 转换为json字符串
        String value = objectMapper.writeValueAsString(user);
        return value;
    }

    // 输出集合
    @RequestMapping("json/j2")
    public String json2() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<User> list = new ArrayList<User>();

        User a = new User(1, "苹果", 11);
        User b = new User(2, "鸭梨", 12);
        User c = new User(3, "c", 13);
        User d = new User(4, "d", 14);

        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);

        String value = objectMapper.writeValueAsString(list);
        return value;
    }

    // 输出时间(时间戳格式
    @RequestMapping("/json/j3")
    public String json3() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Date date = new Date();
        // 时间对象在jackson中解析后的默认格式为 Timestamp 时间戳
        String value = objectMapper.writeValueAsString(date);
        return value;
    }

    // 输出时间(自定义格式)
    @RequestMapping("/json/j4")
    public String json4() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // 不使用时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 自定义时间日期格式对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 指定时间日期格式
        objectMapper.setDateFormat(sdf);

        Date date = new Date();
        String value = objectMapper.writeValueAsString(date);
        return value;
    }

    // 另一种自定义时间格式的方式
    @RequestMapping("/json/j5")
    public String json5() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Date date = new Date();
        // 自定义时间日期格式对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return objectMapper.writeValueAsString(sdf.format(date));
    }
}
```

对于经常重复使用的代码, 可以将其抽取出来制成单独的工具类, 如从上面的Controller类中抽取出日期时间格式化的代码制成工具类

```java
public class JsonUtils {

    public static String getJson(Object object) {
        return getJson(object, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getJson(Object object, String dateFormat) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 不使用时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 自定义时间日期格式对象
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        // 指定时间日期格式
        objectMapper.setDateFormat(sdf);
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
```

使用工具类之后的Controller类方法代码

```java
    // 引用自己编写的时间日期格式化工具类
    @RequestMapping("/json/j6")
    public String j6() {
        Date date = new Date();
        String json = JsonUtils.getJson(date);
        return json;
    }
```

#### FastJson的使用

fastjson.jar是Alibaba开发的一款专门用于Java开发的包, 可以方便地实现**json对象与JavaBean对象**的转换, 实现**JavaBean对象与json字符串**的转换, 实现**json对象与json字符串**的转换

引入fastjson依赖, 并**在Project Structure中手动引入依赖**

```xml
<dependency>
   <groupId>com.alibaba</groupId>
   <artifactId>fastjson</artifactId>
   <version>1.2.60</version>
</dependency>
```

##### fastjson 三个主要的类

**JSONObject代表json对象** 

- JSONObject实现了Map接口, 推测JSONObject底层操作是由Map实现的
- JSONObject对应json对象, 通过各种形式的get()方法可以获取json对象中的数据, 也可利用如size()、isEmpty()等方法获取键值对的个数和判断是否为空。其本质是通过实现Map接口并调用接口中的方法完成的

**JSONArray代表json对象数组**

- 内部是由List接口中的方法来完成操作的

**JSON代表JSONObject和JSONArray的转化**

- JSON类源码分析与使用
- 这些方法, 主要是实现json对象, json对象数组, javabean对象, json字符串之间的相互转化

创建一个测试类

```java
public class FastJsonTest {
    public static void main(String[] args) {
        List<User> list = new ArrayList<User>();

        User a = new User(1, "a", 11);
        User b = new User(2, "b", 12);
        User c = new User(3, "c", 13);
        User d = new User(4, "d", 14);

        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        // Java对象转换为Json字符串
        System.out.println("JavaObject To JsonString");
        String jsonString = JSON.toJSONString(list);
        System.out.println(list + " -> " + jsonString);
        String s = JSON.toJSONString(a);
        System.out.println(a + " -> " + s);
        System.out.println();
        // Json字符串转换为Java对象
        System.out.println("JsonString To JavaObject");
        User user = JSON.parseObject(s, User.class);
        System.out.println(s + " -> " + user);
        System.out.println();
        // Java对象转换为Json对象
        System.out.println("JavaObject To JsonObject");
        JSONObject jsonObject = (JSONObject) JSON.toJSON(user);
        System.out.println(user + " -> " + jsonObject);
        System.out.println();
        // Json对象转换为Java对象
        System.out.println("JsonObject To JavaObject");
        User javaObject = JSON.toJavaObject(jsonObject, User.class);
        System.out.println(jsonObject + " -> " + javaObject);
    }
}
```

对于这些工具类, 只需掌握它的使用方法, 并能找到其适用的业务场景去实现对应的要求即可

### SSM框架整合

#### 前提知识储备

- MySQL数据库
- Spring
- JavaWeb
- MyBatis
- 前端基础知识

#### 环境及工具

环境

- Java 8

- MySQL 5.7.30
- Tomcat 9.0.8
- Maven 3.5.2

工具(可根据实际情况替换)

- 后端: IntelliJ IDEA
- 前端: VsCode
- 数据库: navicat
- 测试: Apifox
- 文档: Typora

#### 整体结构

```tree
└─SSM-Demo
    ├─src
    │  ├─main
    │  │  ├─java
    │  │  │  └─com
    │  │  │      └─entropy
    │  │  │          ├─controller
    │  │  │          ├─dao
    │  │  │          ├─pojo
    │  │  │          └─service
    │  │  └─resources
    │  └─test
    │      └─java
    └─web
        └─WEB-INF
            ├─css
            ├─img
            ├─js
            └─jsp
```

#### 数据库环境搭建

使用navicat连接MySQL, 创建一个名为ssm_demo的数据库, 可参考以下SQL语句建表

```sql
/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : ssm_demo

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 01/12/2022 13:06:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '书名',
  `counts` int(255) DEFAULT NULL COMMENT '数量',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '简介',
  `price` decimal(10, 2) DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, 'C++', 12, '算法实现', 89.00);
INSERT INTO `book` VALUES (2, 'Java', 33, 'web应用', 66.00);
INSERT INTO `book` VALUES (3, 'Linux', 15, '基础入门', 77.00);

SET FOREIGN_KEY_CHECKS = 1;
```

#### 基本环境搭建

1.创建一个新的maven项目SSM-Demo, 添加web支持

2.引入相关依赖, 配置静态资源导出

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>SSM-Demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!--lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.16</version>
        </dependency>
        <!--Junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!--MySQL-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!--Connection Pool-->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>

        <!--Servlet JSP-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>

        <!--MyBatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.2</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>

        <!--Spring-->
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
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.13</version>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
</project>
```

#### MyBatis整合

1.数据库配置文件database.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/ssm_demo?useSSL=false&useUnicode=true&characterEncoding=UTF-8
jdbc.username=root
jdbc.password=root
```

2.mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--数据源由Spring配置实现, 这里主要进行基础配置-->
    <!--取别名-->
    <typeAliases>
        <package name="com.entropy.pojo"/>
    </typeAliases>

    <!--注册mapper-->
    <mappers>
        <mapper class="com.entropy.dao.BookMapper"/>
    </mappers>
</configuration>
```

3.实体类Book

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    // 这里的成员变量与数据库中的字段一一对应
    private Integer id;
    private String name;
    private Integer counts;
    private String detail;
    private BigDecimal price;
}
```

4.dao层Mapper接口

 ```java
public interface BookMapper {
    // 增加Book
    int addBook(Book book);
    // 根据ID删除Book
    int deleteBookById(@Param("bookId") int id); // @Param注解用于命名SQL参数
    // 更新Book
    int updateBook(Book book);
    // 根据ID查询Book
    Book queryById(@Param("bookId") int id);
    // 查询所有Book
    List<Book> queryAll();
    // 根据name查询Book
    List<Book> queryByName(@Param("bookName") String name);
}
 ```

5.Mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.entropy.dao.BookMapper">

    <!--增加Book-->
    <insert id="addBook" parameterType="Book">
        insert into ssm_demo.book(name,counts,detail,price)
        values (#{name},#{counts},#{detail},#{price})
    </insert>
    <!--根据ID删除Book-->
    <delete id="deleteBookById" parameterType="int">
        delete from ssm_demo.book where id=#{bookId}
    </delete>
    <!--更新Book-->
    <update id="updateBook" parameterType="Book">
        update ssm_demo.book set name=#{name},counts=#{counts},detail=#{detail},price=#{price} where id=#{id}
    </update>
    <!--根据ID查询Book-->
    <select id="queryById" resultType="Book">
        select * from ssm_demo.book where id=#{bookId}
    </select>
    <!--查询所有Book-->
    <select id="queryAll" resultType="Book">
        select * from ssm_demo.book
    </select>
    <!--根据name查询Book-->
    <select id="queryByName" resultType="Book">
        select * from ssm_demo.book where name like concat('%',#{bookName},'%')
    </select>
</mapper>
```

6.service层接口与实现类

接口

```java
public interface BookService {
    // 增加Book
    int addBook(Book book);
    // 根据ID删除Book
    int deleteBookById(int id); // @Param注解用于命名SQL参数
    // 更新Book
    int updateBook(Book book);
    // 根据ID查询Book
    Book queryById(int id);
    // 查询所有Book
    List<Book> queryAll();
    // 根据name查询Book
    List<Book> queryByName(String name);
}
```

实现类

```java
public class BookServiceImpl implements BookService {

    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public int addBook(Book book) {
        return bookMapper.addBook(book);
    }

    @Override
    public int deleteBookById(int id) {
        return bookMapper.deleteBookById(id);
    }

    @Override
    public int updateBook(Book book) {
        return bookMapper.updateBook(book);
    }

    @Override
    public Book queryById(int id) {
        return bookMapper.queryById(id);
    }

    @Override
    public List<Book> queryAll() {
        return bookMapper.queryAll();
    }

    @Override
    public List<Book> queryByName(String name) {
        return bookMapper.queryByName(name);
    }
}
```

#### Spring整合

1.spring-dao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!--整合MyBatis-->
    <!--1.关联database.properties-->
    <context:property-placeholder location="classpath:database.properties"/>

    <!--2.配置数据库连接池-->
    <!--数据库连接池
        dbcp 半自动化操作 不能自动连接
        c3p0 自动化操作(自动加载配置文件并设置到对象里面)
    -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--配置连接池属性-->
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>

        <!--c3p0连接池私有属性-->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!--设置关闭连接后不会自动提交事务-->
        <property name="autoCommitOnClose" value="false"/>
        <!--设置获取连接的超时时间-->
        <property name="checkoutTimeout" value="10000"/>
        <!--设置获取连接失败的重连次数-->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

    <!--3.配置SqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--注入数据库连接池-->
        <property name="dataSource" ref="dataSource"/>
        <!--关联MyBatis全局配置文件mybatis-config.xml-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!--4.配置扫描dao包, 动态实现dao接口注入Spring容器-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--注入sqlSessionFactory, 注意这里使用的是value而不是ref-->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!--指定扫描包-->
        <property name="basePackage" value="com.entropy.dao"/>
    </bean>
</beans>
```

2.spring-service.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!--扫描service下的包-->
    <context:component-scan base-package="com.entropy.service"/>

    <!--注入service实现类-->
    <bean id="BookServiceImpl" class="com.entropy.service.BookServiceImpl">
        <property name="bookMapper" ref="bookMapper"/>
    </bean>

    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--注入数据库连接池-->
        <property name="dataSource" ref="dataSource"/>
    </bean>
</beans>
```

#### SpringMVC整合

1.web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <!--加载总配置文件-->
            <param-value>classpath:applicationContext.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--encodingFilter乱码过滤-->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--Session过期时间-->
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>
</web-app>
```

2.spring-mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!--配置SpringMVC-->
    <!--1.开启注解驱动-->
    <mvc:annotation-driven/>
    <!--2.静态资源默认配置-->
    <mvc:default-servlet-handler/>
    <mvc:resources mapping="/js/**" location="/WEB-INF/js/"/>
    <mvc:resources mapping="/img/**" location="/WEB-INF/img/"/>
    <mvc:resources mapping="/css/**" location="/WEB-INF/css/"/>
    <!--3.扫描web相关包, controller包-->
    <context:component-scan base-package="com.entropy.controller"/>
    <!--4.配置视图解析器ViewResolver-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

3.总配置文件applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <import resource="spring-dao.xml"/>
    <import resource="spring-service.xml"/>
    <import resource="spring-mvc.xml"/>
</beans>
```

#### 具体功能实现

创建BookController和相应的jsp视图

注意jsp中涉及的js、css、img素材可直接使用本项目提供的素材或自行寻找素材, 素材来源于网络, 如有侵权请联系 [s-chance (Exceed System) (github.com)](https://github.com/s-chance) 

##### 1.查询全部书籍

方法

```java
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;

    // 查询所有书籍
    @RequestMapping("/all")
    public String all(Model model) {
        List<Book> books = bookService.queryAll();
        model.addAttribute("all", books);
        return "allBooks";
    }
}
```

首页视图index.jsp(默认生成的, 与WEB-INF是同级, 用于初始页面)

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>首页</title>
    <style type="text/css">
      a {
        text-decoration: none;
        color: black;
        font-size: 18px;
      }
      h3 {
        width: 180px;
        height: 38px;
        margin: 100px auto;
        text-align: center;
        line-height: 38px;
        background: deepskyblue;
        border-radius: 4px;
      }
    </style>
  </head>
  <body background="${pageContext.request.contextPath}/img/index.png" style="background-repeat:no-repeat;background-size:100% 100%;background-attachment: fixed;">
    <h3>
      <a href="${pageContext.request.contextPath}/book/all">进入书籍列表页</a>
    </h3>
  </body>
</html>
```

书籍列表页allBooks.jsp(WEB-INF/jsp目录下)

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍列表</title>
    <%--BootStrap美化--%>
    <!--引入BootStrap-->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body background="${pageContext.request.contextPath}/img/list.png" style="background-repeat:no-repeat;background-size:100% 100%;background-attachment: fixed;">
<div class="container">

    <div class="row clearfix">

        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>书籍列表</small>
                </h1>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4 column">
                <%--跳转到添加书籍页面--%>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAdd">新增书籍</a>
                <%--全部查询--%>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/all">显示全部书籍</a>
            </div>

            <div class="col-md-4 column"></div>
            <div class="col-md-4 column">
                <%--搜索框--%>
                <form action="${pageContext.request.contextPath}/book/queryByName" class="form-inline" method="post">
                    <input type="text" name="name" class="form-control" placeholder="请输入书名" required>
                    <input type="submit" value="查询" class="btn btn-primary">
                </form>
            </div>
        </div>

    </div>

    <div class="row clearfix">

        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th>编号</th>
                        <th>书名</th>
                        <th>库存</th>
                        <th>详情</th>
                        <th>价格</th>
                        <th>操作</th>
                    </tr>
                </thead>

                <%--EL表达式遍历后端传递过来的数据--%>
                <taody>
                    <c:forEach var="book" items="${all}">
                        <tr>
                            <td>${book.id}</td>
                            <td>${book.name}</td>
                            <td>${book.counts}</td>
                            <td>${book.detail}</td>
                            <td>${book.price}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/book/toUpdate?id=${book.id}">修改</a>
                                &nbsp; | &nbsp;
                                <a href="${pageContext.request.contextPath}/book/delete/${book.id}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </taody>
            </table>
            <span style="color: red;font-weight: bold">${error}</span>
        </div>

    </div>

</div>
</body>
</html>
```

##### 2.添加书籍

方法

```java
    // 跳转到添加书籍页面
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "addBook";
    }

    // 添加书籍
    @RequestMapping("/add")
    public String add(Book book) {
        System.out.println("add " + book);
        bookService.addBook(book);
        // 重定向到书籍列表页面
        return "redirect:/book/all";
    }
```

添加书籍页addBook.jsp(WEB-INF/jsp目录下)

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加书籍</title>
    <%--引入BootStrap--%>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <%--正则表达式验证--%>
    <script>
        $(function () {
            $("#counts").blur(function () {
                var regx = new RegExp("^[0-9]*$");
                var counts = $("#counts").val();
                var legal = regx.test(counts);
                if (!legal) {
                    alert("检测到非法的库存输入, 请重新输入");
                    $("#counts").val("");
                }
            });
        });
    </script>
</head>
<body background="${pageContext.request.contextPath}/img/addPage.png" style="background-repeat:no-repeat;background-size:100% 100%;background-attachment: fixed;">
<div class="container">

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>添加书籍</small>
                </h1>
            </div>
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/book/add" method="post">
        <div class="form-group">
            <label>书名: </label>
            <input type="text" name="name" class="form-control" required>
        </div>
        <div class="form-group">
            <label>库存: </label>
            <input id="counts" type="text" name="counts" class="form-control" required>
        </div>
        <div class="form-group">
            <label>详情: </label>
            <input type="text" name="detail" class="form-control" required>
        </div>
        <div class="form-group">
            <label>价格: </label>
            <input type="text" name="price" class="form-control" required>
        </div>
        <div class="form-group">
            <input type="submit" class="form-control" value="add">
        </div>
    </form>

</div>
</body>
</html>
```

##### 3.修改书籍

方法

```java
    // 跳转到修改页面, 携带id数据用于修改指定书籍信息
    @RequestMapping("/toUpdate")
    public String toUpdate(int id, Model model) {
        Book book = bookService.queryById(id);
        model.addAttribute("book", book);
        return "updateBook";
    }

    // 修改书籍信息
    @RequestMapping("/update")
    public String update(Book book, Model model) {
        System.out.println("update " + book);
        bookService.updateBook(book);
        // 更新书籍后的信息
        Book queryById = bookService.queryById(book.getId());
        System.out.println("after update " + queryById);
        return "redirect:/book/all";
    }
```

修改书籍页updateBook.jsp(WEB-INF/jsp目录下)

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改书籍</title>
    <!-- 引入 Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <%--正则表达式验证--%>
    <script>
        $(function () {
            $("#counts").blur(function () {
                var regx = new RegExp("^[0-9]*$");
                var counts = $("#counts").val();
                var legal = regx.test(counts);
                if (!legal) {
                    alert("检测到非法的库存输入, 请重新输入");
                    $("#counts").val("");
                }
            });
        });
    </script>
</head>
<body background="${pageContext.request.contextPath}/img/updatePage.png" style="background-repeat:no-repeat;background-size:100% 100%;background-attachment: fixed;">
<div class="container">

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改书籍信息</small>
                </h1>
            </div>
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/book/update" method="post">
        <%--隐藏域携带id信息--%>
        <input type="hidden" name="id" value="${book.id}">
        <%--数据回显--%>
        <div class="form-group">
            <label>书名: </label>
            <input type="text" name="name" class="form-control" value="${book.name}" required>
        </div>
        <div class="form-group">
            <label>库存: </label>
            <input id="counts" type="text" name="counts" class="form-control" value="${book.counts}" required>
        </div>
        <div class="form-group">
            <label>详情: </label>
            <input type="text" name="detail" class="form-control" value="${book.detail}" required>
        </div>
        <div class="form-group">
            <label>价格: </label>
            <input type="text" name="price" class="form-control" value="${book.price}" required>
        </div>
        <div class="form-group">
            <input type="submit" class="form-control" value="update">
        </div>
    </form>

</div>

</body>
</html>
```

##### 4.删除书籍

方法

```java
    // 删除书籍
    @RequestMapping("/delete/{bookId}")
    public String delete(@PathVariable("bookId") int id) {
        bookService.deleteBookById(id);
        return "redirect:/book/all";
    }
```

##### 5.搜索书籍

方法

```java
    // 根据书名查询
    @RequestMapping("/queryByName")
    public String queryByName(String name, Model model) {
        List<Book> books = bookService.queryByName(name);
        if (books.size() == 0) {
            model.addAttribute("error", "未找到任何信息");
        } else {
            model.addAttribute("all", books);
        }
        System.out.println(books);
        return "allBooks";
    }
```

在allBooks.jsp中增加搜索框(前面的代码中已经添加)

```jsp
		<div class="row">
            <div class="col-md-4 column">
                <%--跳转到添加书籍页面--%>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAdd">新增书籍</a>
                <%--全部查询--%>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/all">显示全部书籍</a>
            </div>

            <div class="col-md-4 column"></div>
            <div class="col-md-4 column">
                <%--搜索框--%>
                <form action="${pageContext.request.contextPath}/book/queryByName" class="form-inline" method="post">
                    <input type="text" name="name" class="form-control" placeholder="请输入书名" required>
                    <input type="submit" value="查询" class="btn btn-primary">
                </form>
            </div>
		</div>
```

##### 6.运行测试

配置好tomcat启动运行项目测试

##### 7.可能出现的问题及应对方案

- 所有问题**优先考虑配置问题**, 再考虑逻辑问题

- 项目依赖缺失问题, 刷新maven依赖并在Project Structure中手动导入依赖
- 视图加载问题, js、css、img等素材如果是通过url方式链接的话会受到网络状态的影响, 可考虑直接将素材下载到本地, 并在springmvc中配置
- 数据操作问题, 检查数据库连接是否成功(数据库相关的配置), 再排查mapper.xml文件中的SQL语法问题以及mapper接口参数传递问题, 然后依次往service层、controller层、jsp视图排查问题

**到这里一个基于SSM整合框架的基本网站搭建完成, 但这个网站只包括最基本的增删改查操作, 下面继续补充SpringMVC的知识**

- Ajax和Json
- 文件上传和下载
- 拦截器

### Ajax

####  简介

- **AJAX即Asynchronous JavaScript and XML（异步的 JavaScript 和 XML）**
- AJAX 是一种在**无需重新加载整个网页**的情况下, 能够**更新部分网页**的技术
- **Ajax 不是一种新的编程语言, 而是一种用于创建更好更快以及交互性更强的Web应用程序的技术**
- 在 2005 年, Google 通过其 Google Suggest 使 AJAX 变得流行起来(Google Suggest能够**自动完成搜索单词**)
- Google Suggest 使用 AJAX 创造出动态性极强的 web 界面: 当您在谷歌的搜索框**输入关键字时**, JavaScript 会把这些字符**发送到服务器**, 然后服务器会返回一个搜索建议的列表
- 传统的网页(即不用ajax技术的网页), 想要更新内容或者提交一个表单, 都需要**重新加载整个网页**
- 使用ajax技术的网页, 通过在后台服务器进行少量的数据交换, 就可以实现异步局部更新
- 使用Ajax, 用户可以创建接近本地桌面应用的直接、高可用、更丰富、更动态的Web用户界面

#### 模拟Ajax

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript">
      function ajax() {
        var url = document.getElementById("url").value;
        document.getElementById("frame").src=url;
      }
    </script>
</head>
<body>
  <div>
    <p>加载url地址</p>
    <p>
      <input type="text" id="url" value="https://cn.bing.com/">
      <input type="button" value="load" onclick="ajax()">
    </p>
  </div>
  <div>
    <iframe id="frame" style="width: 100%;height: 500px"></iframe>
  </div>
</body>
</html>
```

**Ajax的主要用途**

- 注册用户信息校验
- 登录失败提示信息
- 删除数据时局部更新页面
- ...

#### jQuery使用Ajax

- Ajax的核心是XMLHttpRequest对象(XHR)。XHR为向服务器发送请求和解析服务器响应提供了接口, 能够以异步方式从服务器获取新数据

- jQuery 提供了多个与 AJAX 有关的方法

- 通过 jQuery AJAX 方法, 能够使用 HTTP Get 和 HTTP Post 从远程服务器上请求文本、HTML、XML 或 JSON, 同时能够把这些外部数据直接载入网页的被选元素中
- jQuery Ajax本质就是 XMLHttpRequest, 对其进行了封装, 方便调用

>jQuery.ajax(...)
>      部分参数：
>            url：请求地址
>            type：请求方式，GET、POST（1.9.0之后用method）
>        headers：请求头
>            data：要发送的数据
>    contentType：即将发送信息至服务器的内容编码类型(默认: "application/x-www-form-urlencoded; charset=UTF-8")
>          async：是否异步
>        timeout：设置请求超时时间（毫秒）
>      beforeSend：发送请求前执行的函数(全局)
>        complete：完成之后执行的回调函数(全局)
>        success：成功之后执行的回调函数(全局)
>          error：失败之后执行的回调函数(全局)
>        accepts：通过请求头发送给服务器，告诉服务器当前客户端可接受的数据类型
>        dataType：将服务器端返回的数据转换成指定类型
>          "xml": 将服务器端返回的内容转换成xml格式
>          "text": 将服务器端返回的内容转换成普通文本格式
>          "html": 将服务器端返回的内容转换成普通文本格式，在插入DOM中时，如果包含JavaScript标签，则会尝试去执行
>        "script": 尝试将返回值当作JavaScript去执行，然后再将服务器端返回的内容转换成普通文本格式
>          "json": 将服务器端返回的内容转换成相应的JavaScript对象
>        "jsonp": JSONP 格式使用 JSONP 形式调用函数时，如 "myurl?callback=?" jQuery 将自动替换 ? 为正确的函数名，以执行回调函数

**使用最原始的HttpServletResponse处理Ajax请求**

1.配置web.xml和springmvc-servlet.xml

web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    
    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联SpringMVC配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    
    <!--请求拦截-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    
    <!--乱码过滤-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```

springmvc-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--自动扫描包-->
    <context:component-scan base-package="com.entropy.controller"/>
    <!--静态资源过滤-->
    <mvc:default-servlet-handler/>
    <mvc:resources mapping="/statics/js/**" location="/WEB-INF/statics/js/"/>
    <!--注解驱动-->
    <mvc:annotation-driven/>
    
    <!--json乱码过滤-->
    <mvc:annotation-driven>
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <!--视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

2.编写Controller类

```java
@RestController
public class AjaxTestController {

    @RequestMapping("/a1")
    public void a1(String name, HttpServletResponse response) throws IOException {
        System.out.println(name);
        if ("admin".equals(name)) {
            response.getWriter().print(true);
        } else {
            response.getWriter().print(false);
        }
    }
}
```

3.编写index.jsp, 这里使用离线本地文件引入jQuery, 注意路径是在/WEB-INF/statics/js目录下

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.3.1.min.js"></script>
    <script>
      function a() {
        $.post({
          url:"${pageContext.request.contextPath}/a1",
          data:{"name":$("#username").val()},
          success:function (data, status) {
            alert(data);
            console.log(data);
            console.log(status);
          }
        });
      }
    </script>
  </head>
  <body>
    <%--失去焦点事触发事件--%>
    name: <input type="text" id="username" onblur="a()">
  </body>
</html>
```

4.启动tomcat测试, 用浏览器的开发者工具可以看到在鼠标触发事件时发送了Ajax请求

#### SpringMVC实现Ajax

**注意在springmvc-servlet中配置json乱码过滤(上文已经进行配置)**

1.编写实体类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private Integer age;
    private String password;
}
```

2.新增Controller类方法

```java
    @RequestMapping("/a2")
    public List<User> a2() {
        List<User> list = new ArrayList<>();
        list.add(new User("first", 12, "12121"));
        list.add(new User("second", 13, "ababa"));
        list.add(new User("third", 18, "cdcdc"));
        return list; // 返回list的json字符串形式
    }
```

3.jsp页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.3.1.min.js"></script>
    <script>
      $(function () {
        $("#btn").click(function () {
          $.post("${pageContext.request.contextPath}/a2",function (data) {
            console.log(data);
            var html = "";
            for (let i = 0; i < data.length; i++) {
              html+="<tr>" +
                      "<td>" + data[i].name + "</td>" +
                      "<td>" + data[i].age + "</td>" +
                      "<td>" + data[i].password + "</td>" +
                      "</tr>";
            }
            $("#content").html(html);
          });
        });
      });
    </script>
</head>
<body>
  <input type="button" id="btn" value="获取用户列表">
  <table width="80%" align="center">
    <tr>
      <th>name</th>
      <th>age</th>
      <th>password</th>
    </tr>
    <tbody id="content" align="center">
      <%--用jQuery构造DOM结构, 并接收后端数据--%>
    </tbody>
  </table>
</body>
</html>
```

#### Ajax实现注册提示

1.新增Controller类方法

```java
    @RequestMapping("/a3")
    public String a3(String name, String password) {
        String msg = "";
        // 模拟数据库验证
        String data = "admin";
        if (name != null) {
            if (data.equals(name)) {
                msg = "该名称已存在, 请重新输入";
            } else {
                msg = "PASS";
            }
        }
        if (password != null) {
            if (password.contains(data)) {
                msg = "密码不能包含特定名称, 如admin";
            } else {
                msg = "PASS";
            }
        }
        return msg;
    }
```

2.register.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.3.1.min.js"></script>
    <script>
    function a1() {
        $.post({
          url:"${pageContext.request.contextPath}/a3",
          data:{'name':$("#name").val()},
          success:function (data) {
            if (data.toString() === 'PASS') {
              $("#userInfo").css("color", "green");
            } else {
              $("#userInfo").css("color", "red");
            }
            $("#userInfo").html(data);
          }
        });
      }
    function a2() {
      $.post({
        url:"${pageContext.request.contextPath}/a3",
        data:{'password':$("#password").val()},
        success:function (data) {
          if (data.toString() === 'PASS') {
            $("#passInfo").css("color", "green");
          } else {
            $("#passInfo").css("color", "red");
          }
          $("#passInfo").html(data);
        }
      });
    }
    </script>
</head>
<body>
  <p>
    用户名: <input type="text" id="name" onblur="a1()">
    <span id="userInfo"></span>
  </p>
  <p>
    密码: <input type="password" id="password" onblur="a2()">
    <span id="passInfo"></span>
  </p>
</body>
</html>
```

#### JSONP获取baidu接口

```html
<!DOCTYPE HTML>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>JSONP百度搜索</title>
  <style>
    #q{
      width: 500px;
      height: 30px;
      border:1px solid #ddd;
      line-height: 30px;
      display: block;
      margin: 0 auto;
      padding: 0 10px;
      font-size: 14px;
    }
    #ul{
      width: 520px;
      list-style: none;
      margin: 0 auto;
      padding: 0;
      border:1px solid #ddd;
      margin-top: -1px;
      display: none;
    }
    #ul li{
      line-height: 30px;
      padding: 0 10px;
    }
    #ul li:hover{
      background-color: #f60;
      color: #fff;
    }
  </style>
  <script>
    window.onload = function(){
      // 获取输入框和ul
      var Q = document.getElementById('q');
      var Ul = document.getElementById('ul');
      // 事件鼠标抬起时候
      Q.onkeyup = function(){
        // 如果输入框不等于空
        if (this.value != '') {
          // JSONP重点
          // 创建标签
          var script = document.createElement('script');
          //给定要跨域的地址 赋值给src
          //这里是要请求的跨域的地址 我写的是百度搜索的跨域地址
          script.src = 'https://sp0.baidu.com/5a1Fazu8AA54nxGko9WTAnF6hhy/su?wd='+this.value+'&cb=demo';
          // 将组合好的带src的script标签追加到body里
          document.body.appendChild(script);
        }
      }
    }

    function demo(data){
      var Ul = document.getElementById('ul');
      var html = '';
      // 如果搜索数据存在 把内容添加进去
      if (data.s.length) {
        // 隐藏掉的ul显示出来
        Ul.style.display = 'block';
        // 搜索到的数据循环追加到li里
        for(var i = 0;i<data.s.length;i++){
          html += '<li>'+data.s[i]+'</li>';
        }
        // 循环的li写入ul
        Ul.innerHTML = html;
      }
    }
  </script>
</head>
<body>
<input type="text" id="q" />
<ul id="ul">

</ul>
</body>
</html>
```

### 拦截器和文件上传下载

#### 概述

SpringMVC的**处理器拦截器**类似于Servlet开发中的**过滤器Filter**, 用于对处理器进行预处理和后处理, 可以自定义一些拦截器来实现特定的功能

**过滤器与拦截器的区别: 拦截器是AOP思想的具体应用**

**过滤器**

- servlet规范中的一部分, 任何JavaWeb工程都可以使用
- 在url-pattern中配置了/*之后, 可以对所有要访问的资源进行拦截

**拦截器** 

- 拦截器是SpringMVC框架提供的, 只有在基于SpringMVC框架的工程里才能使用
- 拦截器只会拦截访问的控制器方法, 如果访问的是jsp、html、css、image、js等文件是不会进行拦截的

#### 自定义拦截器

自定义拦截器需要实现**HandlerInterceptor接口**

1.创建新的Module, 添加web支持

2.配置web.xml和springmvc-servlet.xml

3.创建拦截器类

```java
public class MyInterceptor implements HandlerInterceptor {
    // 在请求处理的方法之前执行
    // 返回true则执行下一个拦截器, false则不执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("方法执行前");
        return true;
    }

    // 在请求处理的方法之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("方法执行后");
    }

    // 在DispatcherServlet处理完之后执行, 负责清理工作
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("清理");
    }
}
```

2.在springmvc-servlet中配置拦截器

```xml
    <!--配置拦截器-->
    <mvc:interceptors>
        <mvc:interceptor>
            <!--/**包括当前路径和所有子路径-->
            <!--/admin/*这种仅拦截/admin/add之类的, 而不能拦截/admin/add/id等-->
            <!--/admin/**则会拦截admin下的所有路径-->
            <mvc:mapping path="/**"/>
            <!--注入编写的自定义拦截器-->
            <bean class="com.entropy.interceptor.MyInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>
```

3.创建Controller类

```java
@RestController
public class TestController {

    @GetMapping("/t1")
    public String t1() {
        System.out.println("TestController执行t1方法");
        return "OK";
    }
}
```

4.编写jsp页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <a href="${pageContext.request.contextPath}/t1">拦截器测试</a>
  </body>
</html>
```

5.配置好tomcat启动测试

#### 拦截器实现用户认证

**思路**

1.登录页面和对应的controller方法

2.登录页面通过表单提交的方式传递数据给controller指定的方法, 由controller中的方法验证用户信息。通过验证则在session域中写入用户信息, 返回登录成功的信息

3.直接访问success.jsp, 拦截用户请求, 判断用户是否已经登录, 是则放行, 否则跳转到登录页面

**具体实现**

1.登录页面login.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <h1>登录页面</h1>
  <form action="${pageContext.request.contextPath}/user/login" method="post">
    用户名: <input type="text" name="username"><br/>
    密码: <input type="password" name="password"><br/>
    <input type="submit" value="提交">
  </form>
</body>
</html>
```

2.编写Controller类方法

```java
@Controller
@RequestMapping("/user")
public class LoginController {

    // 存储用户信息, 跳转到登录成功页面
    @RequestMapping("/login")
    public String login(HttpSession session, String username, String password) {
        session.setAttribute("loginInfo", username + " : " + password);
        return "success";
    }

    // 跳转到登录页面
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    // 跳转到成功登录页面
    @RequestMapping("/success")
    public String main() {
        return "success";
    }

    // 退出登录, 清除用户登录信息
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginInfo");
        return "login";
    }
}
```

3.登录成功页面success.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
    <h1>登录成功</h1>
    <h2>当前用户信息</h2>
    <h3>${loginInfo}</h3>
    <a href="${pageContext.request.contextPath}/user/logout">登出</a>
  </body>
</html>
```

4.修改index.jsp用于测试登录与未登录情况下跳转的页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <a href="${pageContext.request.contextPath}/t1">拦截器测试</a>
    <a href="${pageContext.request.contextPath}/user/toLogin">访问用户登录认证页面</a>
    <a href="${pageContext.request.contextPath}/user/success">直接访问成功登录页面</a>
  </body>
</html>
```

5.编写拦截器类

```java
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是登录页面则放行
        System.out.println("URI: " + request.getRequestURI());
        if (request.getRequestURI().contains("login")) {
            return  true;
        }
        HttpSession session = request.getSession();
        // 如果已经存储了用户信息, 放行
        if (session.getAttribute("loginInfo") != null) {
            return true;
        }
        // 没有任何用户信息则强制跳转到登录页面
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
```

6.在springmvc-servlet.xml配置拦截器

```xml
    <!--用户认证拦截器-->
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/user/**"/>
            <bean class="com.entropy.interceptor.LoginInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>
```

7.配置tomcat启动测试, 分别在登录的情况下、未登录的情况下、登录后再登出的情况下访问成功登录页面

**特别补充：关于逻辑**

在应用程序开发中，通常可以将逻辑分为以下几类：

1. 业务逻辑：实现应用程序特定功能的代码，例如处理用户订单、计算商品价格和运费、检查库存和更新订单状态等。

2. 基础设施逻辑：与应用程序的基础设施相关的代码，例如数据库访问、网络通信和文件操作等。

3. 验证和错误处理逻辑：用于确保应用程序能够正确地处理用户输入和外部数据的代码，例如数据规范性、完整性检查以及空数据和非法数据的检测等。

4. 横切关注点：与应用程序的多个模块或功能相关的逻辑，例如身份验证、授权、日志记录和异常处理等。

5. 非功能性需求：与应用程序的性能、可靠性、安全性和可用性等方面相关的需求，例如优化算法、提高系统稳定性和增强安全防护等。

> 业务逻辑是应用程序的核心，也是应用程序的主要价值所在。基础设施逻辑是应用程序的基础，是应用程序能够正常运行的基础。验证和错误处理逻辑是应用程序的保障，是应用程序能够正确运行的保障。横切关注点是应用程序的补充，是应用程序的辅助。非功能性需求是应用程序的保证，是应用程序的保障。
> 
> service层主要是业务逻辑，dao层主要是基础设施逻辑，controller层主要是验证和错误处理逻辑，aop层主要是横切关注点，非功能性需求则是贯穿于整个应用程序的各个层次。

#### 文件上传和下载

文件上传和下载是项目开发中最常用的功能之一。SpringMVC支持文件上, 但SpringMVC默认没有装配**MultipartResolver**(用于文件传输), 因此要通过SpringMVC处理文件传输工作需要手动在配置文件中配置**MultipartResolver**

**前端表单上传文件的特别要求**: 必须将表单的method设置为**POST**, 并将enctype设置为multipart/form-data。只有在这样的情况下, 浏览器才会把选择的文件以**二进制数据**发送给服务器

**表单中的 enctype 属性的详细说明**

- application/x-www=form-urlencoded: 默认方式, 只处理表单域中的 value 属性值, 采用这种编码方式的表单会将表单域中的值处理成 URL 编码方式

- multipart/form-data: 这种编码方式会以**二进制流**的方式来处理表单数据, 这种编码方式会把文件域指定文件的内容也封装到请求参数中, 不会对字符编码

- text/plain: 除了把空格转换为 "+" 号外, 其他字符都不做编码处理, 这种方式适用直接通过表单发送邮件

  ```html
  <form action="" enctype="multipart/form-data" method="post">
     <input type="file" name="file"/>
     <input type="submit">
  </form>
  ```

- 一旦设置了enctype为multipart/form-data，浏览器即会采用二进制流的方式来处理表单数据, 而对于文件上传的处理则涉及在服务器端解析原始的HTTP响应。在2003年, Apache Software Foundation发布了开源的Commons FileUpload组件, 其很快成为Servlet/JSP程序员上传文件的最佳选择

- Servlet3.0规范已经提供方法来处理文件上传, 但这种上传需要在Servlet中完成, 而Spring MVC则提供了更简单的封装
- Spring MVC为文件上传提供了直接的支持, 这种支持是用即插即用的MultipartResolver实现的
- Spring MVC使用Apache Commons FileUpload技术实现了一个MultipartResolver实现类**CommonsMultipartResolver**。因此, SpringMVC的文件上传还需要依赖Apache Commons FileUpload的组件

##### 文件上传

1.引入文件上传所需的依赖, 并在Project Structure中手动引入依赖

```xml
    <dependencies>
        <!--文件上传-->
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.3</version>
        </dependency>
        <!--servlet-api-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
        </dependency>
    </dependencies>
```

2.配置springmvc-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--自动扫描包-->
    <context:component-scan base-package="com.entropy.controller"/>
    <!--静态资源过滤-->
    <mvc:default-servlet-handler/>
    <mvc:annotation-driven/>

    <!--json乱码过滤-->
    <mvc:annotation-driven>
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <!--视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--文件上传配置-->
    <!--这里的id必须为multipartResolver, 否则上传文件时会出现400错误-->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <!--请求的编码格式, 必须和jsp的pageEncoding属性一致, 以便正确读取表单的内容, 默认为ISO-8859-1-->
        <property name="defaultEncoding" value="utf-8"/>
        <!--上传文件大小限制, 单位为字节 10485760B=10M-->
        <property name="maxUploadSize" value="10485760"/>
        <property name="maxInMemorySize" value="40960"/>
    </bean>
</beans>
```

3.配置web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别, 数字越小, 启动越快-->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!--请求拦截-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--乱码过滤-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```

4.编写index.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div>
        <h1>上传通道一</h1>
        <form action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" method="post">
            <input type="file" name="file">
            <input type="submit" value="upload">
        </form>  
    </div>
    <div>
        <h1>上传通道二</h1>
        <form action="${pageContext.request.contextPath}/upload2" enctype="multipart/form-data" method="post">
            <input type="file" name="file">
            <input type="submit" value="upload">
        </form>
    </div>
</body>
</html>
```

5.编写FileController

```java
@Controller
public class FileController {
    // @RequestParam("file") 将name=file的控件得到的文件封装成CommonsMultipartFile对象
    // 批量上传创建CommonsMultipartFile的数组即可
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        // 文件为空直接返回首页
        if ("".equals(originalFilename)) {
            return "redirect:/index.jsp";
        }
        System.out.println("上传文件: " + originalFilename);
        // 上传文件保存路径
        String path = request.getServletContext().getRealPath("/upload");
        // 若该路径不存在则自动创建
        File realPath = new File(path);
        if (!realPath.exists()) {
            realPath.mkdir();
        }
        System.out.println("上传文件保存路径: " + realPath);

        // 文件输入流
        InputStream inputStream = file.getInputStream();
        // 文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream(new File(realPath, originalFilename));
        // 读写
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, len);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
        inputStream.close();
        return "redirect:/index.jsp";
    }

    // 采用CommonsMultipartFile自带的transferTo方法实现文件上传
    @RequestMapping("/upload2")
    public String upload2(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        // 上传文件保存路径
        String path = request.getServletContext().getRealPath("/upload");
        File realPath = new File(path);
        if (!realPath.exists()) {
            realPath.mkdir();
        }
        System.out.println("上传文件保存路径: " + realPath);

        // 通过CommonsMultipartFile的方法直接写文件
        file.transferTo(new File(realPath + "/" + file.getOriginalFilename()));

        return "redirect:/index.jsp";
    }
}
```

6.配置tomcat启动访问index.jsp

**CommonsMultipartFile的常用方法**

- **String getOriginalFilename()：获取上传文件的原名**
- **InputStream getInputStream()：获取文件流**
- **void transferTo(File dest)：将上传文件保存到一个目录文件中**

##### 文件下载

**步骤**

1.设置response响应头

2.读取文件InputStream

3.写入文件OutputStream

4.执行读写操作

5.关闭流

**具体实现**

1.新增FileController类方法

```java
    @RequestMapping("/download")
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取下载图片的路径
        String path = request.getServletContext().getRealPath("/upload");
        File[] files = new File(path).listFiles();
        String fileName = files[0].getName();
        // 1.设置response响应头
        response.reset(); // 清空缓存
        response.setCharacterEncoding("UTF-8"); // 设置字符编码
        response.setContentType("multipart/form-data"); // 设置二进制流传输数据
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        File file = new File(path, fileName);
        // 2.读取文件
        InputStream fileInputStream = new FileInputStream(file);
        // 3.写入文件
        ServletOutputStream outputStream = response.getOutputStream();

        byte[] bytes = new byte[1024];
        int len = 0;
        // 4.读写操作
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }
        outputStream.close();
        fileInputStream.close();
        return null;
    }
```

2.修改index.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<div>
  <h1>上传通道一</h1>
  <form action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" method="post">
    <input type="file" name="file">
    <input type="submit" value="upload">
  </form>
</div>
<div>
  <h1>上传通道二</h1>
  <form action="${pageContext.request.contextPath}/upload2" enctype="multipart/form-data" method="post">
    <input type="file" name="file">
    <input type="submit" value="upload">
  </form>
</div>
<br/>
<div>
  <h1>
    <a href="${pageContext.request.contextPath}/download">
      下载文件
    </a>
  </h1>
</div>
</body>
</html>
```

3.启动tomcat测试文件下载

至此, SpringMVC的基础内容完结。**对比之前的JavaWeb原生方式, 可以体会到框架对项目开发所带来的影响**
