package com.brainybites.demo.service;

import com.brainybites.demo.bean.Customer;

import java.util.List;

public interface SessionService {

    void setCusSession(Customer cus);

    void setRelSession(List<Integer> rel);

    Customer getCusSession();

    List<Integer> getRelSession();

    Integer getPagThenAddOne(String category);

    Integer getSetPagAfterCusChange(String category);

    void initPagSession();

}
