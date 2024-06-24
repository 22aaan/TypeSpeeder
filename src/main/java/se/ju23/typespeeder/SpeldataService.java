package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SpeldataService {

    @Autowired
    private SpeldataRepository speldataRepository;

    public SpeldataService(SpeldataRepository speldataRepository) {
        this.speldataRepository = speldataRepository;
    }

    public void avslutaSpel(Anvandare anvandare, long tid, boolean isCorrect, int rattaSvar, String svarIOrdning) {
        Speldata nySpelData = new Speldata();
        nySpelData.setAnvandare(anvandare);
        nySpelData.setTid(tid);
        nySpelData.setIsCorrect(isCorrect);
        nySpelData.setRattaSvar(rattaSvar);
        nySpelData.setSvarIOrdning(svarIOrdning);

        int fleraRattIRad = isCorrect ? hittaSenasteFleraRattForAnvandare(anvandare) + 1 : 0;
        nySpelData.setFleraRatt(fleraRattIRad);

        if (isCorrect || fleraRattIRad == 0) {
            speldataRepository.save(nySpelData);
        }
    }


    public int hittaSenasteFleraRattForAnvandare(Anvandare anvandare) {
        return speldataRepository.findTopByAnvandareAndIsCorrectOrderBySpelDataIDDesc(anvandare, true)
                .map(Speldata::getFleraRatt)
                .orElse(0);
    }

    public List<Speldata> getSpeedRanking() {
        return speldataRepository.findAllByOrderByTidAsc();
    }

    public List<Speldata> getCorrectAnswersRanking() {
        return speldataRepository.findAllByOrderByRattaSvarDesc();
    }

    public List<Spelresultat> getRankingByFleraRatt() {
        List<Speldata> speldataList = speldataRepository.findAll();
        Map<String, Integer> highestScores = new HashMap<>();

        for (Speldata speldata : speldataList) {
            String anvandarnamn = speldata.getAnvandare().getAnvandarnamn();
            highestScores.compute(anvandarnamn, (key, val) -> {
                if (val == null || speldata.getFleraRatt() > val) {
                    return speldata.getFleraRatt();
                } else {
                    return val;
                }
            });
        }

        return highestScores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> new Spelresultat(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

    }
}