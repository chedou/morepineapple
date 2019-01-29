package com.jnshu.resourceservice.web;

import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.service.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ModuleTableController {
    @Autowired
    private ModuleTableService moduleTableService;

    //日志打印
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    // TODO: 2018/11/8 获取模块管理列表
    @GetMapping(value = "/a/manage/module")
    @PreAuthorize("hasAuthority('ModuleManageAll')")
    public RetResult<?> getqureyModuleList(Integer page, Integer size){
        HashMap data=new HashMap();
        List list =new ArrayList();
        // TODO: 2018/11/8 分页功能后续再加
        logger.info("-----------输入请求参数---------");
        logger.info("page:"+page+"\n"+"size:"+size+"\n" );
        logger.info("--------------------------------");
        try{
            list=moduleTableService.qureyModuleList();
            data.put("getqureyModuleList",list);
            return RetResponse.result(RetCode.SUCCESS_MODULE_LIST).setData(data);
        }catch (RuntimeException e){
            logger.info(e.toString());
            return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO: 2018/11/8 新增模块信息
    @PostMapping("/a/manage/module")
    @PreAuthorize("hasAuthority('ModuleManageAll') AND hasAuthority('ModuleManagAdd') ")
    public RetResult<?> insertModuleInfo(String name,String menuld,String url,Integer parentModuleId,String moduleCategory,String createBy,Long gmtCreate){
        logger.info("-----------输入请求参数---------");
        logger.info("\n"+"name:"+name+"\n"+"menuld:"+menuld+"\n"+"url:"+url+"\n"+"parentModuleId:"+parentModuleId+"\n" +"moduleCategory:"+moduleCategory+"\n"+"createBy:"+createBy+"\n"+"gmtCreate:"+gmtCreate+"\n"   );
        logger.info("--------------------------------");
        try{
            Integer i=moduleTableService.insertModuleInfo(name, menuld, url, parentModuleId, moduleCategory, createBy,gmtCreate);
            return RetResponse.result(RetCode.SUCCESS_MODULE_ONE_ADD).setData(i);
        }catch (RuntimeException e){
            e.toString();
            return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO: 2018/11/8 更新模块信息
    @PutMapping("/a/manage/module/{id}")
    @PreAuthorize("hasAuthority('ModuleManageAll') AND hasAuthority('ModuleManageUpdate') ")
    public RetResult<?> updateModuleById(@PathVariable Integer id, String name, String menuld, String url, Integer parentModuleId, String moduleCategory, String updateBy, Long gmtUpdate){
        logger.info("-----------输入请求参数---------");
        logger.info("\n"+"id:"+id+"\n"+"name:"+name+"\n"+"menuld:"+menuld+"\n"+"url:"+url+"\n"+"parentModuleId:"+parentModuleId+"\n" +"moduleCategory:"+moduleCategory+"\n"+"createBy:"+updateBy+"\n"+"gmtCreate:"+gmtUpdate+"\n"   );
        logger.info("--------------------------------");
        try{
            Integer i=moduleTableService.updateModuleById(id,name, menuld, url, parentModuleId, moduleCategory, updateBy,gmtUpdate);
            return RetResponse.result(RetCode.SUCCESS_MODULE_ONE_UPDATE).setData(i);
        }catch (RuntimeException e){
            e.toString();
            return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);
        }

    }

    // TODO: 2018/11/8 删除模块信息
    @DeleteMapping("/a/manage/module/{id}")
    @PreAuthorize("hasAuthority('ModuleManageAll') AND hasAuthority('ModuleManageDelete') ")
    public RetResult<?> deleteModuleById(@PathVariable Integer id){
        logger.info("-----------输入请求参数---------");
        logger.info("\n"+"id:"+id+"\n" );
        logger.info("--------------------------------");
        try{
            Integer i=moduleTableService.deleteModuleById(id);
            return RetResponse.result(RetCode.SUCCESS_MODULE_ONE_DELETE).setData(i);
        }catch (RuntimeException e){
            e.toString();
            return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO: 2018/11/8 查询单个模块信息
    @GetMapping("/a/manage/module/{id}")
    @PreAuthorize("hasAuthority('ModuleManageAll')")
    public RetResult<?> qureyModuleInfoById(@PathVariable Integer id){
        HashMap data=new HashMap();
        logger.info("-----------输入请求参数---------");
        logger.info("\n"+"id:"+id+"\n" );
        logger.info("--------------------------------");
        try{
            List list=moduleTableService.qureyModuleInfoById(id);
            data.put("qureyModuleInfoById",list);
            return RetResponse.result(RetCode.SUCCESS_MODULE_ONE_DELETE).setData(data);
        }catch (RuntimeException e){
            e.toString();
            return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);
        }
    }
}
