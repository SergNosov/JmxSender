package gov.kui.jmssender.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosConnectionFactoryBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class JmsSenderConnectionFactoryBean extends AtomikosConnectionFactoryBean{

    private final ActiveMQXAConnectionFactory senderActiveMQXAConnectionFactory;
    private final String destinationQueue;

    @Autowired
    public JmsSenderConnectionFactoryBean(ActiveMQXAConnectionFactory senderActiveMQXAConnectionFactory,
                                          @Value("${jms.queue.destination}")
                                          String destinationQueue) {
        this.destinationQueue = destinationQueue;
        this.senderActiveMQXAConnectionFactory = senderActiveMQXAConnectionFactory;
        this.setXaConnectionFactory(senderActiveMQXAConnectionFactory);
        this.setUniqueResourceName("SenderJMS_SCFB_"+destinationQueue);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("--- jmsSenderConnectionFactoryBean  init("+System.identityHashCode(this)+");");
        isJmsAlive();
        super.afterPropertiesSet();
    }

    @Override
    public void destroy() throws Exception {
        log.info("--- jmsSenderConnectionFactoryBean  destroy("+System.identityHashCode(this)+");");
        super.destroy();
    }

    private void  isJmsAlive(){
        log.info("--- jmsSenderConnectionFactoryBean (с проверкой соединения JMS)");
        try {
            final RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "http://localhost:8161/console/jolokia",
                    HttpMethod.GET,
                    new HttpEntity<String>(setHttpHeaders()),
                    String.class
            );

            log.info("--- ActiveMQ Artemis status: " + responseEntity.getStatusCode());
            log.info("--- ActiveMQ Artemis request body: " + responseEntity.getBody());
        } catch (ResourceAccessException conEx){
            log.error("--- Нет связи с брокером сообщений ActiveMQ Artemis: " + "\n"+conEx.getMessage());
            //System.exit(-1000);
            throw new RuntimeException("--- Нет связи с брокером сообщений. Exception: "+conEx);
        }
    }

    private HttpHeaders setHttpHeaders() {
        //С целью решения проблемы с заголовком Origin указать JVM аргумент:
        //-Dsun.net.http.allowRestrictedHeaders=true
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setOrigin("http://localhost:8161/console/jolokia");
        httpHeaders.setBasicAuth("root", "root");
        return httpHeaders;
    }
}