package gov.kui.jmssender.service.impl;

import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.service.JmsProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

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
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders httpHeaders = new HttpHeaders();
         //   httpHeaders.set("origin", "http://localhost");
          //  httpHeaders.setOrigin("http://localhost:8161");
            httpHeaders.setOrigin("http://localhost");
            httpHeaders.setBasicAuth("root", "root");

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "http://localhost:8161/console/jolokia",
                    HttpMethod.GET,
                    new HttpEntity<String>(httpHeaders),
                    String.class);

            log.info("--- headers: " + httpHeaders);
            log.info("--- origin: " + httpHeaders.getOrigin());
            log.info("---status: " + responseEntity.getStatusCode());
            log.info("--- !!!! " + responseEntity.getBody());
        } catch (ResourceAccessException conEx){
            log.info("--- Нет связи с брокером сообщений ActiveMQ Artemis: " + conEx.getMessage());
            throw new RuntimeException("--- Нет связи с брокером сообщений ActiveMQ Artemis.");
        }

//        try (Connection con = jmsTemplate.getConnectionFactory().createConnection()){
//            log.info("--- con: "+con.getMetaData().getJMSProviderName());
//        } catch (JMSException jmsEx) {
//            log.error("--- jmsEx: "+jmsEx);
//            throw new RuntimeException(jmsEx);
//        }
    }
}
