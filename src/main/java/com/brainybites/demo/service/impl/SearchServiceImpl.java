package com.brainybites.demo.service.impl;

import com.brainybites.demo.dao.ArtDao;
import com.brainybites.demo.model.ArtFullMod;
import com.brainybites.demo.service.SearchService;
import com.brainybites.demo.util.PageHandler;
import com.brainybites.demo.util.TypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    ArtDao artDao;

    @Override
    public List<ArtFullMod> searchContentSimple(String key, Integer page, Integer pageSize) {
        Integer start = PageHandler.calcuStartNO(page, pageSize);
        List<ArtFullMod> resultList = artDao.searchContentSimple(key, start, pageSize);
        for (ArtFullMod result: resultList) {
            result.setArtType(TypeHandler.typeTransSingleEnToCh(result.getArtType()));
        }
        return resultList;
    }
}
