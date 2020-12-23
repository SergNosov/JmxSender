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

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class JmsProducerServiceImpl implements JmsProducerService {
    private final JmsTemplate jmsTemplate;
    private final String artemisHostJolokia;
    private final String login;
    private final String password;

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

    public void send(final DocumentDto documentDto) {
        jmsTemplate.convertAndSend(documentDto);
        log.info("--- sending: " + documentDto);
    }

    public List<DocumentDto> getAllMessages() {
        final Queue queue = (Queue) jmsTemplate.getDefaultDestination();

        return jmsTemplate.browse(queue, (session, browser) -> {

            List<DocumentDto> documentDtos = new ArrayList<>();
            Enumeration enumeration = browser.getEnumeration();

            while (enumeration.hasMoreElements()) {
                Message msg = (Message) enumeration.nextElement();
                log.info("--- Message from kuiQueue: " + msg);

                Optional<DocumentDto> dtoFromMessage = convertMsgToDocumentDto(msg);

                if (dtoFromMessage.isPresent()) {
                    documentDtos.add(dtoFromMessage.get());
                    log.info("--- documentDto from kuiQueue: " + dtoFromMessage.get());
                }
            }
            return documentDtos;
        });
    }

    private Optional<DocumentDto> convertMsgToDocumentDto(Message msg) {
        try {
            DocumentDto dtoFromMessage = (DocumentDto) jmsTemplate.getMessageConverter().fromMessage(msg);
            return Optional.of(dtoFromMessage);
        } catch (JMSException | ClassCastException ex) {
            log.error("--- не удалось конвертировать сообщение в объект DocumentDto. Exception: " + ex.getMessage());
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}
