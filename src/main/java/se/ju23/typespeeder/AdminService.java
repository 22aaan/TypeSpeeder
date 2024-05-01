package se.ju23.typespeeder;

import java.util.List;

public interface AdminService {
    boolean authenticateAdmin(String password);
    void addNewsLetter(String title, String content);
    List<String> getNewsletters();
    void addPatch(String title, String description);
    List<Patch> getPatches();
}