package gov.kui.jmssender.config;

import com.hazelcast.collection.IList;
import com.hazelcast.config.Config;
import com.hazelcast.config.ListConfig;

import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import gov.kui.jmssender.model.DocumentDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelcastConfig(){
        Config config = new Config();
        config.setInstanceName("hazelcast-instance")
                .addListConfig(new ListConfig().setName("documentDtoList"));
      //  config.setManagementCenterConfig(new ManagementCenterConfig().setEnabled(true));
        return config;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(Config hazelcastConfig){
        return Hazelcast.newHazelcastInstance(hazelcastConfig);
    }

    @Bean
    public IList<DocumentDto> hazelcastDocumentDtoList(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance){
        return hazelcastInstance.getList("documentDtoList");
    }
}
