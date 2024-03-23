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
    private String currentAnvandarnamn;
    private Anvandare currentUser;


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
            currentUser = loggedInUserOptional.get(); // Sätt currentUser till den inloggade användaren
            System.out.println("Inloggningen lyckades!");

            this.currentAnvandarnamn = currentUser.getAnvandarnamn();

            selectLanguage(scanner);
            showGameMenu(scanner, currentUser.getSpelnamn()); // Använd spelnamn från currentUser
        } else {
            System.out.println("Inloggningen misslyckades, kontrollera dina uppgifter.");
        }
    }


    private void selectLanguage(Scanner scanner) {
        System.out.println("1. English\n2. Svenska");
        String choice = scanner.nextLine();
        Locale locale = "1".equals(choice) ? new Locale("en", "US") : new Locale("sv", "SE");
        messages = ResourceBundle.getBundle("MessagesBundle", locale);
    }


    private void showGameMenu(Scanner scanner, String spelnamn) {
        System.out.println(messages.getString("welcomeGame") + " " + spelnamn + "!");
        boolean stayInMenu = true;
        while (stayInMenu) {
            System.out.println(messages.getString("chooseOption"));
            System.out.println("1. " + messages.getString("startWordChallenge"));
            System.out.println("2. " + messages.getString("startCountChallenge"));
            System.out.println("3. " + messages.getString("updateAccount"));
            System.out.println("4. " + messages.getString("showScore"));
            System.out.println("5. " + messages.getString("logout"));
            System.out.print(messages.getString("yourChoice"));
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    startWordChallenge(); // Se till att denna metod använder ResourceBundle för dess texter också
                    break;
                case "2":
                    startCountChallenge(); // Samma här, se till att använda ResourceBundle
                    break;
                case "3":
                    updateAccount(scanner);
                    break;
                case "4":
                    showScore();
                    break;
                case "5":
                    System.out.println(messages.getString("loggingOut"));
                    stayInMenu = false;
                    break;
                default:
                    System.out.println(messages.getString("invalidChoice"));
                    break;
            }
        }
    }
    private void startWordChallenge() {
        if (currentUser != null) {
            WordChallenge wordChallenge = new WordChallenge(messages, currentUser);
            wordChallenge.startChallenge();
            userService.saveUser(currentUser); // Anta att denna metod sparar användaren till databasen
        } else {
            System.out.println("Användaren är inte inloggad.");
        }
    }


    private void startCountChallenge() {
        if (currentUser != null) {
            CountChallenge countChallenge = new CountChallenge(messages, currentUser, userService); // Nu med UserService
            countChallenge.startChallenge();
        } else {
            System.out.println("Användaren är inte inloggad.");
        }
    }


    private void showScore() {
        if (currentUser != null) {
            System.out.println("Your current score is: " + currentUser.getPoang());
        } else {
            System.out.println("No user is currently logged in.");
        }
    }



    private void updateAccount(Scanner scanner) {
        System.out.println("Vad vill du uppdatera?");
        System.out.println("1. Användarnamn");
        System.out.println("2. Spelnamn");
        System.out.println("3. Lösenord");
        String choice = scanner.nextLine();

        // Hämta den aktuella användaren baserat på det aktuella användarnamnet
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
