package com.brainybites.demo.controller;

import com.brainybites.demo.bean.Customer;
import com.brainybites.demo.model.ArtFullMod;
import com.brainybites.demo.service.ShapeService;
import com.brainybites.demo.util.TypeHandler;
import com.brainybites.demo.service.LoadService;
import com.brainybites.demo.service.SelfService;
import com.brainybites.demo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/load")
public class LoadController {

    @Autowired
    LoadService loadService;
    @Autowired
    ShapeService shapeService;
    @Autowired
    SelfService selfService;
    @Autowired
    SessionService session;

    /**
     * 获取新闻类别(英文)
     * 22-12-19 创建方法 TODO 老用户的处理逻辑还没完成
     * @return
     */
    @RequestMapping("/type")
    public List<String> getArtTypes() {
        Customer customer = session.getCusSession();
        if (customer == null) {
            return null;
        }
        if (selfService.checkIsNewUser(customer.getCusId())) {
            return loadService.getArtTypesForNew();
        } else {
            return loadService.getArtTypesForOld(customer.getCusId());
        }
    }

    /**
     * 按照类别获取一页文章
     * 22-12-19 创建方法
     * 23-01-02 添加老用户推荐逻辑
     * 23-01-18 修改逻辑, 在新用户切换成老用户时添加计算相似用户的逻辑
     * 23-01-19 推荐逻辑修改, 在相似用户用完之后, 重新计算相似用户
     * 23-01-24 方法修改, 接受 artType 后转换为英文.
     * @param artType
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("/tiny")
    public List<ArtFullMod> getTinyArtOnePageByType(
            @RequestParam String artType, @RequestParam Integer page, @RequestParam Integer pageSize) {
        artType = TypeHandler.typeTransSingleChToEn(artType);
        Customer customer = session.getCusSession();
        if (customer == null) {
            return null;
        }
        List<ArtFullMod> recommendList = null;
        // 新用户推荐
        if (selfService.checkIsNewUser(customer.getCusId())) {
            recommendList = loadService.getTinyArtOnePageByTypeForNew(customer.getCusId(), artType, session.getPagThenAddOne(artType), pageSize);
            shapeService.recordRecommendList(customer.getCusId(), recommendList);
            return recommendList;
        }

        // 老用户推荐
        List<Integer> relativeCusList = session.getRelSession();

        // 当前用户变为老用户, session 中相似用户的记录为空
        if (relativeCusList == null || relativeCusList.size() == 0) {
            relativeCusList = selfService.getRelativeCusList(customer.getCusId(), 10);
            session.setRelSession(relativeCusList);
            session.getSetPagAfterCusChange(artType);
        }
        //获取推荐内容
        recommendList = loadService.getTinyArtOnePageByTypeForOld(
                customer.getCusId(), relativeCusList, artType, session.getPagThenAddOne(artType), pageSize);
        // 如果 getTinyArtOnePageByTypeForOld 方法返回内容长度为 0, 说明本批次相似用户推荐结束. 重新计算相似用户
        // getTinyArtOnePageByTypeForOld 方法返回内容长度为 0, 系统利用新一批相似用户再次尝试推荐
        if (recommendList.size() == 0) {
            if ("news_global".equals(artType)) {
                relativeCusList = selfService.getRelativeCusList(customer.getCusId(), 10);
                session.setRelSession(relativeCusList);
                session.getSetPagAfterCusChange(artType);
                recommendList = loadService.getTinyArtOnePageByTypeForOld(customer.getCusId(), relativeCusList, artType, session.getPagThenAddOne(artType), pageSize);
            }
            // 如果再次推荐的结果内容长度为 0, 切换至新用户推荐
            if (recommendList.size() == 0) {
                if ("news_global".equals(artType)) {
                    session.setRelSession(new ArrayList<>());
                    session.getSetPagAfterCusChange(artType);
                }
                recommendList = loadService.getTinyArtOnePageByTypeForNew(customer.getCusId(), artType, session.getPagThenAddOne(artType), pageSize);
            }
        }
        shapeService.recordRecommendList(customer.getCusId(), recommendList);
        return recommendList;
    }

    /**
     * 提供一页的热点新闻
     * 22-12-19 创建方法
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("/hot")
    public List<ArtFullMod> getHotArtOnePage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Customer customer = session.getCusSession();
        if (customer == null) {
            return null;
        }
        return loadService.getHotArtOnePage(session.getPagThenAddOne("news_hot"), pageSize);
    }

    /**
     * 获取文章的主体内容, 包括文章内容, 文章作者, 文章的特征信息, 当前用户与文章的关系.
     * 22-12-19 创建方法
     * @param artId
     * @return
     */
    @RequestMapping("/main")
    public ArtFullMod getFullArt(@RequestParam Integer artId) {
        Customer customer = session.getCusSession();
        if (customer == null) {
            return null;
        }
        ArtFullMod artFullMod = loadService.getFullArt(customer.getCusId(), artId);
        shapeService.setCusBehaviorArtRead(customer.getCusId(), artId);
        return artFullMod;
    }

    /**
     * 文章点赞/点踩控制, 包括取消
     * 22-12-19 创建方法
     * @param artId
     * @param type  1: 点赞, 2: 点踩, -1: 取消点赞, -2: 取消点踩
     * @return
     */
    @RequestMapping("/prefer")
    public String setArtPreference(@RequestParam Integer artId, @RequestParam Integer type) {
        Customer customer = session.getCusSession();
        if (customer == null) {
            return "操作失败";
        }
        Boolean result = false;
        switch (type){
            case 1:
                result = shapeService.setCusBehaviorArtLike(customer.getCusId(), artId, true);
                break;
            case 2:
                result = shapeService.setCusBehaviorArtDislike(customer.getCusId(), artId, true);
                break;
            case -1:
                result = shapeService.setCusBehaviorArtLike(customer.getCusId(), artId, false);
                break;
            case -2:
                result = shapeService.setCusBehaviorArtDislike(customer.getCusId(), artId, false);
                break;
        }
        if (result) {
            return "操作成功";
        } else {
            return "操作失败";
        }
    }

}
