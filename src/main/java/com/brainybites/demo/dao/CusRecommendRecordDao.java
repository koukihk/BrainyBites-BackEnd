package com.brainybites.demo.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CusRecommendRecordDao {

    /**
     * 记录系统为用户推荐的内容
     * 23-02-03 创建方法
     * @param cusId
     * @param list
     * @return
     */
    Integer addRecommendList(Integer cusId, List<Integer> list);

    /**
     * 删除历史推荐记录
     * 23-02-03 创建方法
     * @param cusId
     */
    void deleteOldCommentList(Integer cusId);
}
