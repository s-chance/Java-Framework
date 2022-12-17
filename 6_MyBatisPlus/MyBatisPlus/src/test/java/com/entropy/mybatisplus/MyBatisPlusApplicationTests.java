package com.entropy.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entropy.mybatisplus.mapper.UserMapper;
import com.entropy.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

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

    @Test
    public void update() {
        User user = new User();
        user.setId(6L); //通过条件自动破解sql
        user.setName("update");
        user.setAge(11);
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

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

    // 单个删除
    @Test
    public void deleteById() {

        userMapper.deleteById(1L);
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

    // 性能测试
    @Test
    public void performTest() {
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

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
}
