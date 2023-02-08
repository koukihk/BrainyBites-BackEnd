package com.brainybites.demo.service;

import com.brainybites.demo.bean.Comment;
import com.brainybites.demo.bean.Reply;
import com.brainybites.demo.model.ComFullMod;

import java.util.List;

public interface DiscussService {

    /**
     * 获取完整的评论列表
     * 22-12-20 创建方法
     * @param artId
     * @return
     */
    List<ComFullMod> getComList(Integer artId);

    /**
     * 添加评论
     * 22-12-20 创建方法
     * @param comment
     * @return
     */
    String addNewCom(Comment comment);

    /**
     * 添加回复, 包括回复的回复
     * 22-12-20 创建方法
     * @param reply
     * @return
     */
    String addNewRep(Reply reply);

}
