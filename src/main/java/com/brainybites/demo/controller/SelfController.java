package com.brainybites.demo.controller;

import com.brainybites.demo.bean.Customer;
import com.brainybites.demo.model.CusDynamicMod;
import com.brainybites.demo.model.CusFeatureFullMod;
import com.brainybites.demo.service.ShapeService;
import com.brainybites.demo.service.SelfService;
import com.brainybites.demo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/self")
public class SelfController {

    @Autowired
    SelfService selfService;
    @Autowired
    ShapeService shapeService;
    @Autowired
    SessionService session;

    /**
     * 用户登录
     * 22-12-18 创建方法
     * 23-01-02 添加获取相似用户的逻辑
     * @param cusName
     * @param cusPass
     * @return
     */
    @RequestMapping("/login")
    public String cusLogin(@RequestParam String cusName, @RequestParam String cusPass) {
        if (session.getCusSession() != null) {
            return "您已登录";
        }
        try {
            Customer customer = selfService.checkCusForLogin(cusName, cusPass);
            if (customer != null) {
                customer.setCusPass(null);
                session.setCusSession(customer);
                List<Integer> cusList = selfService.getRelativeCusList(customer.getCusId(), 10);
                session.setRelSession(cusList);
                session.initPagSession();
                return "登录成功";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "登录失败";
    }

    /**
     * 用户退出登录
     * 22-12-24 创建方法
     * @return
     */
    @RequestMapping("/quit")
    public String quitLogin() {
        session.setCusSession(null);
        return "退出成功";
    }

    /**
     * 用户注册
     * 22-12-18 创建方法
     * 22-12-24 逻辑修改, 在注册时清空 session 中的用户
     * @param cusName
     * @param cusPass
     * @return
     */
    @RequestMapping("/register")
    public String cusRegister(@RequestParam String cusName, @RequestParam String cusPass) {
        try {
            session.setCusSession(null);
            return selfService.setNewCus(cusName, cusPass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "注册失败";
    }

    /**
     * 通过 ID 获取用户基本信息
     * 22-12-18 创建方法
     * @param cusId
     * @return
     */
    @RequestMapping("/basic")
    public Customer getCusBasicInfo(@RequestParam Integer cusId) {
        if (session.getCusSession() == null) {
            return null;
        }
        if (cusId == null || cusId <= 0) {
            return session.getCusSession();
        } else {
            return selfService.getCusBasicInfo(cusId);
        }
    }

    /**
     * 修改用户的基本信息
     * 22-12-18 创建方法
     * @param customer
     * @return
     */
    @RequestMapping("/modify")
    public String setCusBasicInfo(@RequestBody Customer customer) {
        if (session.getCusSession() == null) {
            return "修改失败";
        }
        try {
            if (selfService.setCusBasicInfo(customer)) {
                session.setCusSession(customer);
                return "修改成功";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "修改失败";
    }

    /**
     * 处理用户关注与取消关注
     * 22-12-18 创建方法
     * 22-12-26 修改逻辑, 防止用户关注自己
     * 23-01-17 补上用户关注后, 更新后台三个表信息.
     * @param cusId 关注或取消关注的用户的 ID
     * @return
     */
    @RequestMapping("/follow")
    public String setCusFollow(@RequestParam Integer cusId) {
        if (session.getCusSession() == null) {
            return "关注失败";
        }
        Customer cusFrom = session.getCusSession();
        Integer cusIdFrom = cusFrom.getCusId();
        if (cusId.equals(cusIdFrom)) {
            return "不能关注自己";
        }
        if (selfService.setCusFollow(cusIdFrom, cusId)) {
            shapeService.setCusBehaviorCusFollow(cusIdFrom, cusId);
            return "关注成功";
        } else {
            return "关注失败";
        }
    }

    /**
     * 获取用户完成的特征数据
     * 22-12-18 创建方法
     * @param cusId
     * @return
     */
    @RequestMapping("/feature")
    public CusFeatureFullMod getCusFeatureInfo(@RequestParam Integer cusId) {
        return selfService.getCusFeatureInfo(cusId);
    }

    /**
     * 获取指定用户的动态
     * 22-12-24 创建方法
     * @param cusId
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("/dynamic")
    public List<CusDynamicMod> getCusDynamic(
            @RequestParam Integer cusId, @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (session.getCusSession() == null) {
            return null;
        }
        return selfService.getCusDynamic(cusId, page, pageSize);
    }

    /**
     * 检查用户之间是否存在关注关系
     * 22-12-26 创建方法
     * @param cusId
     * @return
     */
    @RequestMapping("/chefollow")
    public Boolean checkCusFollow(@RequestParam Integer cusId) {
        Customer cusFrom = session.getCusSession();
        if (cusFrom == null) {
            return false;
        }
        return selfService.checkCusFollow(cusFrom.getCusId(), cusId);
    }

}
