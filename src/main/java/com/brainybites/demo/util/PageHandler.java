package com.brainybites.demo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageHandler {

    public static Integer calcuStartNO(Integer page, Integer pageSize) {
        return page * pageSize;
    }

    public static Map<String, Integer> initPageList() {
        Map<String, Integer> pages = new HashMap<>();
        pages.put("news_recommend", 0);
        pages.put("news_all", 0);
        pages.put("news_hot", 0);

        pages.put("news_cope", 0);
        pages.put("news_apple", 0);
        pages.put("news_flash", 0);
        pages.put("news_review", 0);
        pages.put("news_tip", 0);
        pages.put("news_opinion", 0);
        pages.put("news_game", 0);

        pages.put("news_application", 0);
        pages.put("news_company", 0);
        pages.put("news_geography", 0);
        pages.put("news_history", 0);
        pages.put("news_talk", 0);
        pages.put("news_question", 0);

        return pages;
    }

    //public static void hotArtPageCheck(Map<String, Integer> pages) {
    //    if (pages.get("news_hot") > 4) {
    //        pages.replace("news_hot", 0);
    //    }
    //}
    //
    //public static void pageChange(Map<String, Integer> pages, String category, Integer num) {
    //    pages.replace(category, num);
    //}
}
