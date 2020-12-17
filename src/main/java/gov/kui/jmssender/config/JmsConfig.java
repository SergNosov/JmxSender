package gov.kui.jmssender.config;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosConnectionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.Destination;

@Configuration
public class JmsConfig {
    private final String brokerUrl;
    private final String user;
    private final String password;
    private final String destinationQueue;

    public JmsConfig(@Value("${artemis.broker-url}") String brokerUrl,
                     @Value("${artemis.user}")String user,
                     @Value("${artemis.password}") String password,
                     @Value("${jms.queue.destination}") String destinationQueue) {
        this.brokerUrl = brokerUrl;
        this.user = user;
        this.password = password;
        this.destinationQueue=destinationQueue;
    }

    @Bean
    public ActiveMQXAConnectionFactory senderActiveMQXAConnectionFactory(){
        return new ActiveMQXAConnectionFactory(brokerUrl,user,password);
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public AtomikosConnectionFactoryBean  atomikosConnectionFactoryBean(ActiveMQXAConnectionFactory senderActiveMQXAConnectionFactory){
        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setXaConnectionFactory(senderActiveMQXAConnectionFactory);
        atomikosConnectionFactoryBean.setUniqueResourceName("SenderJMS_"+destinationQueue);

        return atomikosConnectionFactoryBean;
    }

    @Bean
    public JmsTemplate jmsTemplate(AtomikosConnectionFactoryBean  atomikosConnectionFactoryBean,
                                   MessageConverter messageConverter,
                                   Destination queue) {
        JmsTemplate jmsTemplate = new JmsTemplate(atomikosConnectionFactoryBean);
        jmsTemplate.setDefaultDestination(queue);
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }

    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public ActiveMQQueue queue(){
        ActiveMQQueue queue = new ActiveMQQueue(destinationQueue);
        return  queue;
    }
}
