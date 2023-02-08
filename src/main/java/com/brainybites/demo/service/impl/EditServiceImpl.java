package com.brainybites.demo.service.impl;

import com.brainybites.demo.bean.Article;
import com.brainybites.demo.dao.ArtDao;
import com.brainybites.demo.service.EditService;
import com.brainybites.demo.util.TypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditServiceImpl implements EditService {

    @Autowired
    ArtDao artDao;

    @Override
    public String addNewArt(Article article) {
        String type = article.getArtType();
        article.setArtType(TypeHandler.typeTransSingleChToEn(type));
        article.setArtLegal(1);
        if (artDao.addArt(article) == 1) {
            return "文章添加成功";
        } else {
            return "文章添加失败";
        }
    }
}
