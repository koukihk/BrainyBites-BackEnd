package com.brainybites.demo.service;

import com.brainybites.demo.model.ArtFullMod;

import java.util.List;

public interface LoadService {

    /**
     * 为新用户提供文章类别列表
     * 22-12-18 创建方法
     * @return
     */
    List<String> getArtTypesForNew();

    /**
     * 为老用户提供文章类别列表
     * 22-12-19 创建方法, TODO 功能上和 getArtTypesForNew 没有区别, 需要跟换
     * @param cusId
     * @return
     */
    List<String> getArtTypesForOld(Integer cusId);

    /**
     * 为新用户提供一页指定类别的新闻缩率信息
     * 22-12-19 创建方法
     * 22-12-28 方法修改, 添加用户浏览内容去重
     * 23-01-06 方法修改, 返回时将文章类别从英文翻译成中文
     * 23-01-13 修改文章推荐方式, 核心文章 pageSize-3: 资讯文章 2: 新生文章 1
     * 23-01-24 方法修改, artType 接受英文
     * @param artType
     * @param page
     * @param pageSize
     * @return
     */
    List<ArtFullMod> getTinyArtOnePageByTypeForNew(Integer cusId, String artType, Integer page, Integer pageSize);

    /**
     * 为老用户提供一页指定类别的新闻缩率信息
     * 如果相似用户的内容推荐完毕, 就切换到新用户的推荐逻辑上.
     * 22-12-19 创建方法
     * 22-12-28 方法修改, 添加用户浏览内容去重
     * 23-01-02 添加对老用户的推荐
     * 23-01-06 方法修改, 返回时将文章类别从英文翻译成中文
     * 23-01-13 修改文章推荐方式, 协同推荐文章 pageSize-1: 新生文章 1
     * 23-01-19 方法修改, 当相似用户推荐低于 10 的时候, 返回长度为 0 的列表
     * 23-01-24 方法修改, artType 接受英文
     * @param artType
     * @param cusList   相似用户列表
     * @param page
     * @param pageSize
     * @return
     */
    List<ArtFullMod> getTinyArtOnePageByTypeForOld(
            Integer cusId, List<Integer> cusList, String artType, Integer page, Integer pageSize);


    /**
     * 提供一页的热点新闻的缩略信息
     * 22-12-19 创建方法
     * @param page
     * @param pageSize
     * @return
     */
    List<ArtFullMod> getHotArtOnePage(Integer page, Integer pageSize);

    /**
     * 获取一篇文章的完整信息, 包括文章, 文章作者, 文章特征统计数据, 当前用户与文章的关系
     * 22-12-19 创建方法
     * 23-01-06 修改方法, 获取文章时返回文章的中文类别
     * @param cusId
     * @param artId
     * @return
     */
    ArtFullMod getFullArt(Integer cusId, Integer artId);

}
