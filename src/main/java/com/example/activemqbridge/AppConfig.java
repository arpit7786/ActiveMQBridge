package com.example.activemqbridge;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public JmsComponent activemq() {

        ActiveMQConnectionFactory acf = new ActiveMQConnectionFactory();
        acf.setBrokerURL("tcp://localhost:61616");
        acf.setUserName("admin");
        acf.setPassword("admin");

        JmsComponent jms = new JmsComponent();
        jms.setConnectionFactory(acf);
        return jms;
    }

    @Bean
    public JmsComponent wmq() {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(mqQueueConnectionFactory());
        return jmsComponent;
    }

    @Bean
    public MQQueueConnectionFactory mqQueueConnectionFactory() {
        MQQueueConnectionFactory queueConnectionFactory = new MQQueueConnectionFactory();
        queueConnectionFactory.setHostName("localhost");
        try {
            queueConnectionFactory.setTransportType(1);
            queueConnectionFactory.setChannel("AppToQ");
            queueConnectionFactory.setPort(1414);
            queueConnectionFactory.setQueueManager("QM_TEST");
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        return queueConnectionFactory;
    }
}
