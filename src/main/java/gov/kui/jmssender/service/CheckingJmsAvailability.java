package gov.kui.jmssender.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public interface CheckingJmsAvailability {

    public void myPrint();

//    public default ResponseEntity<String> isJmsAlive(String checkingHost, String login,String password){
//        try {
//            //С целью решения проблемы с заголовком Origin указать JVM аргумент:
//            //-Dsun.net.http.allowRestrictedHeaders=true
//            final HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setOrigin(checkingHost);
//            httpHeaders.setBasicAuth(login, password);
//
//            return  new RestTemplate().exchange(
//                    checkingHost,
//                    HttpMethod.GET,
//                    new HttpEntity<String>(httpHeaders),
//                    String.class
//            );
//        } catch (ResourceAccessException conEx){
//            return new ResponseEntity<String>(conEx.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
//        }
//    }
}
