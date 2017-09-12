package com.tsystems.stand.jms.listener;


import com.tsystems.stand.bean.ejb.TopProducts;
import org.apache.log4j.Logger;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Message listener. The main goal of this class is to listen
 * messages from JMS server. It is used here {@link com.tsystems.stand.bean.ejb.JmsConfig}
 * as listener object.
 */
public class MessageListenerImpl implements MessageListener {

    /**
     * Apache log4j object is used to logging all important info.
     */
    private static final Logger log = Logger.getLogger(MessageListenerImpl.class);

    /**
     * Injected by JmsConfig bean in order to call update method
     * when message has received.
     */
    private TopProducts topProducts;

    /**
     * Method always listens to the JMS server.
     * @param message
     */
    public void onMessage(Message message) {
        log.info("Message has received from JMS server.");
        topProducts.updateTopProducts();
    }

    /**
     * Method allows us to inject topProducts bean.
     * See {@link TopProducts}
     * @param topProducts exemplar that must be injected.
     */
    public void setTopProducts(TopProducts topProducts) {
        this.topProducts = topProducts;
    }
}
