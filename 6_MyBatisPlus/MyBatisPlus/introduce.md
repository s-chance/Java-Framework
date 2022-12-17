### MyBatisPlus

#### 1.概述

**MyBatisPlus**在**MyBatis**的基础上进一步进行了增强, 极大简化了CRUD代码的编写。 在学习MyBatisPlus之前, 请先学习MyBatis、Spring、SpringMVC的知识, 以便于更好地使用以及整合MyBatisPlus

#### 2.简介

官网[MyBatis-Plus](https://baomidou.com/)

github官方仓库[baomidou/mybatis-plus](https://github.com/baomidou/mybatis-plus)

MyBatisPlus(简称MP), 是MyBatis的增强工具, 在MyBatis的基础上只进行增强而不进行改造, 目的就是为了简化开发、提高效率

#### 3.特性

摘自官网原文[MyBatis-Plus (baomidou.com)](https://baomidou.com/pages/24112f/)

- **无侵入**：只做增强不做改变，引入它不会对现有工程产生影响
- **损耗小**：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
- **强大的 CRUD 操作**：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，有强大的条件构造器，满足各类使用需求，简单的CRUD操作基本不需要再由开发者手动编写
- **支持 Lambda 形式调用**：通过 Lambda 表达式，方便的编写各类查询条件，避免字段写错
- **支持主键自动生成**：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
- **支持 ActiveRecord 模式**：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
- **支持自定义全局通用操作**：支持全局通用方法注入（ Write once, use anywhere ）
- **内置代码生成器**：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，有多种自定义配置可供使用
- **内置分页插件**：基于 MyBatis 物理分页，开发者不需要实现具体操作，配置好插件之后，写分页等同于普通 List 查询
- **分页插件支持多种数据库**：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer 等多种数据库
- **内置性能分析插件**：可输出 SQL 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
- **内置全局拦截插件**：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作

### 一、快速入门

官网地址[快速开始 | MyBatis-Plus (baomidou.com)](https://baomidou.com/pages/226c21/#初始化工程)

如何使用第三方插件: 

1.导入对应的依赖

2.研究如何配置依赖

3.研究如何编写代码

4.提高扩展技术能力

**步骤**

1.创建数据库mybatis_plus

2.创建user表, sql语句如下

```mysql
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);
```

**注意: 实际开发中往往会有这四个字段, version(乐观锁)、deleted(逻辑删除)、gmt_create(创建时间)、gmt_modified(修改时间)**

增加测试数据, sql语句如下

```mysql
DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```

3.创建**SpringBoot**工程(工程将以 H2 作为默认数据库进行演示)

可以通过[Spring Initializr](https://start.spring.io/)在线快速初始化一个SpringBoot工程或者使用IntelliJ IDEA内置的**Spring Initializr**(本质上也是通过调用[Spring Initializr](https://start.spring.io/)实现初始化)

4.导入依赖

```xml
        <!--数据库驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!--lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.22</version>
        </dependency>
        <!--mybatis-plus-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.0.5</version>
        </dependency>
```

5.连接数据库, 在自动生成的application.properties中配置

```properties
# mysql5 驱动 com.mysql.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://localhost:3306/mybatis_plus?useSSL=false&useUnicode=true&characterEncoding=utf-8
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

# mysql8 驱动 com.mysql.cj.jdbc.Driver 且url需要额外配置时区 serverTimezone=GMT%2B8
```

6.编写实体类和mapper接口

实体类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

mapper接口

```java
@Repository //持久层
public interface UserMapper extends BaseMapper<User> {
    //直接继承BaseMapper即可实现所有的基本的CRUD操作
    //需要在主启动类配置扫描mapper包注解@MapperScan
}
```

在主启动类MyBatisPlusApplication中配置@MapperScan

```java
@SpringBootApplication
@MapperScan("com.entropy.mybatisplus.mapper")
public class MyBatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusApplication.class, args);
    }

}
```

7.在测试类MyBatisPlusApplicationTests中测试

```java
@SpringBootTest
class MyBatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        //参数是一个条件构造器, 为null则表示查询全部
        //查询全部用户
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

}
```

**对比原生MyBatis, SQL语句以及对应的接口方法都已经在MyBatisPlus中实现**

### 二、配置日志

通过日志可以清楚地看到sql执行的细节, 便于快速排查错误

```properties
# 配置日志(系统自带的控制台输出)
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

### 三、CRUD扩展

#### 1.新增操作

```java
    @Test
    public void insert() {
        User user = new User();
        user.setName("mp");
        user.setAge(1);
        user.setEmail("mp@baomidou.com");

        //id自动随机生成, 且不会重复
        int insert = userMapper.insert(user);
        System.out.println("insert = " + insert); 
        System.out.println(user);
    }
}
```

MyBatisPlus生成的id是全局唯一的

#### 2.主键生成策略

[分布式系统唯一ID生成方案汇总 - nick hao - 博客园 (cnblogs.com)](https://www.cnblogs.com/haoxinyue/p/5208136.html)

**雪花算法**

snowflake是Twitter开源的分布式ID生成算法，结果是一个long型的ID。其核心思想是：使用41bit作为毫秒数，10bit作为机器的ID（5个bit是数据中心，5个bit的机器ID），12bit作为毫秒内的流水号（意味着每个节点在每毫秒可以产生 4096 个 ID），最后还有一个符号位，永远是0。可以保证几乎全球唯一！

**主键自增**

使用主键自增策略需要先保证数据表的主键字段已经设置为自动递增

1.在navicat中设计表的id主键字段为自动递增

2.在实体类对应的属性上添加注解@TableId

```java
    @TableId(type = IdType.AUTO) //自增策略
    private Long id;
```

3.再次测试insert方法

4.部分源码说明

```java
public enum IdType {
    AUTO(0), // 数据库id自增
    NONE(1), // 未设置主键
    INPUT(2), // 手动输入，自定义id
    ID_WORKER(3), // 全局唯一id(默认的)
    UUID(4), // 全局唯一id uuid
    ID_WORKER_STR(5); // ID_WORKER 字符串表示法
}
```

#### 3.更新操作

```java
    @Test
    public void update() {
        User user = new User();
        user.setId(6L); //通过条件自动破解sql
        user.setName("update");
        user.setAge(11);
        int i = userMapper.updateById(user);
        System.out.println(i);
    }
```

#### 4.自动填充

创建时间、修改时间这些操作一般都是自动化完成。每个数据表为了实际开发需求都需要配置gmt_create、gmt_modified这两个字段, 且需要实现自动化创建和更新

**数据库层面**

1.通过navicat设计表的结构, 直接添加字段create_time和update_time, 类型均为int

2.在实体类中添加对应的属性

```java
    private Date createTime;
    private Date updateTime;
```

3.测试insert方法手动记录当前时间, 查看结果

**代码层面**

1.删除数据库中的默认值并手动更新

2.实体类添加@TableField注解配置对应的填充策略

```java
    @TableField(fill = FieldFill.INSERT) //字段自动填充内容
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
```

3.编写处理器类来处理这个注解

```java
@Slf4j
@Component // 处理器注册到IOC容器中
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 新增操作时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill...");
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    // 更新操作时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill...");
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
```

4.测试insert方法, 查看结果

5.测试update方法, 查看结果

#### 5.乐观锁

>乐观锁：顾名思义，它总是认为不会出现问题，无论干什么都不去上锁！如果出现了问题，再次更新值测试！
>
>悲观锁：顾名思义，它总是认为总是出现问题，无论干什么都上锁！再去操作！
>
>乐观锁适用于读多写少的情况, 悲观锁适用于写多读少的情况
>
>乐观锁和悲观锁都是为了解决事务并发造成的问题而被设计出来的

乐观锁实现方式: 获取记录时, 得到version字段。在执行更新操作时, 会以set version = newVersion where version = oldVersion, 如果这个oldVersion值不匹配(**说明有人同时操作了目标数据**), 则更新失败

**测试MyBatisPlus的乐观锁插件**

1.数据库中添加一个version字段

2.实体类添加对应的属性并加上乐观锁的@Version注解

```java
    @Version //乐观锁的@Version注解
    private Integer version;
```

3.创建MyBatisPlusConfig注册乐观锁插件

```java
@MapperScan("com.entropy.mybatisplus.mapper")
@EnableTransactionManagement //开启事务支持
@Configuration //配置类
public class MyBatisPlusConfig {
    // 注册乐观锁插件
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
```

**可能遇到的问题org.apache.ibatis.binding.BindingException: Parameter 'MP_OPTLOCK_VERSION_ORIGINAL' not found.**是由于MyBatisPlus高版本中OptimisticLockerInterceptor已经过时, 误用了高版本中的OptimisticLocker**Inner**Interceptor, 选择3.0.5版本的依赖即可

4.测试

```java
    //乐观锁成功的情况
    @Test
    public void optimisticLockOK() {
        // 查询用户信息
        User user = userMapper.selectById(6L);
        // 修改用户信息
        user.setName("optimistic");
        user.setAge(111);
        // 更新操作
        userMapper.updateById(user);
    }

    //乐观锁失败的情况
    @Test
    public void optimisticLockFail() {
        // 线程一
        User first = userMapper.selectById(6L);
        first.setName("first");
        first.setAge(1);

        // 线程二
        User second = userMapper.selectById(6L);
        second.setName("second");
        second.setAge(2);

        // 陷入自旋锁忙等状态
        // 如果没有乐观锁就会覆盖掉插队线程的值
        userMapper.updateById(second);
        userMapper.updateById(first);
    }
```

#### 6.查询操作

```java
    //单个查询
    @Test
    public void selectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }
    //批量查询
    @Test
    public void selectBatchIds() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    //根据map条件查询
    @Test
    public void selectByMap() {
        HashMap<String, Object> map = new HashMap<>();
        //自定义查询
        map.put("name", "Tom");
        map.put("age", 28);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }
```

#### 7.分页查询

分页查询在实际开发中经常使用

1.原始limit关键词进行分页

2.pageHelper第三方插件

3.MyBatisPlus内置插件

**内置分页插件使用**

1.配置拦截器组件

```java
    // 分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
```

2.直接使用Page对象

```java
    // 分页查询
    @Test
    public void page() {
        // 参数一: 当前页  参数二: 单页显示记录数目
        Page<User> page = new Page<>(1, 2);
        userMapper.selectPage(page, null);
        // 遍历结果集
        page.getRecords().forEach(System.out::println);
        // 总记录数
        System.out.println(page.getTotal());
    }
```

#### 8.删除操作

```java
    // 单个删除
    @Test
    public void deleteById() {

        userMapper.deleteById(1603415578496184325L);
    }

    // 批量删除
    @Test
    public void deleteBatchIds() {
        userMapper.deleteBatchIds(Arrays.asList(7, 8));
    }

    // map条件删除
    @Test
    public void deleteByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "mp");
        userMapper.deleteByMap(map);
    }
```

直到目前为主, 上面的删除方式都属于**物理删除,** 但是实际开发中还会使用**逻辑删除**的方式, 防止误操作造成损失

#### 9.删除方式

- **物理删除**, 又称硬删除、真删除, 即删除操作是将数据记录直接从数据库删除
- **逻辑删除**, 又称软删除、假删除, 通过添加删除标记或者将要删除的数据记录移动到另一张表的方式实现。好处就是对于误操作，数据被删除了, 可以很方便地将数据恢复

**逻辑删除的实现**

1.在数据库中添加一个deleted字段, 类型为int

2.实体类中添加对应的属性并加上注解@TableLogic

```java
    @TableLogic // 逻辑删除
    private Integer deleted;
```

3.注册组件

```java
    // 逻辑删除插件
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }
```

4.在application.properties中配置逻辑删除

```properties
# 配置逻辑删除 删除标记为1 未删除标记为0
mybatis-plus.global-config.db-config.logic-delete-value=1
mybatis-plus.global-config.db-config.logic-not-delete-value=0
```

5.测试删除操作, 查看日志输出

执行的实际上是update操作, 而不是真正的delete操作。deleted字段作为删除标记被更新, 在下次查询时作为条件进行判断

###  四、性能分析插件

在实际开发不可避免会遇到一些慢sql, 即执行耗时过长。因此, 需要进行监控

**性能分析拦截器能够监控并输出每条SQL语句及其执行时间**

MyBatisPlus也提供了性能分析插件, 当执行时间超过限制时间时就会停止执行

1.注册插件

```java
    // 性能分析插件
    @Bean
    @Profile({"dev","test"}) //设置dev test多环境
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // 设置sql执行的最大耗时限制, 超过限制则不执行
        performanceInterceptor.setMaxTime(100); //耗时超过100ms则不执行
        performanceInterceptor.setFormat(true); //是否格式化
        return performanceInterceptor;
    }
```

2.配置环境

```properties
# 配置开发环境
spring.profiles.active=dev
```

3.测试

```java
    // 性能测试
    @Test
    public void performTest() {
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }
```

使用性能分析插件后能够在控制台看到sql执行耗时, 但在最新版MP中该插件被移除, 可以使用第三方插件如druid监控或p6spy开源框架等

### 五、条件构造器

一些方法的测试, 注意查看日志分析其sql执行的过程

```java
    // 条件构造器Wrapper
    @Test
    public void test1() {
        // 查询name不为null的用户, 且邮箱不为null的用户, 年龄大于等于12
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .isNotNull("name")
                .isNotNull("email")
                .ge("age", 12);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }
    @Test
    public void test2() {
        // 查询name为Tom的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("name", "Tom");
        // selectOne单独查询一条记录
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }
    @Test
    public void test3() {
        // 查询age位于20-30之间的用户数量
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 20, 30);
        Integer count = userMapper.selectCount(wrapper);
        System.out.println(count);
    }
    @Test
    public void test4() {
        // 模糊查询
        // 查询name中不包含e, email是t开头的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .notLike("name", "e")
                .likeRight("email", "t"); //相当于like 't%'
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }
    @Test
    public void test5() {
        // 嵌套子查询
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.inSql("id", "select id from user where id < 3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }
    @Test
    public void test6() {
        // 通过id排序
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //wrapper.orderByDesc("id"); //降序
        wrapper.orderByAsc("id"); //升序
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
```

其他方法的使用可自行参考官网[条件构造器 | MyBatis-Plus ](https://baomidou.com/pages/10c804/#abstractwrapper)

### 六、代码生成器

**AutoGenerator 是 MyBatis-Plus 的代码生成器，通过 AutoGenerator 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码，极大地提升了开发效率**

#### 简单使用

由于代码生成器在3.0.3之后就从MyBatisPlus内部移除, 因此使用代码生成器需要手动添加外部依赖, 包括**代码生成器**和**模板引擎**的依赖

1.导入依赖, 这里使用的是[FreeMarker](https://freemarker.apache.org/)模板引擎, 其它引擎可参考官网[代码生成器（旧） | MyBatis-Plus](https://baomidou.com/pages/d357af/#添加依赖)自行配置

```xml
        <!--mybatis-plus-generator-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.0.5</version>
        </dependency>

        <!--freemarker-->
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>2.3.31</version>
        </dependency>
```

2.代码生成配置类参考用例, 部分配置请根据实际开发环境进行修改

```java
public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + ": ");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "! ");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("entropy");
        gc.setOpen(false);
        //gc.setSwagger2(true); //实体属性Swagger2注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/mybatis_plus?useUnicode=true&useSSL=false&characterEncoding=utf8");
        //dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.entropy.mybatisplus");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // todo nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名, 多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
```

启动后在控制台输入对应的模块名和需要映射的表名, 就会在指定目录下生成代码文件
