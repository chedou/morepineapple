package com.jnshu.reception.dao;

import com.jnshu.reception.pojo.*;
import org.junit.*;
import org.junit.runner.*;
import org.mybatis.spring.annotation.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

/**
 * @program: morepineapple
 * @description:
 * @author: Mr.huang
 * @create: 2019-02-03 18:23
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "com.jnshu.reception.*")
public class companyInfoTest {

	private final org.slf4j.Logger LOGGER =LoggerFactory.getLogger(this.getClass());

	@Autowired(required = false)
	private CompanyInfoMapper companyInfoMapper;

	@Test
	public void recommendCompany() {
		LOGGER.info("-------------------------");
		LOGGER.info("推荐公司");
		System.out.println(companyInfoMapper.toString());
		companyInfoMapper.recommendCompany();
		List<CompanyInfo2> companyInfo2List = companyInfoMapper.recommendCompany();
		System.out.println(companyInfo2List.toString());
		// if ( 0 == companyInfo2List.size() ){
		// 	System.out.println("所得查询之为 0");
		// }else {
		// 	System.out.println(companyInfo2List);
		// }

	}

	@Test
	public void getCompanyDetail() {
		LOGGER.info("-------------------------");
		LOGGER.info("查询公司");
		List<CompanyInfo2> companyInfoList2 =companyInfoMapper.getCompanyDetail(1L);
		System.out.println(companyInfoList2.toString());


	}
}
