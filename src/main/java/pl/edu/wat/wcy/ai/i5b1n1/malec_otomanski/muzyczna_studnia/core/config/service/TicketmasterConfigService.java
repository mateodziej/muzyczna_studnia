package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.config.service;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.DiscoveryApi;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ticketmaster")
@Getter
@Setter
public class TicketmasterConfigService {

    private String apiKey;

    @Bean
    public DiscoveryApi discoveryApi() {
        return new DiscoveryApi(apiKey);
    }
}
