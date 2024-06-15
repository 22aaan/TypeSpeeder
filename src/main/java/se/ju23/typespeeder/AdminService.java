package se.ju23.typespeeder;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminService {
    boolean authenticateAdmin(String password);
    void addNewsLetter(String title, String content);
    List<String> getNewsletters();
    void addPatch(String patchVersion, LocalDateTime releaseDateTime);
    List<Patch> getPatches();
}