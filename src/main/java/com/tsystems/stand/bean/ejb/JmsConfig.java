package com.tsystems.stand.bean.ejb;


import com.tsystems.stand.jms.listener.MessageListenerImpl;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Current class exists to configure JMS connection(ActiveMQ server).
 */
@Singleton
@Startup
public class JmsConfig {

    /**
     * Connection with the ActiveMQ server
     */
    private QueueConnection connection;
    /**
     * ActiveMQ session.
     */
    private QueueSession session;
    /**
     * Receiver object that gets every message from JMS queue. Within Shop application
     * and Stand application the name of this queue is 'advertising.stand'.
     */
    private QueueReceiver receiver;

    /**
     * Injected topProducts singleton bean. It stores list of the top products
     * received from Shop application by https://localhost:8080/advertising/stand URL.
     */
    @EJB
    TopProducts topProducts;

    /**
     * Apache log4j object is used to logging all important info.
     */
    private static final Logger log = Logger.getLogger(JmsConfig.class);


    /**
     * Initializer of current bean.
     */
    @PostConstruct
    private void startConnection() throws NamingException, JMSException {
        Properties props = new Properties();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", "tcp://localhost:61616");
        props.put("queue.js-queue", "advertising.stand");
        props.put("connectionFactoryNames", "queueCF");

        Context context = new InitialContext(props);
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup("queueCF");
        Queue queue = (Queue) context.lookup("js-queue");

        connection = connectionFactory.createQueueConnection();
        connection.start();

        session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

        receiver = session.createReceiver(queue);
        MessageListenerImpl listener = new MessageListenerImpl();
        listener.setTopProducts(topProducts);
        receiver.setMessageListener(listener);

        //log
        log.info("Connection with JMS server has set.");
    }

    /**
     * Method close all connections to prevent memory leaks.
     */
    @PreDestroy
    private void closeConnection() {
        try {
            receiver.close();
            session.close();
            connection.close();
            //log
            log.info("All connections with JMS server was successfully closed.");
        } catch (JMSException e) {
            log.error("Something was wrong during closing connection with JMS server.", e);
        }
    }

}
