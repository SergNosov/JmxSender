package gov.kui.jmssender.service.impl;

import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.service.JmsProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.JMSException;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class JmsProducerServiceImpl implements JmsProducerService {
    private final JmsTemplate jmsTemplate;
    private final String destinationQueue;

    @Autowired
    public JmsProducerServiceImpl(JmsTemplate jmsTemplate, @Value("${jms.queue.destination}") String destinationQueue) {
        this.jmsTemplate = jmsTemplate;
        this.destinationQueue = destinationQueue;
    }

    public void send(DocumentDto documentDto){
        jmsTemplate.convertAndSend(destinationQueue, documentDto);
        log.info("--- sending: "+documentDto);
    }

    public void  isJmsAlive(){
        try (Connection con = jmsTemplate.getConnectionFactory().createConnection()){
            log.info("--- con: "+con.getMetaData().getJMSProviderName());
        } catch (JMSException jmsEx) {
            log.error("--- jmsEx: "+jmsEx);
            throw new RuntimeException(jmsEx);
        }
    }
}
