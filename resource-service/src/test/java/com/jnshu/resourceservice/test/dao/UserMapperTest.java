package com.jnshu.resourceservice.test.dao;

import com.jnshu.resourceservice.*;
import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.entity.*;
import org.junit.*;

import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourcesServiceApplication.class)
public class UserMapperTest {
	private final org.slf4j.Logger logger =LoggerFactory.getLogger(this.getClass());

	@Autowired(required = false)
	private UserMapper userMapper;

	@Test
	public void findByUsername() {
		logger.info("-------------------------");
		logger.info("根据用户名测试获取单个角色权限关联信息");

		User user = userMapper.findByUsername("海澜之家2");
		System.out.println(user);
		// System.out.println(user.toString());

		logger.info("-------------------------");
		if (user.getAuthorities() != null){
			logger.info("getAuthorities 不为null");
			System.out.println("1212");
			System.out.println(user.getAuthorities().toString());

		}
	}



	@Test
	public void selectUserDetailById() {
		logger.info("-------------------------");
		logger.info("根据用户名测试获取单个角色权限关联信息");

		User user = userMapper.selectUserDetailById(20L);
		System.out.println(user);
		// System.out.println(user.toString());

		logger.info("-------------------------");
		if (user.getAuthorities() != null){
			logger.info("getAuthorities 不为null");
			System.out.println("1212");
			System.out.println(user.getAuthorities().toString());

		}
	}

	@Test
	public void insertSelective(){
		logger.info("-------------------------");
		logger.info("插入单个用户");

		User user = new User();
		user.setName("");
		user.setPassword("12awsdfewcfasdawd");
		System.out.println(user.toString());
		System.out.println(userMapper.insertSelective(user));
		System.out.println("新增数据之后返回的id:" + user.getId());
		logger.info("-------------------------");
		System.out.println(userMapper);

		logger.info("-------------------------");

	}

	@Test
	public void selectAll(){
		logger.info("-------------------------");
		logger.info("查询全部有效数据");
		List<User> userList = new ArrayList<>();

		// userList = userMapper.selectAll();
		System.out.println(userList.toString());
		logger.info("-------------------------");

	}

	@Test
	public void selectUserList(){
		logger.info("-------------------------");
		logger.info("查询全部有效数据");
		List<User> userList = new ArrayList<>();

		// 模拟参数
		// User user = new User();
		// user.setName("admin");
		// Role role = new Role();
		// role.setRoleName("super");

		// System.out.println(user.toString());
		// System.out.println(role.toString());


		// String a = null;
		// String b = null;
		userList = userMapper.selectUserList("admin", "super");
		// userList = userMapper.selectUserList(a, b);
		System.out.println(userList.size());
		System.out.println(userList.toString());
		if (0 != userList.size()){
			System.out.println(userList.get(0).toString());
		}

		logger.info("-------------------------");

	}
}