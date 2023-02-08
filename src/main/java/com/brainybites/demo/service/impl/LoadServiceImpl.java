package com.brainybites.demo.service.impl;

import com.brainybites.demo.dao.ArtDao;
import com.brainybites.demo.dao.ArtFeatureCountDao;
import com.brainybites.demo.dao.CusBehaviorRecordDao;
import com.brainybites.demo.dao.CusFeatureCountDao;
import com.brainybites.demo.model.ArtFullMod;
import com.brainybites.demo.model.CusArtBehaviorMod;
import com.brainybites.demo.util.PageHandler;
import com.brainybites.demo.util.TypeHandler;
import com.brainybites.demo.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoadServiceImpl implements LoadService {

    @Autowired
    ArtDao artDao;
    @Autowired
    CusFeatureCountDao cusFeatureCountDao;
    @Autowired
    ArtFeatureCountDao artFeatureCountDao;
    @Autowired
    CusBehaviorRecordDao cusBehaviorRecordDao;

    @Override
    public List<String> getArtTypesForNew() {
        List<String> result = artDao.getArtTypesOrderByTypeNum();
        return TypeHandler.typeTransAllEnToCh(result);
    }

    @Override
    public List<String> getArtTypesForOld(Integer cusId) {
        List<String> result = artDao.getArtTypesOrderByTypeNum();
        return TypeHandler.typeTransAllEnToCh(result);
    }

    @Override
    public List<ArtFullMod> getTinyArtOnePageByTypeForNew(
            Integer cusId, String artType, Integer page, Integer pageSize) {
        Integer start = PageHandler.calcuStartNO(page, pageSize);
        List<ArtFullMod> resultList = null;
        if (artType.equals("news_global")) {
            resultList = artDao.getTinyInfoArtFromGlobalForNew(cusId, start, 2);
            resultList.addAll(artDao.getTinyHotArtFromGlobalForNew(cusId, start, pageSize-1-2));
            resultList.addAll(artDao.getTinyNewArtFromGlobalForNew(cusId, start, 1));
        } else {
            resultList = artDao.getTinyNewArtByTypeForNew(artType, cusId, start, 1);
            resultList.addAll(artDao.getTinyArtOnePageByTypeNew(artType, cusId, start, pageSize-1));
        }
        for (ArtFullMod result: resultList) {
            result.setArtType(TypeHandler.typeTransSingleEnToCh(result.getArtType()));
        }
        return resultList;
    }

    @Override
    public List<ArtFullMod> getTinyArtOnePageByTypeForOld(
            Integer cusId, List<Integer> cusList, String artType, Integer page, Integer pageSize) {
        Integer start = PageHandler.calcuStartNO(page, pageSize);
        String cusIdListStr = "";
        for (int i = 0; i < cusList.size(); i++) {
            if (i != (cusList.size() - 1)) {
                cusIdListStr += cusList.get(i) + ", ";
            } else {
                cusIdListStr += cusList.get(i);
            }
        }
        List<ArtFullMod> resultList = null;
        if (artType.equals("news_global")) {
            resultList = artDao.getTinyHotArtFromGlobalForNew(cusId, start, 3);
            resultList.addAll(artDao.getTinyArtOnePageFromGlobalOld(cusId, cusIdListStr, start, pageSize-1-3));
            resultList.addAll(artDao.getTinyNewArtFromGlobalForOld(cusId, start, 1));
        } else {
            resultList = artDao.getTinyArtOnePageByTypeOld(artType, cusId, cusIdListStr, start, pageSize-1);
            resultList.addAll(artDao.getTinyNewArtByTypeForOld(artType, cusId, start, 1));
        }
        // 如果相似用户的推荐内容数量不足 10, 则返回长度为 0 的列表.
        if (resultList.size() < 10) {
            return new ArrayList<>();
        } else {
            for (ArtFullMod result: resultList) {
                result.setArtType(TypeHandler.typeTransSingleEnToCh(result.getArtType()));
            }
            return resultList;
        }
    }

    @Override
    public List<ArtFullMod> getHotArtOnePage(Integer page, Integer pageSize) {
        Integer start = PageHandler.calcuStartNO(page, pageSize);
        //if (page <= 4) {
        //    start = PageHandler.calcuStartNO(page, pageSize);
        //}
        return artDao.getHotArtOnePage(start, pageSize);
    }

    @Override
    public ArtFullMod getFullArt(Integer cusId, Integer artId) {
        ArtFullMod artFullMod = artDao.getArtFull(artId);
        artFullMod.setArtType(TypeHandler.typeTransSingleEnToCh(artFullMod.getArtType()));
        CusArtBehaviorMod behaviorMod = new CusArtBehaviorMod();
        behaviorMod.setArtId(artId);
        behaviorMod.setCusId(cusId);
        // 是否阅读过此文章
        if (cusBehaviorRecordDao.countTargetCusArtBehaviorFrom(cusId, artId, 2) >= 1) {
            behaviorMod.setRead(true);
        } else {
            behaviorMod.setRead(false);
        }
        // 是否点赞
        if (cusBehaviorRecordDao.countTargetCusArtBehaviorFrom(cusId, artId, 3) == 1) {
            behaviorMod.setPreference(1);
        } else if (cusBehaviorRecordDao.countTargetCusArtBehaviorFrom(cusId, artId, 4) == 1) {
            behaviorMod.setPreference(-1);
        } else {
            behaviorMod.setPreference(0);
        }
        // 是否是文章作者
        if (cusBehaviorRecordDao.countTargetCusArtBehaviorFrom(cusId, artId, 1) == 1) {
            behaviorMod.setArtAuthor(true);
        } else {
            behaviorMod.setArtAuthor(false);
        }
        // 是否关注了文章作者
        if (cusBehaviorRecordDao.countTargetCusFollowBehavior(cusId, artFullMod.getArtCusId()) == 1) {
            behaviorMod.setFollow(true);
        } else {
            behaviorMod.setRead(false);
        }
        artFullMod.setCusArtBehavior(behaviorMod);
        return artFullMod;
    }

}
