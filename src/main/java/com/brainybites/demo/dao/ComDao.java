package com.brainybites.demo.dao;

import com.brainybites.demo.bean.Comment;
import com.brainybites.demo.model.ComFullMod;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComDao {

    /**
     * 获取评论列表
     * 22-12-20 创建方法
     * @param artId
     * @return
     */
    List<ComFullMod> getComFullList(Integer artId);

    /**
     * 添加评论
     * 22-12-10 创建方法
     * 22-12-24 允许评论后返回自增主键
     * @param comment
     * @return
     */
    Integer addCom(Comment comment);

    /**
     * 获取单一一条评论, 不包括其回复
     * 22-12-24 创建方法
     * @param flag      用来判断是否需要查询, 生效值为 2
     * @param comId
     * @return
     */
    ComFullMod getSingleCom(Integer flag, Integer comId);

}
