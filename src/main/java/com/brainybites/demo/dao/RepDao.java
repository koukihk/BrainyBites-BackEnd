package com.brainybites.demo.dao;

import com.brainybites.demo.bean.Reply;
import com.brainybites.demo.model.RepFullMod;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepDao {

    /**
     * 通过评论 ID 来获取此评论下所有的回复
     * 22-12-19 创建方法
     * 22-12-23 BUG 修复, SQL 逗号问题
     * @param comId
     * @return
     */
    List<RepFullMod> getRepFullList(Integer comId);

    /**
     * 添加回复
     * 22-12-19 创建方法
     * 22-12-24 允许插入后返回自增主键
     * @param reply
     * @return
     */
    Integer addRep(Reply reply);

    /**
     * 获取单一一条回复
     * 22-12-24 创建方法
     * @param flag      用于判断查询是否生效, 生效值位 3
     * @param repId
     * @return
     */
    RepFullMod getSingleRep(Integer flag, Integer repId);

}
