package gov.kui.jmssender.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.ListConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelCastConfig(){
        Config config = new Config();
        config.setInstanceName("hazelcast-instance")
                .addListConfig(new ListConfig().setName("documentDtoList"));
        return config;
    }
}
