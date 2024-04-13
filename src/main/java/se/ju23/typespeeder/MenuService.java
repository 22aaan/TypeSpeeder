package se.ju23.typespeeder;

import java.util.List;
import java.util.Scanner;

public interface MenuService {
    List<String> getMenuOptions();
    void displayMenu();
    void addNewsLetterIfAdmin(Scanner scanner);
}
