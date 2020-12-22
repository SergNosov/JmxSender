package gov.kui.jmssender.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class JmsSenderUtils {

    public static ResponseEntity<String> IsJmsArtemisAvailable(String checkingHost, String login, String password){
        try {
            //для решения проблемы с заголовком Origin указать JVM аргумент:
            //-Dsun.net.http.allowRestrictedHeaders=true
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setOrigin(checkingHost);
            httpHeaders.setBasicAuth(login, password);

            return  new RestTemplate().exchange(
                    checkingHost,
                    HttpMethod.GET,
                    new HttpEntity<String>(httpHeaders),
                    String.class
            );
        } catch (Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    //Подавление создание конструктора по умолчанию
    // с целью достижения неинстанцируемости
    // (Дж.Блох Java Эффективное программирование. Глава 2 п. 2.4)
    private JmsSenderUtils(){
        throw new AssertionError("Невозможно создать экземпляр утилитарного класса.");
    }
}
