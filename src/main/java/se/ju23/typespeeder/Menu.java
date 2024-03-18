package se.ju23.typespeeder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu implements MenuService {
    private Scanner scanner = new Scanner(System.in);

    public Menu() {
    }

    @Override
    public List<String> getMenuOptions() {
        // Skapa en lista med menyval
        List<String> options = new ArrayList<>();
        options.add("Starta ny utmaning");
        options.add("Visa tidigare resultat");
        options.add("Ändra användarinställningar");
        options.add("Visa ledartavlan");
        options.add("Avsluta");
        return options;
    }

    @Override
    public void displayMenu() {
        // Visa menyvalen för användaren
        List<String> options = getMenuOptions();
        System.out.println("Välkommen till TypeSpeeder!");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        // Hantera språkval
        System.out.println("Välj språk (svenska/engelska):");
        String languageSelection = scanner.nextLine().trim().toLowerCase();
        if ("svenska".equals(languageSelection)) {
            System.out.println("Svenska valt.");
        } else if ("engelska".equals(languageSelection)) {
            System.out.println("Engelska valt.");
        } else {
            System.out.println("Ogiltigt val. Använder standardinställningar.");
        }
    }
}
