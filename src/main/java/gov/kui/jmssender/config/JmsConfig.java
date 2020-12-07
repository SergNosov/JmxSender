package gov.kui.jmssender.config;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {
    private final String brokerUrl;
    private final String user;
    private final String password;

    public JmsConfig(@Value("${artemis.broker-url}") String brokerUrl,
                     @Value("${artemis.user}")String user,
                     @Value("${artemis.password}") String password) {
        this.brokerUrl = brokerUrl;
        this.user = user;
        this.password = password;
    }

    @Bean
    public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
        return new ActiveMQConnectionFactory(brokerUrl,user,password);
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(
                senderActiveMQConnectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory,
                                   MessageConverter messageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
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
}
