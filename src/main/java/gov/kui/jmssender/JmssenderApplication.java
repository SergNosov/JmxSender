package gov.kui.jmssender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JmssenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmssenderApplication.class, args);
    }

}
