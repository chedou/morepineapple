// package com.jnshu.resourceservice.service;
//
// /**
//  * @Description 后台管理-公司信息模块
//  * @author Mr.HUANG
//  * @date 2019/1/14
//  *
//  */
// public interface CompanyInfoService {
//
// 	// TODO: 2018/11/8 后端后台
// 	// TODO: 2018/11/8 搜索公司列表
// 	List<CompanyInfo2> qureyCompanyList(String companyName, Integer companyIndustry, String city,
// 										String area, String produceName, Integer financingScale, Integer isApprove,
// 										Integer isFrozen);
//
// 	// TODO: 2018/11/9 获取单个公司信息
// 	List<CompanyInfo2> getACompanyInfo(Long companyId);
//
// 	// TODO: 2018/11/9 新增公司信息接口
// 	// TODO: 2018/11/9 插入公司信息
// 	int insertCompanyInfo(CompanyInfo record);
// 	// TODO: 2018/11/9 新增公司产品
// 	int insertCompanyProduce(Long companyId,String produceName,String produceSlogan,String produceLogo,String productIntroduction,Long gmtCreate,Long gmtUpdate,String createBy,String updateBy);
// 	// TODO: 2018/11/9 新增公司招聘信息
// 	int insertRecruitingCompanyInformation(Long companyId,String phone,String email,String adress,String map,Long gmtCreate,Long gmtUpdate,String createBy,String updateBy);
//
// 	// TODO: 2018/11/9  更新公司信息接口
// 	// TODO: 2018/11/9 更新公司信息
// 	int updateCompanyById(CompanyInfo record);
// 	// TODO: 2018/11/9 更改产品信息
// 	int updatecompanyProduceByIde(Integer id,String produceName,String produceSlogan,String produceLogo,String productIntroduction,Long gmtUpdate,String updateBy);
// 	// TODO: 2018/11/9 更新招聘公司信息
// 	int updateRecruitingCompanyInformationByCompanyId(Long companyId,String produceName,String produceSlogan,String produceLogo,String productIntroduction,Long gmtUpdate,String updateBy);
//
// 	// TODO: 2018/11/9 修改公司认证状态
// 	int updateCompanyApproveStatue(Long companyId,Integer isApprove);
//
// 	// TODO: 2018/11/9 修改公司冻结状态
// 	int updateCompanyFrozenStatue(Long companyId,Integer isFrozen);
//
// 	// TODO: 2018/11/10 删除公司信息接口
// 	// TODO: 2018/11/9 查询在招职位数
// 	int queryPositionOnlineCount(Long companyId);
// 	// TODO: 2018/11/9 删除公司信息
// 	int deleteCompanyInfoByCompanyId(Long companyId);
// 	// TODO: 2018/11/10 删除公司产品信息
// 	int deleteCompanyProduceByCompanyId(Long companyId);
// 	// TODO: 2018/11/10 删除招聘公司信息
// 	int deleteRecruitingCompanyInformationByCompanyId( Long companyId);
// 	// TODO: 2018/11/10 删除职位信息i
// 	int deletePositionInfoByCompanyId(Long companyId);
// }
