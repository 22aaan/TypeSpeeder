package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdminServiceImpl implements AdminService {
    private final String adminPassword = "kaka22";

    @Autowired
    private NewsLetterRepository newsLetterRepository;

    @Autowired
    private PatchRepository patchRepository;

    @Override
    public boolean authenticateAdmin(String password) {
        return adminPassword.equals(password);
    }

    @Override
    public void addNewsLetter(String title, String content) {
        NewsLetter newsLetter = new NewsLetter();
        newsLetter.setTitel(title);
        newsLetter.setBeskrivning(content);
        newsLetter.setPublishDateTime(LocalDateTime.now());
        newsLetterRepository.save(newsLetter);
        System.out.println("Nyhetsinformation tillagd.");
    }
    @Override
    public List<String> getNewsletters() {
        return newsLetterRepository.findAll().stream()
                .map(news -> news.getTitel() + ": " + news.getBeskrivning())
                .collect(Collectors.toList());
    }
    @Override
    public void addPatch(String patchVersion, LocalDateTime releaseDateTime) {
        Patch patch = new Patch(patchVersion, releaseDateTime);
        patchRepository.save(patch);
        System.out.println("Patch-information tillagd med version " + patchVersion + " och datum " + releaseDateTime);
    }
    @Override
    public List<Patch> getPatches() {
        return patchRepository.findAll();
    }
}