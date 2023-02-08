package com.brainybites.demo.dao;

import com.brainybites.demo.bean.ArtFeatureCount;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtFeatureCountDao {

    /**
     * 获取文章的特征统计数据
     * 22-12-18 创建方法
     * @param artId
     * @return
     */
    ArtFeatureCount getArtFeatureCountByArtId(Integer artId);

    /**
     * 更新文章指定字段的特征数据
     * 22-12-19 创建方法
     * 23-01-28 补丁, 数据库表缺陷
     * @param artId
     * @param column    特征字段, 保证 afc 前缀, 下划线
     * @param num
     * @return
     */
    Integer updateArtFeature(Integer artId, String column, Integer num);

}
