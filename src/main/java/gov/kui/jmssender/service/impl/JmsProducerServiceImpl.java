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

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class JmsProducerServiceImpl implements JmsProducerService {
    private final JmsTemplate jmsTemplate;
    private final String artemisHost;
    private final String login;
    private final String password;

    @Autowired
    public JmsProducerServiceImpl(JmsTemplate jmsTemplate,
                                  @Value ("${artemis.host}") String artemisHost,
                                  @Value ("${artemis.user}") String login,
                                  @Value ("${artemis.password}") String password
    ) {
        this.jmsTemplate = jmsTemplate;
        this.artemisHost = artemisHost;
        this.login = login;
        this.password = password;
    }

    public void send(final DocumentDto documentDto){
        jmsTemplate.convertAndSend(documentDto);
        log.info("--- sending: "+documentDto);
    }

    public void  isJmsAlive(){
        try {
            final RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    artemisHost+":8161/console/jolokia",
                    HttpMethod.GET,
                    new HttpEntity<String>(setHttpHeaders()),
                    String.class
            );

            log.info("---ActiveMQ Artemis status: " + responseEntity.getStatusCode());
            log.info("---ActiveMQ Artemis request body: " + responseEntity.getBody());
        } catch (ResourceAccessException conEx){
            log.info("--- Нет связи с брокером сообщений ActiveMQ Artemis: " + conEx.getMessage());
            throw new RuntimeException("--- Нет связи с брокером сообщений ActiveMQ Artemis: "+conEx.getMessage());
        }
    }

    private HttpHeaders setHttpHeaders() {
        //С целью решения проблемы с заголовком Origin указать JVM аргумент:
        //-Dsun.net.http.allowRestrictedHeaders=true
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setOrigin(artemisHost);
        httpHeaders.setBasicAuth(login, password);
        return httpHeaders;
    }
}
