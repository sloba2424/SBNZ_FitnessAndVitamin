package com.sbnz.ibar.config;

import com.sbnz.ibar.repositories.UserRepository;
import com.sbnz.ibar.utils.Utils;
import lombok.AllArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static com.sbnz.ibar.utils.Utils.*;

@Configuration
@EnableTransactionManagement
@EnableAsync
@AllArgsConstructor
public class KieConfig {

    private final UserRepository userRepository;

    @Bean
    public KieContainer kieContainer() {
        KieServices kieService = KieServices.Factory.get();
        KieContainer kieContainer = kieService.newKieContainer(kieService
                .newReleaseId(Utils.KIE_GROUP_ID, Utils.KIE_ARTIFACT_ID, Utils.KIE_VERSION));
        KieScanner kieScanner = kieService.newKieScanner(kieContainer);
        kieScanner.start(Utils.SCAN_INTERVAL);
        return kieContainer;
    }

    @Bean(name = "loginSession")
    public KieSession loginSession() {
        return kieContainer().newKieSession(LOGIN_SESSION);
    }

    @Bean(name = "mealsSession")
    public KieSession mealsSession() {
        KieSession mealsSession = kieContainer().newKieSession(BOOKS_SESSION);
        mealsSession.setGlobal("highRatingPoints", 10L);
        mealsSession.setGlobal("averageRatingPoints", 3L);
        mealsSession.setGlobal("lowRatingPoints", 2L);
        mealsSession.setGlobal("readPoints", 2L);
        mealsSession.setGlobal("readingListPoints", 15L);
        mealsSession.setGlobal("recommendationThreshold", 10L);
        return mealsSession;
    }

    @Bean(name = "ranksSession")
    public KieSession ranksSession() {
        KieSession ranksSession = kieContainer().newKieSession(RANKS_SESSION);
        ranksSession.setGlobal("userRepository", userRepository);
        ranksSession.setGlobal("durationThreshold", 10L);
        return ranksSession;
    }

    @Bean(name = "classifySession")
    public KieSession classifySession() {
        KieSession classifySession = kieContainer().newKieSession(CLASSIFY_SESSION);
        classifySession.setGlobal("userRepository", userRepository);
        classifySession.setGlobal("minAge", 13L);
        classifySession.setGlobal("maxAge", 120L);
        return classifySession;
    }

}
