### Maven
#### 概念
- 原始的javaSE和javaEE项目在团队开发中, 各个模块所用到的jar包可能不统一, 且还可能出现兼容性问题, 这加重了项目维护升级的负担。为了解决这一系列问题, maven就应运而生
- maven本质上是一个项目管理工具, 将项目开发和管理过程抽象成一个项目对象模型POM(Project Object Model)
- maven本身就是由java编写, 它的核心文件就是pom.xml, 这个文件就代表了一个项目
- maven具有依赖管理功能, 通过依赖管理maven就能对项目资源进行管理
- maven依赖管理的资源主要有三个位置: 本地仓库、私服、中央仓库
- maven除了管理资源, 还能通过相关插件对项目进行构建, 管理项目生命周期

#### 作用
- 项目构建
- 依赖管理
- 统一团队开发标准和开发结构

#### 项目目录结构说明
src/main/java: 项目源码(java文件等)  
src/main/resources: 项目相关配置文件  
src/main/webapp: web资源(前端文件等)

#### 环境搭建
##### 下载
maven[官网](http://maven.apache.org/)  
注意: maven版本需要对应IDEA的版本进行选择, 建议根据自己IDEA的版本来选择maven版本, 否则可能会出现问题  
##### 安装
直接解压官网下载的zip压缩包即可
##### maven根目录结构说明
bin: 可执行程序目录  
boot: maven自身的启动加载器  
conf: maven配置文件目录
lib: maven自身运行所需库的目录
##### 环境变量配置
maven由java编写通过java环境运行, 故最基本的java环境变量需要配置好  
maven环境变量配置只需参考java环境变量配置即可  
打开cmd执行`mvn -v`测试maven环境是否配置成功, 若出现了版本号的信息则表示配置成功

#### 仓库
maven中仓库主要是指存在各种jar资源的地方, 可以是本地电脑, 也可以是服务器  
本地仓库: 开发者本地电脑上的仓库(通俗来看就是一个文件夹), 里面存储的大量jar资源基本都是从远程仓库联网下载下来的  
私服: 一些开发团队或企业搭建的仓库, 同样能从中央仓库下载jar资源  
中央仓库: 由maven开发团队搭建的开源的仓库, 其它仓库的大部分jar资源都是从中央仓库下载的  

关于私服:  
私服相对中央仓库, 是闭源的, 因此私服会存储一些付费购买的或者自研的jar资源来保护版权  
私服能在一定范围内共享资源, 即仅对内开放或者对特定用户开放等

#### 坐标
maven中的坐标用于描述仓库中特定jar资源的位置  
坐标主要包括:  
groupId(当前资源隶属组织名称)  
artifactId(当前资源的名称)  
version(当前资源的版本号)  
packaging(打包方式):  
jar(java普通项目的包)  
war(javaWeb的包, war包基本只用于产品发布阶段, 而非开发阶段)  
pom(表示本资源为父资源, 不进行打包)  

maven坐标查询[官网](https://mvnrepository.com/), 输入名称查询到坐标后复制坐标到pom.xml文件中完成下载  

#### 仓库配置
maven仓库默认下载位置是C盘, 要修改仓库位置需要在maven的conf目录下找到settings.xml文件进行修改  
配置过程: 在settings.xml中找到以下字样
```text
  <!-- localRepository
   | The path to the local repository maven will use to store artifacts.
   |
   | Default: ${user.home}/.m2/repository
  <localRepository>/path/to/local/repo</localRepository>
  -->
```
上面描述的就是maven本地仓库的位置, 根据注释中的说明, 仿照它的格式在下面写好路径即可  
例如: 配置本地仓库位置在D盘目录下, 只需要在注释下面添加`  <localRepository>D:\maven\repository</localRepository>`后保存即可, 路径中的repository文件夹就是指定的本地仓库  

maven仓库默认的远程仓库是中央仓库, 但在国内从中央仓库直接下载jar资源比较慢, 可以配置国内的站点镜像, 加快资源下载速度  
配置过程: 在settings.xml中找到
```text
  <!-- mirrors
   | This is a list of mirrors to be used in downloading artifacts from remote repositories.
   |
   | It works like this: a POM may declare a repository to use in resolving certain artifacts.
   | However, this repository may have problems with heavy traffic at times, so people have mirrored
   | it to several places.
   |
   | That repository definition will have a unique id, so we can create a mirror reference for that
   | repository, to be used as an alternate download site. The mirror site will be the preferred
   | server for that repository.
   |-->
  <mirrors>
    <!-- mirror
     | Specifies a repository mirror site to use instead of a given repository. The repository that
     | this mirror serves has an ID that matches the mirrorOf element of this mirror. IDs are used
     | for inheritance and direct lookup purposes, and must be unique across the set of mirrors.
     |
    <mirror>
      <id>mirrorId</id>
      <mirrorOf>repositoryId</mirrorOf>
      <name>Human Readable Name for this Mirror.</name>
      <url>http://my.repository.com/repo/path</url>
    </mirror>
  </mirrors>
     -->
```
在mirrors标签内添加镜像仓库位置, 例如
```text
  <mirrors>
    <mirror>
      <id>nexus-aliyun</id>
      <mirrorOf>central</mirrorOf>
      <name>Nexus aliyun</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public</url>
    </mirror>
  </mirrors>
```

#### IDEA集成Maven

1. IDEA创建maven项目
在New Project或者New Module界面可以看见maven选项, 根据需求可选择是否从archetype(原型/模板)创建maven项目  
基础项目直接创建即可  
maven-archetype-quickstart快速开始(相对缺少一些目录)  
cocoon-22-archetype-webapp创建web工程(同样缺少一些目录)  
缺少的目录可通过IDEA提示进行补全
2. IDEA配置maven
File->Settings->maven, 然后设置maven的根目录, settings.xml文件以及配置好的仓库文件路径, 针对当前项目的设置  
File->New Projects Settings->Settings for New Projects, 这个是对之后所有新建的maven项目进行设置, 而不需要每次新建项目再重新去设置maven  
注意: 刚刚创建完项目之后, 需要等待文件加载完成。源文件夹、资源文件夹等在IDEA中会有特殊的颜色提示。若未看到颜色提示请等待加载或者重新创建项目  
3. IDEA引入依赖
- 在common模块下的[pom.xml](common/pom.xml)中添加junit的依赖
    ```xml
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>
    ```
  添加完后刷新maven, 否则可能识别不到依赖, 之后如果出现找不到依赖的问题请优先考虑刷新maven, 再看看是否还有其它问题
- 编写代码测试junit[示例](common/src/test/java/JunitTest.java)
#### 关于web项目的说明  
maven从原型创建的web项目默认有一个jetty的插件, 相对tomcat是一款轻量级java服务器, 但不用于大规模企业开发  
maven插件: maven本身就是依赖插件来运行的框架。插件不同于依赖, 插件能够执行具体的工作, 而依赖只是第三方包或工具  
maven提供了tomcat的插件, 相当于内置了tomcat服务器, 要修改端口、访问路径直接在pom.xml中修改即可, 修改完后刷新maven
```xml
<build>
    <finalName>webapp</finalName>
    <plugins>
      <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.2</version>
        <configuration>
            <port>8080</port>
            <path>/</path>
            <uriEncoding>UTF-8</uriEncoding>
      </configuration>
      </plugin>
    </plugins>
  </build>
```
插件使用: 在IDEA的右侧打开maven操作面板, 找到Plugins下的tomcat插件运行后控制台会给出url  
注意:  
- 访问如果出现404状态码, 可能是src/main/webapp目录下缺少了index.jsp文件  
- 若从原型创建的web项目的WEB-INF下如果有自动生成的配置文件在报错, 请删除后再重新运行tomcat插件

#### maven依赖管理
**依赖配置**
```xml
<!--设置当前项目所有依赖的jar-->
<dependencies>
    <!--设置具体依赖-->
    <dependency>
        <!--依赖所属群组id-->
        <groupId>junit</groupId>
        <!--依赖所属项目id-->
        <artifactId>junit</artifactId>
        <!--依赖的版本号-->
        <version>4.12</version>
    </dependency>
</dependencies>
```
**依赖传递**  

直接依赖: 在当前项目中通过依赖配置建立的依赖关系  
间接依赖: 被依赖的资源如果依赖其他资源, 则表明当前项目间接依赖其他资源  
注意: 直接依赖和间接依赖其实也是一个相对关系  
**依赖传递的冲突问题**

解决依赖传递冲突有三种优先法则
1. 路径优先: 当依赖中出现相同资源时, 层级越深, 优先级越低, 反之则越高
2. 声明优先: 当资源在相同层级被依赖时, 配置顺序靠前的覆盖靠后的
3. 特殊优先: 当同级配置了相同资源的不同版本时, 后配置的覆盖先配置的

**依赖范围**

默认依赖是能在项目内任何地方使用  
通过`scope`标签能够设置依赖的作用范围:  
主程序范围(src/main目录下)  
测试程序范围(src/test目录下)  
打包范围(package指令范围)  

#### maven生命周期与插件
常用的: clean清理、compile编译、test测试、package打包、install安装  
clean与install组合用于清楚缓存再重新构建  
package则是打包整个项目为jar包或者war包, 打包好的程序可以部署到服务器上面运行   

**插件**

- 插件与生命周期内的阶段绑定, 在执行到对应生命周期时执行对应的插件
- maven默认在各个生命周期上都绑定了预先设定的插件来完成相应功能
- 插件还可以实现一些自定义功能

maven官网对插件的[介绍](http://maven.apache.org/plugins/index.html)

最后附上web项目开发必备依赖的一个[清单](webapp/template.txt)