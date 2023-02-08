package com.brainybites.demo.service;

import com.brainybites.demo.model.ArtFullMod;

import java.util.List;

public interface SearchService {

    /**
     * 简单的搜索 % %
     * 22-12-20 创建方法
     * 23-01-06
     * @param key
     * @param page
     * @param pageSize
     * @return
     */
    List<ArtFullMod> searchContentSimple(String key, Integer page, Integer pageSize);

}
