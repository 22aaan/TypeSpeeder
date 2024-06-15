package se.ju23.typespeeder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu implements MenuService {
    private Scanner scanner = new Scanner(System.in);
    private AdminService adminService;

    public Menu(AdminService adminService) {
        this.adminService = adminService;
    }

    public Menu() {

    }


    @Override
    public List<String> getMenuOptions() {
        List<String> options = new ArrayList<>();
        options.add("Starta ny utmaning");
        options.add("Visa tidigare resultat");
        options.add("Ändra användarinställningar");
        options.add("Visa ledartavlan");
        options.add("Avsluta");
        options.add("Lägg till nyhetsinformation (endast admin)"); // Nytt alternativ
        return options;
    }
        {
        System.out.println("Välj språk (svenska/engelska):");
            System.out.println("Svenska valt.");
    }
    @Override
    public void displayMenu() {
        List<String> options = getMenuOptions();
        System.out.println("Välkommen till TypeSpeeder!");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    @Override
    public void addNewsLetterIfAdmin(Scanner scanner) {
        System.out.print("Ange adminlösenord: ");
        String password = scanner.nextLine();
        if ("rättLösenord".equals(password)) {
            System.out.print("Skriv in nyhetsinformation: ");
            String news = scanner.nextLine();
            // Här skulle du anropa din adminService för att faktiskt lägga till nyhetsinformation
            System.out.println("Nyhetsinformation tillagd.");
        } else {
            System.out.println("Felaktigt lösenord. Endast admin kan lägga till nyhetsinformation.");
        }

}
}