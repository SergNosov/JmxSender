package gov.kui.jmssender.config;

import com.atomikos.icatch.jta.UserTransactionManager;

import com.hazelcast.collection.IList;
import com.hazelcast.config.Config;
import com.hazelcast.config.ListConfig;

import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.transaction.HazelcastXAResource;
import gov.kui.jmssender.model.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class HazelcastConfiguration {

    private String hzClusterName;
    private String hzInstanceName;
    private String hzIListName;
    private String hzIMapName;

    @Bean
    public Config hazelcastConfig(){
        Config config = new Config();
        config.setClusterName(hzClusterName)
            .setInstanceName(hzInstanceName)
                .addListConfig(new ListConfig().setName(hzIListName))
                .addMapConfig(new MapConfig().setName(hzIMapName));
        return config;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(Config hazelcastConfig){
        return Hazelcast.newHazelcastInstance(hazelcastConfig);
    }

    @Bean
    public HazelcastXAResource hazelcastXAResource(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance){
        return hazelcastInstance.getXAResource();
    }

    @Bean
    public UserTransactionManager userTransactionManager (){
        return new UserTransactionManager();
    }

    @Bean
    public IList<DocumentDto> hazelcastDocumentDtoList(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance){
        return hazelcastInstance.getList(hzIListName);
    }

    @Bean
    public IMap<String, DocumentDto> hazelcastDocumentDtoMap(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance){
        return hazelcastInstance.getMap(hzIMapName);
    }

    @Autowired
    public void setHzClusterName(@Value("${jmsSender.hazalcast.clusterName}") String hzClusterName) {
        this.hzClusterName = hzClusterName;
    }

    @Autowired
    public void setHzInstanceName(@Value("${jmssender.hazalcast.instanceName}") String hzInstanceName) {
        this.hzInstanceName = hzInstanceName;
    }

    @Autowired
    public void setHzIListName(@Value("${jmssender.hazalcast.IListName}") String hzIListName) {
        this.hzIListName = hzIListName;
    }

    @Autowired
    public void setHzIMapName(@Value("${jmssender.hazalcast.IMapName}") String hzIMapName) {
        this.hzIMapName = hzIMapName;
    }
}
