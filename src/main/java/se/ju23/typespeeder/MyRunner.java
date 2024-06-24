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
            boolean running = true;
            while (running) {
                selectLanguage(scanner); // Lägg till språkval här

                boolean exit = false;
                while (!exit) {
                    System.out.println(messages.getString("welcome"));
                    System.out.println("1. " + messages.getString("registerUser"));
                    System.out.println("2. " + messages.getString("login"));
                    System.out.println("3. " + messages.getString("addNewsletterAdmin"));
                    System.out.println("4. " + messages.getString("showNewsAndPatches"));
                    System.out.println("5. " + messages.getString("exit"));
                    System.out.print(messages.getString("chooseOption"));
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
                            System.out.println(messages.getString("exitProgram"));
                            exit = true;
                            running = false; // Lägg till denna rad för att avsluta programmet
                            break;
                        default:
                            System.out.println(messages.getString("invalidChoice"));
                            break;
                    }
                }
            }
        }
    }


    private void selectLanguage(Scanner scanner) {
        System.out.println("1. English\n2. Svenska");
        String choice = scanner.nextLine();
        Locale locale = "1".equals(choice) ? new Locale("en", "US") : new Locale("sv", "SE");
        messages = ResourceBundle.getBundle("MessagesBundle", locale);
        System.out.println(messages.getString("languageChosen"));
    }

    private void registerUser(Scanner scanner) {
        System.out.println(messages.getString("enterUsername"));
        String username = scanner.nextLine();
        System.out.println(messages.getString("enterPassword"));
        String password = scanner.nextLine();
        System.out.println(messages.getString("enterGameName"));
        String gameName = scanner.nextLine();

        boolean success = userService.registerUser(username, password, gameName);
        if (success) {
            System.out.println(messages.getString("userRegistered"));
        } else {
            System.out.println(messages.getString("registrationFailed"));
        }
    }

    private void loginUser(Scanner scanner) {
        System.out.print(messages.getString("enterUsername"));
        String username = scanner.nextLine();
        System.out.print(messages.getString("enterPassword"));
        String password = scanner.nextLine();

        Optional<Anvandare> loggedInUserOptional = userService.loginUser(username, password);
        if (loggedInUserOptional.isPresent()) {
            currentUser = loggedInUserOptional.get();
            System.out.println(messages.getString("loginSuccessful"));

            this.currentAnvandarnamn = currentUser.getAnvandarnamn();

            showGameMenu(scanner, currentUser.getSpelnamn());
        } else {
            System.out.println(messages.getString("loginFailed"));
        }
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
            userService.saveUser(currentUser);
        } else {
            System.out.println(messages.getString("userNotLoggedIn"));
        }
    }

    private void startCountChallenge() {
        if (currentUser != null) {
            CountChallenge countChallenge = new CountChallenge(spelDataService, userService);
            countChallenge.setupChallenge(messages, currentUser);
            countChallenge.startChallenge();
        } else {
            System.out.println(messages.getString("userNotLoggedIn"));
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
            String newSpelnamn = currentUser.getSpelnamn(); // Ändrad för att använda nuvarande spelnamn
            String newLosenord = currentUser.getLosenord(); // Ändrad för att använda nuvarande lösenord

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

    private void addNewsLetterIfAdmin(Scanner scanner, AdminService adminService) {
        System.out.print(messages.getString("enterAdminPassword")); // Lägg till översättning för "enterAdminPassword" i dina resursfiler
        String password = scanner.nextLine();
        if (adminService.authenticateAdmin(password)) {
            System.out.print(messages.getString("enterTitle")); // Lägg till översättning för "enterTitle" i dina resursfiler
            String title = scanner.nextLine();
            System.out.print(messages.getString("enterContent")); // Lägg till översättning för "enterContent" i dina resursfiler
            String content = scanner.nextLine();

            try {
                adminService.addNewsLetter(title, content);
                System.out.println(messages.getString("newsletterAdded")); // Lägg till översättning för "newsletterAdded" i dina resursfiler
            } catch (Exception e) {
                System.out.println(messages.getString("newsletterAddFailed")); // Lägg till översättning för "newsletterAddFailed" i dina resursfiler
                e.printStackTrace();
            }
        } else {
            System.out.println(messages.getString("invalidAdminPassword")); // Lägg till översättning för "invalidAdminPassword" i dina resursfiler
        }
    }

    private void showNewsAndPatches() {
        System.out.println("\n" + messages.getString("newsletters"));
        List<String> newsletters = adminService.getNewsletters();
        if (newsletters.isEmpty()) {
            System.out.println(messages.getString("noNewsAvailable"));
        } else {
            for (String newsItem : newsletters) {
                System.out.println(newsItem);
            }
        }
    }
    private void showScore() {
        if (currentUser != null) {
            System.out.println("Your current score is: " + currentUser.getPoang());
        } else {
            System.out.println("No user is currently logged in.");
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
}