package gov.kui.jmssender.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.transaction.HazelcastXAResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class HazelcastConfiguration {

    private final String hzClusterName;
    private final String hzInstanceName;
    private final String hzIMapName;

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.setClusterName(hzClusterName)
                .setInstanceName(hzInstanceName)
                .addMapConfig(new MapConfig().setName(hzIMapName));
        return config;
    }

    @Bean
    public HazelcastXAResource hazelcastXAResource(Config hazelcastConfig) {
        return Hazelcast
                .newHazelcastInstance(hazelcastConfig)
                .getXAResource();
    }

    public HazelcastConfiguration(@Value("${jmsSender.hazalcast.clusterName}") String hzClusterName,
                                  @Value("${jmssender.hazalcast.instanceName}") String hzInstanceName,
                                  @Value("${jmssender.hazalcast.IMapName}") String hzIMapName) {
        this.hzClusterName = hzClusterName;
        this.hzInstanceName = hzInstanceName;
        this.hzIMapName = hzIMapName;
    }
}
