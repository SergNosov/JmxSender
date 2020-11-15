package gov.kui.jmssender.service.impl;

import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.service.JmsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class JmsProducerImpl implements JmsProducer {
    private final JmsTemplate jmsTemplate;
    private final String destinationQueue;

    @Autowired
    public JmsProducerImpl(JmsTemplate jmsTemplate, @Value("${jms.queue.destination}") String destinationQueue) {
        this.jmsTemplate = jmsTemplate;
        this.destinationQueue = destinationQueue;
    }

    public void send(DocumentDto documentDto){
        jmsTemplate.convertAndSend(destinationQueue, documentDto);
        log.info("--- sending: "+documentDto);
    }
}
