package com.entropy.test;

import com.entropy.dao.BlogMapper;
import com.entropy.pojo.Blog;
import com.entropy.utils.MyBatisUtils;
import com.entropy.utils.UUIDUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    //一级缓存测试
    @Test
    public void localCache() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);

        Map map = new HashMap();
        map.put("title", "web6.0");
        List<Blog> blog = blogMapper.getBlogByIf(map);

//        sqlSession.clearCache(); //清理之后, 下一次相同的查询需要重新查询数据库
//        System.out.println("手动清理缓存");

        System.out.println("分割线1......");

        //本地缓存, 一级缓存, 从输出日志中可以看到这里实际上并没有执行SQL语句
        //它的数据是从缓存中获取的
        List<Blog> localCache = blogMapper.getBlogByIf(map);

        System.out.println("分割线2......");

        System.out.println(blog==localCache); //==比较的是地址, 不是内容

        sqlSession.close();
    }

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

    //自定义ehcache缓存测试
    @Test
    public void ehcache() {
        //创建缓存管理器
        CacheManager cacheManager = CacheManager.create("./src/main/resources/ehcache.xml");
        //获取缓存对象
        Cache cache = cacheManager.getCache("cloud_user");

        //创建元素
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Map map = new HashMap();
        map.put("title", "web6.0");
        List<Blog> blogByIf = blogMapper.getBlogByIf(map);
        System.out.println("blogByIf = " + blogByIf);
        sqlSession.close();

        Element element = new Element("title", blogByIf);

        //将元素添加到缓存
        cache.put(element);
        //获取缓存
        Element value = cache.get("title");
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
}
