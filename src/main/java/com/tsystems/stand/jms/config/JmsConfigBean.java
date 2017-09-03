package com.tsystems.stand.jms.config;


import com.tsystems.stand.ejb.TopProductsBean;
import com.tsystems.stand.jms.listener.Receiver;

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

@Singleton
@Startup
public class JmsConfigBean {

    private QueueConnection connection;
    private QueueSession session;
    private QueueReceiver receiver;

    @EJB
    TopProductsBean topProductsBean;

    @PostConstruct
    private void startConnection() throws NamingException, JMSException {
        Properties props = new Properties();
//        Hashtable<String, String> props = new Hashtable<String, String>();
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
        Receiver r = new Receiver();
        r.setTopProductsBean(topProductsBean);
        receiver.setMessageListener(r);
    }

    public TopProductsBean getTopProductsBean() {
        return topProductsBean;
    }

    @PreDestroy
    private void closeConnection() {
        try {
            receiver.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
