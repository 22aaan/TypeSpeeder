package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;
    private ResourceBundle messages;
    private String currentAnvandarnamn; // Deklarera variabeln här

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
                        loginUser(scanner);
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

    private void loginUser(Scanner scanner) {
        System.out.print("Ange användarnamn: ");
        String username = scanner.nextLine();
        System.out.print("Ange lösenord: ");
        String password = scanner.nextLine();

        Optional<Anvandare> loggedInUserOptional = userService.loginUser(username, password);
        if (loggedInUserOptional.isPresent()) {
            Anvandare loggedInUser = loggedInUserOptional.get();
            // Sätt currentAnvandarnamn till det lyckade inloggningsnamnet
            this.currentAnvandarnamn = loggedInUser.getAnvandarnamn();
            System.out.println("Inloggningen lyckades!");
            selectLanguage(scanner);
            showGameMenu(scanner, loggedInUser.getSpelnamn());
        } else {
            System.out.println("Inloggningen misslyckades, kontrollera dina uppgifter.");
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

    private void showGameMenu(Scanner scanner, String spelnamn) {
        System.out.println("Välkommen " + spelnamn + "!");
        boolean stayInMenu = true;
        while (stayInMenu) {
            System.out.println("\nVälj ett alternativ:");
            System.out.println("1. Starta ordutmaning");
            System.out.println("2. Starta räkneutmaning");
            System.out.println("3. Uppdatera konto");
            System.out.println("4. Visa poäng");
            System.out.println("5. Logga ut");
            System.out.print("Ditt val: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    WordChallenge wordChallenge = new WordChallenge(messages);
                    wordChallenge.startChallenge();
                    break;
                case "2":
                    CountChallenge countChallenge = new CountChallenge(messages);
                    countChallenge.startChallenge();
                    break;
                case "3":
                    updateAccount(scanner, currentAnvandarnamn);

                    break;
                case "4":
                    // Implementera visning av poäng här
                    break;
                case "5":
                    System.out.println("Loggar ut...");
                    stayInMenu = false;
                    break;
                default:
                    System.out.println("Ogiltigt val.");
                    break;
            }
        }
    }

    private void updateAccount(Scanner scanner, String currentAnvandarnamn) {
        System.out.println("Vad vill du uppdatera?");
        System.out.println("1. Användarnamn");
        System.out.println("2. Spelnamn");
        System.out.println("3. Lösenord");
        String choice = scanner.nextLine();

        // Här hämtar vi den aktuella användaren baserat på användarnamnet
        Optional<Anvandare> currentUserOptional = userService.findByAnvandarnamn(currentAnvandarnamn);
        if (currentUserOptional.isPresent()) {
            Anvandare currentUser = currentUserOptional.get();
            Long userId = currentUser.getAnvandarID();

            // Antag att befintliga värden hämtas på något sätt, kanske från en inloggad användar-session.
            String newAnvandarnamn = currentAnvandarnamn;
            String newSpelnamn = ""; // Dessa bör hämtas eller vara tillgängliga för den inloggade användaren
            String newLosenord = "";

            switch (choice) {
                case "1":
                    System.out.print("Ange nytt användarnamn: ");
                    newAnvandarnamn = scanner.nextLine();
                    break;
                case "2":
                    System.out.print("Ange nytt spelnamn: ");
                    newSpelnamn = scanner.nextLine();
                    break;
                case "3":
                    System.out.print("Ange nytt lösenord: ");
                    newLosenord = scanner.nextLine();
                    break;
                default:
                    System.out.println("Ogiltigt val.");
                    return;
            }

            // Använd den uppdaterade informationen för att uppdatera användaren i databasen
            if (userService.updateUser(userId, newAnvandarnamn, newLosenord, newSpelnamn)) {
                System.out.println("Ditt konto har uppdaterats.");
                // Om användarnamnet uppdaterades, bör du antagligen uppdatera `currentAnvandarnamn` för efterföljande operationer
            } else {
                System.out.println("Uppdateringen misslyckades. Användarnamnet kan vara upptaget.");
            }
        } else {
            System.out.println("Användaren finns inte.");
        }
    }


}
