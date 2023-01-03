package com.entropy.springboot02jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

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
