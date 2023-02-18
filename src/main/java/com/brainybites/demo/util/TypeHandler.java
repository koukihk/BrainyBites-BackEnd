package com.brainybites.demo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeHandler {

    private static final String[] ch = {"全部", "推荐", "科普", "Apple", "快讯", "评测", "技巧", "观点", "游戏", "应用", "公司", "地理", "历史", "杂谈"};
    private static final String[] en = {"news_all", "news_recommend", "news_cope", "news_apple", "news_flash", "news_review", "news_tip", "news_opinion",
            "news_game", "news_application", "news_company", "news_geography", "news_history", "news_talk"};

    //public static List<String> sortTypeEn(List<Integer> nums) {
    //    Map<String, Integer> dictionary = new HashMap<>();
    //    for (int i = 0; i < nums.size(); i++) {
    //        dictionary.put(en[i+1], nums.get(i));
    //    }
    //
    //}

    //public static List<String> sortTypeCh(List<Integer> nums) {
    //
    //}

    public static List<String> typeTransAllEnToCh(List<String> enTypes) {
        for (int i = 0; i < enTypes.size(); i++) {
            for (int j = 0; j < en.length; j++) {
                if (enTypes.get(i).equals(en[j])) {
                    enTypes.set(i, ch[j]);
                }
            }
        }
        enTypes.add(0, "推荐");
        enTypes.add(1, "全部");
        return enTypes;
    }

    /**
     * 不出意外的话, 这个方法应该用不到.
     *
     * @param chTypes
     * @return
     */
    public static List<String> typeTransAllChToEn(List<String> chTypes) {
        for (int i = 0; i < chTypes.size(); i++) {
            for (int j = 0; j < ch.length; j++) {
                if (chTypes.get(i).equals(ch[j])) {
                    chTypes.set(i, en[j]);
                }
            }
        }
        chTypes.add(0, "news_recommend");
        return chTypes;
    }
    
    public static String typeTransSingleEnToCh(String enType) {
        for (int i = 0; i < en.length; i++) {
            if (enType.equals(en[i])) {
                return ch[i];
            }
        }
        return null;
    }
    
    public static String typeTransSingleChToEn(String chType) {
        for (int i = 0; i < ch.length; i++) {
            if (chType.equals(ch[i])) {
                return en[i];
            }
        }
        return null;
    }
    
}
