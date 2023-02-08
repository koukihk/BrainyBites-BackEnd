package com.brainybites.demo.service;

import com.brainybites.demo.bean.Article;

public interface EditService {

    /**
     * 用户添加新文章
     * 23-01-04 创建方法
     * 23-01-11 方法修改, 添加 artLegal
     * @param article
     * @return
     */
    String addNewArt(Article article);

}
