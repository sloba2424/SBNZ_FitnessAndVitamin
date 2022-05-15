package com.sbnz.ibar.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@AllArgsConstructor
public class KieInit {
    private final RankRepository rankRepository;
    private final ReaderRepository readerRepository;
    private final AgeClassRepository ageClassRepository;
    private final KieService kieService;

    @PostConstruct
    private void initKie() {
        List<Rank> ranks = rankRepository.findAll();
        ranks.forEach(r -> kieService.getRanksSession().insert(r));
        List<AgeClass> ageClasses = ageClassRepository.findAll();
        ageClasses.forEach(ac -> kieService.getClassifySession().insert(ac));
        readerRepository.findAll().forEach(r -> {
            if (r.getUserCategory() == null) {
                kieService.getClassifySession().insert(r);
            }
        });
        kieService.getClassifySession().fireAllRules();
    }
}
