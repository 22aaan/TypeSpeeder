package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;
    private ResourceBundle messages;
    private String currentAnvandarnamn;
    private Anvandare currentUser;
    @Autowired
    private SpeldataService spelDataService;
    @Autowired
    private AdminService adminService;

    @Override
    public void run(String... args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nVälkommen till TypeSpeeder!");
                System.out.println("1. Registrera ny användare");
                System.out.println("2. Logga in");
                System.out.println("3. Lägg till nyhetsinformation (Admin)");
                System.out.println("4. Visa nyheter och patchar");
                System.out.println("5. Avsluta");
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
                        addNewsLetterIfAdmin(scanner, adminService);
                        break;
                    case "4":
                        showNewsAndPatches();
                        break;
                    case "5":
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
            currentUser = loggedInUserOptional.get();
            System.out.println("Inloggningen lyckades!");

            this.currentAnvandarnamn = currentUser.getAnvandarnamn();

            selectLanguage(scanner);
            showGameMenu(scanner, currentUser.getSpelnamn());
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
            System.out.println("5. " + messages.getString("showRankingLists"));
            System.out.println("6. " + messages.getString("logout"));
            System.out.print(messages.getString("yourChoice"));
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    startWordChallenge();
                    break;
                case "2":
                    startCountChallenge();
                    break;
                case "3":
                    updateAccount(scanner);
                    break;
                case "4":
                    showScore();
                    break;
                case "5":
                    showRankingLists(scanner);
                    break;
                case "6":
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
            WordChallenge wordChallenge = new WordChallenge(spelDataService);
            wordChallenge.setupChallenge(messages, currentUser);
            wordChallenge.startChallenge();
        } else {
            System.out.println("Användaren är inte inloggad.");
        }
    }

    private void startCountChallenge() {
        if (currentUser != null) {
            CountChallenge countChallenge = new CountChallenge(spelDataService, userService);
            countChallenge.setupChallenge(messages, currentUser);
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
        System.out.println(messages.getString("updateAccountPrompt"));
        System.out.println("1. " + messages.getString("updateUsername"));
        System.out.println("2. " + messages.getString("updateGameName"));
        System.out.println("3. " + messages.getString("updatePassword"));
        String choice = scanner.nextLine();

        Optional<Anvandare> currentUserOptional = userService.findByAnvandarnamn(currentAnvandarnamn);
        if (currentUserOptional.isPresent()) {
            Anvandare currentUser = currentUserOptional.get();
            Long userId = currentUser.getAnvandarID();

            String newAnvandarnamn = currentAnvandarnamn;
            String newSpelnamn = currentUser.getSpelnamn();
            String newLosenord = currentUser.getLosenord();

            switch (choice) {
                case "1":
                    System.out.print(messages.getString("enterNewUsername"));
                    newAnvandarnamn = scanner.nextLine();
                    break;
                case "2":
                    System.out.print(messages.getString("enterNewGameName"));
                    newSpelnamn = scanner.nextLine();
                    break;
                case "3":
                    System.out.print(messages.getString("enterNewPassword"));
                    newLosenord = scanner.nextLine();
                    break;
                default:
                    System.out.println(messages.getString("invalidChoice"));
                    return;
            }

            if (userService.updateUser(userId, newAnvandarnamn, newLosenord, newSpelnamn)) {
                System.out.println(messages.getString("accountUpdated"));
            } else {
                System.out.println(messages.getString("updateFailed"));
            }
        } else {
            System.out.println(messages.getString("userNotFound"));
        }
    }
    public void showRankingLists(Scanner scanner) {
        System.out.println(messages.getString("chooseRankingList"));
        System.out.println("1. " + messages.getString("speed"));
        System.out.println("2. " + messages.getString("mostCorrectInARow"));
        System.out.println("3. " + messages.getString("totalCorrect"));
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                List<Speldata> speedRanking = spelDataService.getSpeedRanking();
                System.out.println(messages.getString("speedRanking"));
                for (Speldata spelData : speedRanking) {
                    System.out.println(messages.getString("gameName") + spelData.getAnvandare().getSpelnamn() + ", " +
                            messages.getString("time") + spelData.getTid() + " " + messages.getString("seconds"));
                }
                break;
            case "2":
                List<Speldata> correctAnswersRanking = spelDataService.getCorrectAnswersRanking();
                System.out.println(messages.getString("mostCorrectInARowRanking"));
                for (Speldata spelData : correctAnswersRanking) {
                    System.out.println(messages.getString("gameName") + spelData.getAnvandare().getSpelnamn() + ", " +
                            messages.getString("correctAnswers") + spelData.getRattaSvar());
                }
                break;
            case "3":
                List<Spelresultat> ranking = spelDataService.getRankingByFleraRatt();
                System.out.println(messages.getString("totalCorrectRanking"));
                if (ranking.isEmpty()) {
                    System.out.println(messages.getString("noRankingInfo"));
                } else {
                    for (Spelresultat resultat : ranking) {
                        System.out.println(messages.getString("gameName") + resultat.getSpelnamn() + ", " +
                                messages.getString("correctAnswers") + resultat.getResultat());
                    }
                }
                break;
            default:
                System.out.println(messages.getString("invalidChoice"));
                break;
        }
    }
    public void showNewsAndPatches() {
        System.out.println("\nNyheter och Patchar:");

        System.out.println("\nNyheter:");
        List<String> newsletters = adminService.getNewsletters();
        if (newsletters.isEmpty()) {
            System.out.println("Inga nyheter tillgängliga.");
        } else {
            for (String newsItem : newsletters) {
                System.out.println(newsItem);

            }
        }
    }
    private void addNewsLetterIfAdmin(Scanner scanner, AdminService adminService) {
        System.out.print("Ange adminlösenord: ");
        String password = scanner.nextLine();
        if (adminService.authenticateAdmin(password)) {
            System.out.print("Skriv in titel för nyhetsinformation: ");
            String title = scanner.nextLine();
            System.out.print("Skriv in innehåll för nyhetsinformation: ");
            String content = scanner.nextLine();

            adminService.addNewsLetter(title, content);
        } else {
            System.out.println("Felaktigt lösenord. Endast admin kan lägga till nyhetsinformation.");
        }
    }
}