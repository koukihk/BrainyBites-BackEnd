package com.brainybites.demo.controller;

import com.brainybites.demo.bean.Customer;
import com.brainybites.demo.model.ArtFullMod;
import com.brainybites.demo.service.SearchService;
import com.brainybites.demo.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;
    @Autowired
    ShapeService shapeService;
    @Autowired
    HttpSession session;

    @RequestMapping("/simple")
    public List<ArtFullMod> searchContentSimple(
            @RequestParam String key, @RequestParam Integer page, @RequestParam Integer pageSize) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return null;
        }
        return searchService.searchContentSimple(key, page, pageSize);
    }
}
