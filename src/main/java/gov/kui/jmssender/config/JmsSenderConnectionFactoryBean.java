package gov.kui.jmssender.config;

import gov.kui.jmssender.util.JmsSenderUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosConnectionFactoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JmsSenderConnectionFactoryBean extends AtomikosConnectionFactoryBean {

    private final ActiveMQXAConnectionFactory senderActiveMQXAConnectionFactory;
    private final String destinationQueue;
    private final String artemisHostJolokia;
    private final String login;
    private final String password;

    @Autowired
    public JmsSenderConnectionFactoryBean(ActiveMQXAConnectionFactory senderActiveMQXAConnectionFactory,
                                          @Value("${jms.queue.destination}") String destinationQueue,
                                          @Value("${artemis.host.jolokia}") String artemisHostJolokia,
                                          @Value("${artemis.user}") String login,
                                          @Value("${artemis.password}") String password) {
        this.senderActiveMQXAConnectionFactory = senderActiveMQXAConnectionFactory;
        this.destinationQueue = destinationQueue;
        this.artemisHostJolokia = artemisHostJolokia;
        this.login = login;
        this.password = password;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setXaConnectionFactory(this.senderActiveMQXAConnectionFactory);
        this.setUniqueResourceName("SenderJMS_SCFB_" + destinationQueue);

        isJmsAlive();

        super.afterPropertiesSet();
    }

    @Override
    public void destroy() throws Exception {
        super.destroy();
    }

    private void isJmsAlive() {
        final ResponseEntity<String> response = JmsSenderUtils.IsJmsArtemisAvailable(
                this.artemisHostJolokia,
                this.login,
                this.password
        );
        if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            log.error("--- JMS Брокер сообщений ActiveMQ Artemis недоступен. " + "\n" +
                    "--- HttpStatus: " + response.getStatusCode() + "\n" +
                    "--- ResponseBody: " + response.getBody()+"\n");
            System.exit(-1000);
        } else {
            log.info("--- JMS Брокер ActiveMQ Artemis. Проверка соединения: " + "\n" +
                    "--- HttpStatus: " + response.getStatusCode() + "\n" +
                    "--- ResponseBody: " + response.getBody()+"\n");
        }
    }
}