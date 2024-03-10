package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;
    private ResourceBundle messages;

    @Override
    public void run(String... args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nVälkommen till TypeSpeeder!");
                System.out.println("1. Registrera ny användare");
                System.out.println("2. Logga in");
                System.out.println("3. Avsluta");
                System.out.print("Välj ett alternativ: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        registerUser(scanner);
                        break;
                    case "2":
                        if (loginUser(scanner)) {
                            selectLanguage(scanner);
                            showGameMenu(scanner);
                        }
                        break;
                    case "3":
                        System.out.println("Avslutar programmet...");
                        return;
                    default:
                        System.out.println("Ogiltigt val, försök igen.");
                        break;
                }
            }
        }
    }

    private void registerUser(Scanner scanner) {
        System.out.println("Ange användarnamn:");
        String username = scanner.nextLine();
        System.out.println("Ange lösenord:");
        String password = scanner.nextLine();
        System.out.println("Ange spelnamn:");
        String gameName = scanner.nextLine();

        boolean success = userService.registerUser(username, password, gameName);
        if (success) {
            System.out.println("Användare registrerad!");
        } else {
            System.out.println("Registrering misslyckades, användarnamnet kanske redan är taget.");
        }
    }

    private boolean loginUser(Scanner scanner) {
        System.out.print("Ange användarnamn: ");
        String username = scanner.nextLine();
        System.out.print("Ange lösenord: ");
        String password = scanner.nextLine();

        boolean success = userService.loginUser(username, password);
        if (success) {
            System.out.println("Inloggningen lyckades!");
            return true;
        } else {
            System.out.println("Inloggningen misslyckades, kontrollera dina uppgifter.");
            return false;
        }
    }

    private void selectLanguage(Scanner scanner) {
        System.out.println("Välj språk: 1. Svenska, 2. Engelska");
        String languageChoice = scanner.nextLine();
        Locale locale;
        switch (languageChoice) {
            case "1":
                locale = new Locale("sv", "SE");
                break;
            case "2":
                locale = new Locale("en", "US");
                break;
            default:
                System.out.println("Ogiltigt val, standardinställning Svenska används.");
                locale = new Locale("sv", "SE");
        }
        messages = ResourceBundle.getBundle("MessagesBundle", locale);
    }

    private void showGameMenu(Scanner scanner) {
        boolean stayInMenu = true;
        while (stayInMenu) {
            System.out.println("\n" + messages.getString("welcomeGame"));
            System.out.println("1. " + messages.getString("startChallenge"));
            System.out.println("2. " + messages.getString("showScore"));
            System.out.println("3. " + messages.getString("logout"));
            System.out.print(messages.getString("chooseOption"));
            String val = scanner.nextLine();

            switch (val) {
                case "1":
                    WordChallenge wordChallenge = new WordChallenge(messages);
                    wordChallenge.startChallenge();
                    break;
                case "2":
                    // Implementera visning av poäng här
                    System.out.println("Feature not implemented yet.");
                    break;
                case "3":
                    System.out.println(messages.getString("loggingOut"));
                    stayInMenu = false;
                    break;
                default:
                    System.out.println(messages.getString("invalidChoice"));
                    break;
            }
}
    }
}


