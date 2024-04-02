package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SpeldataService {
    @Autowired
    private SpeldataRepository speldataRepository;


    @Autowired
    public SpeldataService(SpeldataRepository speldataRepository) {
        this.speldataRepository = speldataRepository;
    }

    public List<Speldata> getSpeedRanking() {
        return speldataRepository.findByIsCorrectTrueOrderByTidAsc();
    }

    public List<Speldata> getCorrectAnswersRanking() {
        return speldataRepository.findByOrderByRattaSvarDesc();
    }
    public void saveSpeldata(Speldata spelData) {
        System.out.println("Sparar speldata: " + spelData); // Eller använd en logger om så önskas
        speldataRepository.save(spelData);
    }
    // Inom SpeldataService
    public List<Speldata> getSequentialCorrectAnswersRanking() {
        return speldataRepository.findSequentialCorrectAnswersRanking();
    }


    // Andra tjänster...
}
