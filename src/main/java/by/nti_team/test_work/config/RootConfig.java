package by.nti_team.test_work.config;

import by.nti_team.test_work.storage.api.ILordRepository;
import by.nti_team.test_work.storage.api.IPlanetRepository;
import by.nti_team.test_work.view.LordView;
import by.nti_team.test_work.view.PlanetView;
import by.nti_team.test_work.view.api.ILordView;
import by.nti_team.test_work.view.api.IPlanetView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("by.nti_team.test_work.config")
public class RootConfig {

    @Bean
    ILordView lordView(ILordRepository repository) {
        return new LordView(repository);
    }

    @Bean
    IPlanetView planetView(IPlanetRepository repository) {
      return new PlanetView(repository);
    }

}
