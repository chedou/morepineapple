package com.jnshu.resourceservice.web;

import com.github.pagehelper.*;
import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.service.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController

public class BannerShowController {

    @Autowired
    private BannerShowService bannerShowService;
    //日志打印
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    // // TODO: 2018/11/6 首页banner图
    // @RequestMapping(value = "/banner_list", method = RequestMethod.GET)
    // @PreAuthorize("hasAuthority('ArticleManageAll')")
    // public RetResult<?> getBannerShowBytype(@RequestParam(value = "type", required = false, defaultValue = "1") Integer type,
    //                                         @RequestParam(value = "size", required = false, defaultValue = "4") Integer size) {
    //     HashMap data = new HashMap();
    //     List list = new ArrayList();
    //     logger.info("------------输入请求参数------------");
    //     logger.info("type:" + type + "\n" + "size:" + size + "\n");
    //     logger.info("------------------------");
    //     try {
    //         list.addAll(bannerShowService.selectBannerShowByType(type, size));
    //         data.put("getBannerShowBytype", list);
    //         logger.info("Banner图接口查询出来的结果为：" + list.toString());
    //         return RetResponse.result(RetCode.SUCCESS_BANNER_SHOW_LIST_GET).setData(data);
    //     } catch (RuntimeException e) {
    //         logger.info(e.toString());
    //         return RetResponse.result(RetCode.COMPANY_PARAM_ERROR);
    //     }
    // }

    // TODO: 2018/11/10 获取bannei图列表
    @PostMapping("/banner")
    @PreAuthorize("hasAuthority('ArticleManageAll')")
    public RetResult<?> getBannerList(String tilesName, Integer isOnline, Integer type, String createBy, Long createAtStart, Long createAtEnd,
                                @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        logger.info("------------输入请求参数------------");
        logger.info("tilesName:" + tilesName + "\n" + "isOnline:" + isOnline +
                "\n" + "type:" + type + "\n" + "createBy:" + createBy + "\n" +
                "createAtStart:" + createAtStart + "\n" + "createAtEnd:" + createAtEnd +
                "\n" + "page:" + page + "\n" + "size:" + size + "\n");
        logger.info("------------------------");
        HashMap data = new HashMap();
        try {
            PageHelper.startPage(page, size);
            List<BannerShow> bannerShowList = bannerShowService.bannerList(tilesName, isOnline, type, createBy, createAtStart, createAtEnd);
            PageInfo<BannerShow> pageInfo = new PageInfo<BannerShow>(bannerShowList);
            data.put("total", pageInfo.getTotal());
            data.put("bannerShowList", pageInfo.getList());
            logger.info("查询返回的结果为：" + pageInfo.getList());
            return RetResponse.result(RetCode.SUCCESS_BANNER_SHOW_LIST_GET).setData(data);
        } catch (RuntimeException e) {
            logger.info(e.toString());
            return RetResponse.result(RetCode.COMPANY_PARAM_ERROR).setData(data);

        }
    }

    // TODO: 2018/11/10 新增banner图
    @PostMapping(value = "/banner/insert")
    @PreAuthorize("hasAuthority('ArticleManageAll') AND hasAuthority('ArticleManageAdd') ")
    public RetResult<?> insertPositionInfo(BannerShow record) {
        logger.info("-----------输入请求参数---------");
        logger.info("\n" + "record:" + record + "\n");
        logger.info("--------------------------------");
        try {
            Integer i = bannerShowService.insertBannerInfo(record);
            return RetResponse.result(RetCode.SUCCESS_BANNER_SHOW_ONE_ADD).setData(i);
        } catch (RuntimeException e) {
            e.toString();
            return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO: 2018/11/10 更新banner图上线状态
    @PutMapping(value = "/banner/isOnline/{id}")
    @PreAuthorize("hasAuthority('ArticleManageAll') AND hasAuthority('ArticleManageUpdate')")
    public RetResult<?> updateBannerIsOnline(@PathVariable Long id, Integer isOnline) {
        logger.info("-----------输入请求参数---------");
        logger.info("\n" + "id:" + id + "\n" + "isOnline:" + isOnline + "\n");
        logger.info("--------------------------------");
        Long onlineTime =System.currentTimeMillis();
        try {
            int i = bannerShowService.updateBannerIsOnline(id, isOnline, onlineTime);
            return RetResponse.result(RetCode.SUCCESS_BANNER_SHOW_ONE_ONLINE_UPDATE).setData(i);
        }catch (RuntimeException e){
            logger.info(e.toString());
            return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);

        }
    }

    // TODO: 2018/11/10 更新banner图单个信息
    @PutMapping(value = "/banner/{id}")
    @PreAuthorize("hasAuthority('ArticleManageAll') AND hasAuthority('ArticleManageUpdate') ")
    public RetResult<?> updateBannerInfoById(@PathVariable Long id, BannerShow record) {
        logger.info("-----------输入请求参数---------");
        logger.info("\n" + "id:" + id + "\n" + "record:" + record + "\n");
        logger.info("--------------------------------");
        try {
            int i = bannerShowService.updateBannerInfoById(record);
            return RetResponse.result(RetCode.SUCCESS_BANNER_SHOW_ONE_UPDATE).setData(i);
        }catch (RuntimeException e){
            logger.info(e.toString());
            return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO: 2018/11/10 查询单个banner图信息
    @GetMapping("/banner_info/{id}")
    @PreAuthorize("hasAuthority('ArticleManageAll') ")
    public  RetResult<?> qureyModuleInfoById(@PathVariable Long id){

        HashMap data=new HashMap();
        logger.info("-----------输入请求参数---------");
        logger.info("\n"+"id:"+id+"\n" );
        logger.info("--------------------------------");
        try{
            List list=bannerShowService.qureyOneBannerInfo(id);
            data.put("qureyOneBannerInfo",list);
            return RetResponse.result(RetCode.SUCCESS_BANNER_SHOW_ONE_GET).setData(data);
        }catch (RuntimeException e){
            e.toString();
            return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);

        }
    }

    // TODO: 2018/11/10 删除banner图
    @DeleteMapping(value = "/banner/{id}")
    @PreAuthorize("hasAuthority('ArticleManageAll') AND hasAuthority('ArticleManageDelete') ")
    public RetResult<?> deleteBannerById(@PathVariable Long id) {
        logger.info("-----------输入请求参数---------");
        logger.info("\n" + "id:" + id + "\n");
        logger.info("--------------------------------");
        int statue = bannerShowService.queryBannerStatue(id);
        if (statue == 0) {
            try {
                Integer i = bannerShowService.deleteBannerById(id);
                return RetResponse.result(RetCode.SUCCESS_BANNER_SHOW_ONE_GET).setData(i);
            } catch (RuntimeException e) {
                e.toString();
                return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);
            }
        } else {
            logger.info("存在上线Banner图信息，请先下架该Banner图信息，再进行删除");
            return RetResponse.result(RetCode.INTERNAL_SERVER_ERROR);
        }
    }
}
