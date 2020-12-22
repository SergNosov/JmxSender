package gov.kui.jmssender.service.impl;

import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.service.JmsProducerService;
import gov.kui.jmssender.util.JmsSenderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class JmsProducerServiceImpl implements JmsProducerService {
    private final JmsTemplate jmsTemplate;
    private final String artemisHostJolokia;
    private final String login;
    private final String password;

    @Autowired
    public JmsProducerServiceImpl(JmsTemplate jmsTemplate,
                                  @Value("${artemis.host.jolokia}") String artemisHostJolokia,
                                  @Value("${artemis.user}") String login,
                                  @Value("${artemis.password}") String password
    ) {
        this.jmsTemplate = jmsTemplate;
        this.artemisHostJolokia = artemisHostJolokia;
        this.login = login;
        this.password = password;
    }

    public void send(final DocumentDto documentDto) {
        jmsTemplate.convertAndSend(documentDto);
        log.info("--- sending: " + documentDto);
    }

    public void isJmsAlive() {
        final ResponseEntity<String> response = JmsSenderUtils.IsJmsArtemisAvailable(
                this.artemisHostJolokia,
                this.login,
                this.password
        );

        if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            log.error("--- JMS Брокер сообщений ActiveMQ Artemis недоступен. " + "\n" +
                    "--- HttpStatus: " + response.getStatusCode() + "\n" +
                    "--- ResponseBody: " + response.getBody() + "\n");
            throw new RuntimeException("--- Нет связи с брокером сообщений ActiveMQ Artemis: " + response.getBody());
        } else {
            log.info("--- JMS Брокер ActiveMQ Artemis. Проверка соединения: " + "\n" +
                    "--- HttpStatus: " + response.getStatusCode() + "\n" +
                    "--- ResponseBody: " + response.getBody() + "\n");
        }
    }
}
