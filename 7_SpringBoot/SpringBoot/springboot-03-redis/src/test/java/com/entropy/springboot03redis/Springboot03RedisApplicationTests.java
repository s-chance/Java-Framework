package com.entropy.springboot03redis;

import com.entropy.springboot03redis.pojo.User;
import com.entropy.springboot03redis.utils.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

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
	@Test
	public void testUser() throws JsonProcessingException {
		User user = new User(12, "api");
		// 实际开发中传递的都是json数据
//		String s = new ObjectMapper().writeValueAsString(user);
		redisTemplate.opsForValue().set("user", user);
		System.out.println(redisTemplate.opsForValue().get("user"));
	}

	@Autowired
	private RedisUtils redisUtils;
	@Test
	public void testUtils() {
		redisUtils.set("name", "utils");
		System.out.println(redisUtils.get("name"));
	}
}
