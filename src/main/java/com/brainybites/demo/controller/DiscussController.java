package com.brainybites.demo.controller;

import com.brainybites.demo.bean.Comment;
import com.brainybites.demo.bean.Customer;
import com.brainybites.demo.bean.Reply;
import com.brainybites.demo.model.ComFullMod;
import com.brainybites.demo.service.ShapeService;
import com.brainybites.demo.service.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/discuss")
public class DiscussController {

    @Autowired
    DiscussService discussService;
    @Autowired
    ShapeService shapeService;
    @Autowired
    HttpSession session;

    /**
     * 获取完整的评论列表
     * 22-12-20 方法创建
     * @param artId
     * @return
     */
    @RequestMapping("/page")
    public List<ComFullMod> getComList(@RequestParam Integer artId) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return null;
        }
        return discussService.getComList(artId);
    }

    /**
     * 添加评论
     * 22-12-20 添加方法
     * 23-01-04 在插入用户行为数据之前, 对 result 的内容进行检查
     * @param comment
     * @return
     */
    @RequestMapping("/com")
    public String addNewCom(@RequestBody Comment comment) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "评论失败";
        }
        String result = discussService.addNewCom(comment);
        if (result.equals("评论成功")) {
            shapeService.setCusBehaviorComEdit(customer.getCusId(), comment.getComArtId(), comment.getComId());
        }
        return result;
    }

    /**
     * 添加回复
     * 22-12-20 添加方法
     * 23-01-04 在插入用户行为数据之前, 对 result 的内容进行检查
     * @param reply
     * @return
     */
    @RequestMapping("/rep")
    public String addNewRep(@RequestBody Reply reply) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "评论失败";
        }
        String result = discussService.addNewRep(reply);
        if (result.equals("评论成功")) {
            shapeService.setCusBehaviorRepEdit(customer.getCusId(), reply.getRepArtId(), reply.getRepId());
        }
        return result;
    }

}
