package com.tsystems.stand.jms.listener;


import com.tsystems.stand.ejb.TopProductsBean;

import javax.jms.Message;
import javax.jms.MessageListener;


public class Receiver implements MessageListener {

    private TopProductsBean topProductsBean;

    public void onMessage(Message message) {
        topProductsBean.updateTopProducts();
    }

    public void setTopProductsBean(TopProductsBean topProductsBean) {
        this.topProductsBean = topProductsBean;
    }
}
