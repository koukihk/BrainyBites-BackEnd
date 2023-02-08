package com.brainybites.demo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeHandler {

    private static final String[] ch = {"推荐", "前端", "后端", "人工智能", "深度学习", "数据挖掘", "语言学习", "嵌入式","计算机图形学","网站建设"};
    private static final String[] en = {"recommend", "front_end", "back_end", "artificial_intelligence", "deep_learning", "data_mining",
            "lang_learning", "embedded", "cs_graphics", "site_building"};

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
        chTypes.add(0, "recommend");
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
