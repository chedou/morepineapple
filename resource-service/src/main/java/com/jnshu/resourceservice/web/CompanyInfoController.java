package com.jnshu.resourceservice.web;

import com.github.pagehelper.*;
import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.service.*;
import io.swagger.annotations.*;
import org.apache.ibatis.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.management.*;
import java.util.*;

/**
 * @program: morepineapple
 * @description: 后台管理-公司信息
 * @author: Mr.huang
 * @create: 2019-01-14 21:43
 **/

@RestController
@RequestMapping()
@Api(tags = {"公司管理模块"}, description = "测试公司管理模块")
public class CompanyInfoController {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private CompanyInfoService companyInfoService;

	// TODO: 2018/11/8 后端后台
	// TODO: 2018/11/8 搜索公司列表
	@PostMapping(value = "/company")
	@PreAuthorize("hasAuthority('CompanyManageAll')")
	public RetResult<?> qureyCompanyList(String companyName, Integer companyIndustry, String city, String area, String produceName, Integer financingScale, Integer isApprove, Integer isFrozen,
										@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
										@RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
		logger.info("------------输入请求参数------------");
		logger.info("\n" + "companyName:" + companyName + "\n" + "companyIndustry:" + companyIndustry + "city:" + city + "\n" + "area:" + area + "\n" + "produceName:" + produceName + "financingScale:" + financingScale
				+ "\n" + "isApprove:" + isApprove + "\n" + "isFrozen:" + isFrozen + "\n" + "size:" + size + "\n" + "page:" + page);
		logger.info("------------------------");
		HashMap data = new HashMap();
		List list = new ArrayList();

		try {
			PageHelper.startPage(page, size);
			list = companyInfoService.qureyCompanyList(companyName, companyIndustry, city, area, produceName, financingScale, isApprove, isFrozen);
			PageInfo<CompanyInfo2> pageInfo = new PageInfo<CompanyInfo2>(list);
			data.put("total", pageInfo.getTotal());
			data.put("qureyCompanyList", pageInfo.getList());
			logger.info(pageInfo.getList().toString());
			return RetResponse.result(RetCode.SUCCESS_COMPANY_LIST_GET).setData(data);
		} catch (RuntimeException e) {
			logger.info(e.toString());
			return RetResponse.result(RetCode.COMPANY_PARAM_ERROR);
		}
	}


	// TODO: 2018/11/9 获取单个公司信息
	@GetMapping(value = "/company/{companyId}")
	@PreAuthorize("hasAuthority('CompanyManageAll')")
	public RetResult<?> getAcompanyInfo(@PathVariable Long companyId) {
		logger.info("------------输入请求参数------------");
		logger.info("\n" + "companyId:" + companyId + "\n");
		logger.info("------------------------");
		HashMap data = new HashMap();
		List list1 = new ArrayList();
		List list2 = new ArrayList();

		try {
			list1 = companyInfoService.getACompanyInfo(companyId);
			list2 = companyInfoService.getCompanyProdureDetail(companyId);
			logger.info(list1.toString());
			logger.info(list2.toString());
			data.put("getACompanyInfo", list1);
			data.put("getCompanyProdureDetail", list2);
			return RetResponse.result(RetCode.SUCCESS_USER_ONE_GET).setData(data);
		} catch (RuntimeMBeanException e) {
			logger.info(e.toString());
			return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);
		}
	}


	// TODO: 2018/11/9 插入公司信息接口
	@Transactional
	@Insert("INSERT INTO company_info (company_name,company_slogan,number_of_companies,financing_scale,city,area,company_industry,company_logo,company_profile,company_tag,is_approve,is_frozen,gmt_create,gmt_update,create_by,update_by) VALUES(#{record.companyName},#{record.companySlogan},#{record.numberOfCompanies},#{record.financingScale},#{record.city},#{record.area},#{record.companyIndustry}, #{record.companyLogo},#{record.companyProfile},#{companyTag},#{record.isApprove},#{record.isFrozen},#{record.gmtCreate},#{record.gmtUpdate},#{record.createBy}, #{record.updateBy})")
	@Options(useGeneratedKeys = true, keyProperty = "record.companyId", keyColumn = "company_id")/*增加这个注解插入记录后会返回自增长的id*/
	@PostMapping(value = "/company/insert_company_info")
	@PreAuthorize("hasAuthority('CompanyManageAll') AND hasAuthority('CompanyManageAdd') ")
	public RetResult<?> insertCompanyInfo(CompanyInfo record, String produceName, String produceSlogan, String produceLogo, String productIntroduction, String phone, String email, String adress, String map, Long gmtCreate, Long gmtUpdate, String createBy, String updateBy) {
		logger.info("------------输入请求参数------------");
		logger.info("\n" + "record:" + record + "\n" + "produceName:" + produceName +
				"\n" + "produceSlogan:" + produceSlogan + "\n" + "produceLogo:" + produceLogo + "\n" + "productIntroduction:" + productIntroduction + "\n" + "phone:" + phone + "\n" +
				"\n" + "email:" + email + "\n" + "adress:" + adress + "\n" + "map:" + map + "\n" + "gmtCreate:" + gmtCreate + "\n" + "gmtUpdate:" + gmtUpdate + "\n" + "createBy:" + createBy + "\n" + "updateBy:" + updateBy + "\n");
		logger.info("------------------------");


		try {
			int insertCompanyInfo = companyInfoService.insertCompanyInfo(record);
			logger.info("插入公司信息表成功，insertCompanyInfo=" + insertCompanyInfo);
			Long companyId = record.getCompanyId();

			logger.info("插入公司id为：" + companyId);
			int insertCompanyProduce = companyInfoService.insertCompanyProduce(companyId, produceName, produceSlogan, produceLogo, productIntroduction, gmtCreate, gmtUpdate, createBy, updateBy);
			logger.info("插入公司产品表成功，insertCompanyProduce=" + insertCompanyProduce);

			int insertRecruitingCompanyInformation = companyInfoService.insertRecruitingCompanyInformation(companyId, phone, email, adress, map, gmtCreate, gmtUpdate, createBy, updateBy);
			logger.info("插入招聘公司信息表成功，insertRecruitingCompanyInformation=" + insertRecruitingCompanyInformation);


			return RetResponse.result(RetCode.SUCCESS_COMPANY_ADD);
		} catch (RuntimeMBeanException e) {
			logger.info(e.toString());
			return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);
		}
	}

	// TODO: 2018/11/9  更新公司信息接口
	@Transactional
	@PutMapping(value = "/company/update_company_info")
	@PreAuthorize("hasAuthority('CompanyManageAll') AND hasAuthority('CompanyManageUpdate') ")
	public RetResult<?> updateCompanyInfo(CompanyInfo record, Long companyId, String phone, String email, String adress, String map, Integer id, String produceName, String produceSlogan, String produceLogo, String productIntroduction, Long gmtUpdate, String updateBy) {
		logger.info("------------输入请求参数------------");
		logger.info("\n" + "record:" + record + "\n" + "produceName:" + produceName +
				"\n" + "produceSlogan:" + produceSlogan + "\n" + "produceLogo:" + produceLogo + "\n" + "productIntroduction:" + productIntroduction + "\n" + "phone:" + phone + "\n" +
				"\n" + "email:" + email + "\n" + "adress:" + adress + "\n" + "map:" + map + "\n" + "gmtUpdate:" + gmtUpdate + "\n" + "updateBy:" + updateBy + "\n");
		logger.info("------------------------");
		try {
			int updateCompanyInfo = companyInfoService.updateCompanyById(record);
			logger.info("更新公司信息表成功，updateCompanyInfo=" + updateCompanyInfo);

			int updateCompanyProduce = companyInfoService.updatecompanyProduceByIde(id, produceName, produceSlogan, produceLogo, productIntroduction, gmtUpdate, updateBy);
			logger.info("更新公司产品表成功，updateCompanyProduce=" + updateCompanyProduce);

			int updateRecruitingCompanyInformation = companyInfoService.updateRecruitingCompanyInformationByCompanyId(companyId, phone, email, adress, map, gmtUpdate, updateBy);
			logger.info("更新招聘公司信息表成功，updateRecruitingCompanyInformation=" + updateRecruitingCompanyInformation);


			return RetResponse.result(RetCode.SUCCESS_COMPANY_UPDATE);
		} catch (RuntimeMBeanException e) {
			logger.info(e.toString());
			return RetResponse.result(RetCode.SYSTEM_INNER_ERROR);
		}
	}

	// TODO: 2018/11/9 更改公司认证状态
	@PutMapping(value = "/company/approve/{companyId}")
	@PreAuthorize("hasAuthority('CompanyManageAll') AND hasAuthority('CompanyManageUpdate') ")
	public RetResult<?> updateCompanyApproveStatue(@PathVariable Long companyId, Integer isApprove) {
		logger.info("------------输入请求参数------------");
		logger.info("\n" + "companyId:" + companyId + "\n" + "isApprove:" + isApprove);
		logger.info("------------------------");
		try {
			int updateCompanyApproveStatue = companyInfoService.updateCompanyApproveStatue(companyId, isApprove);
			logger.info("更新招聘公司信息表成功，updateRecruitingCompanyInformation=" + updateCompanyApproveStatue);
			return RetResponse.result(RetCode.SUCCESS_COMPANY_APPROVE_UPDATE);
		} catch (RuntimeMBeanException e) {
			logger.info(e.toString());
			return RetResponse.result(RetCode.SYSTEM_INNER_ERROR);

		}
	}

	// TODO: 2018/11/9 更改公司冻结状态
	@PutMapping(value = "/company/frozen/{companyId}")
	@PreAuthorize("hasAuthority('CompanyManageAll') AND hasAuthority('CompanyManageUpdate') ")
	public RetResult<?> updateCompanyFrozenStatue(@PathVariable Long companyId, Integer isFrozen) {
		logger.info("------------输入请求参数------------");
		logger.info("\n" + "companyId:" + companyId + "\n" + "isFrozen:" + isFrozen);
		logger.info("------------------------");
		try {
			int updateCompanyFrozenStatue = companyInfoService.updateCompanyFrozenStatue(companyId, isFrozen);
			logger.info("更新招聘公司信息表成功，updateRecruitingCompanyInformation=" + updateCompanyFrozenStatue);
			return RetResponse.result(RetCode.SUCCESS_COMPANY_STATUS_UPDATE);
		} catch (RuntimeMBeanException e) {
			return RetResponse.result(RetCode.SYSTEM_INNER_ERROR);

		}
	}

	// TODO: 2018/11/10 删除公司信息接口
	@Transactional
	@DeleteMapping(value = "/delete/company_info")
	@PreAuthorize("hasAuthority('CompanyManageAll') AND hasAuthority('CompanyManageDelete') ")
	public RetResult<?> deleteCompanyInfoByCompanyId(Long companyId) {
		logger.info("------------输入请求参数------------");
		logger.info("\n" + "companyId:" + companyId + "\n");
		logger.info("------------------------");
		int a = companyInfoService.queryPositionOnlineCount(companyId);
		if (a == 0) {
			try {
				int deleteCompanyInfo = companyInfoService.deleteCompanyInfoByCompanyId(companyId);
				logger.info("删除公司信息表成功，deleteCompanyInfo=" + deleteCompanyInfo);

				int deleteCompanyProduce = companyInfoService.deleteCompanyProduceByCompanyId(companyId);
				logger.info("删除公司产品表成功，deleteCompanyProduce=" + deleteCompanyProduce);

				int deleteRecruitingCompanyInformation = companyInfoService.deleteRecruitingCompanyInformationByCompanyId(companyId);
				logger.info("删除招聘公司信息表成功，deleteRecruitingCompanyInformation=" + deleteRecruitingCompanyInformation);

				int deletePositionInfoByCompanyId = companyInfoService.deletePositionInfoByCompanyId(companyId);
				logger.info("删除职位信息表成功，deletePositionInfoByCompanyId=" + deletePositionInfoByCompanyId);

				return RetResponse.result(RetCode.SUCCESS_COMPANY_DELETE);
			} catch (RuntimeMBeanException e) {
				logger.info(e.toString());
				return RetResponse.result(RetCode.SYSTEM_INNER_ERROR);

			}
		} else {
			return RetResponse.result(RetCode.SYSTEM_INNER_ERROR);

		}
	}



}
