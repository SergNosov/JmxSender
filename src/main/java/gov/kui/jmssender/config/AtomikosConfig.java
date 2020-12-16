package gov.kui.jmssender.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;

// https://www.atomikos.com/Documentation/SpringIntegration#The_Advanced_Case_As_of_3_3
// https://www.atomikos.com/Documentation/SpringBootIntegration

@Configuration
public class AtomikosConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);

        return userTransactionManager;
    }

    @Bean
    public UserTransactionImp atomikosUserTransaction() throws SystemException {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(300);

        return userTransactionImp;
    }

    @Bean
    public JtaTransactionManager jtaTransactionManager(UserTransactionManager atomikosTransactionManager,
                                                       UserTransactionImp atomikosUserTransaction){

        return new JtaTransactionManager(atomikosUserTransaction, atomikosTransactionManager);
    }
}