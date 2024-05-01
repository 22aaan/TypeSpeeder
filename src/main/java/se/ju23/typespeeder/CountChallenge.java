package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

@Component
public class CountChallenge {
    private final SpeldataService spelDataService;
    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private Anvandare currentUser;
    private ResourceBundle messages;

    @Autowired
    public CountChallenge(SpeldataService spelDataService, UserService userService) {
        this.spelDataService = spelDataService;
        this.userService = userService;
    }

    public void setupChallenge(ResourceBundle messages, Anvandare user) {
        this.messages = messages;
        this.currentUser = user;
    }

    public void startChallenge() {
        System.out.println(messages.getString("selectDifficulty"));
        int levelChoice = Integer.parseInt(scanner.nextLine());
        int pointsToAdd = 0, pointsToDeduct = 0;
        int minCount = 0, maxCount = 0;

        switch (levelChoice) {
            case 1:
                minCount = 1; maxCount = 5; pointsToAdd = 1; pointsToDeduct = 1;
                break;
            case 2:
                minCount = 6; maxCount = 10; pointsToAdd = 2; pointsToDeduct = 1;
                break;
            case 3:
                minCount = 11; maxCount = 15; pointsToAdd = 4; pointsToDeduct = 2;
                break;
            default:
                System.out.println(messages.getString("invalidChoice"));
                return;
        }

        StringBuilder text = generateRandomText(minCount, maxCount);
        System.out.println(messages.getString("countChallengeText") + "\n" + text);
        System.out.println(messages.getString("countChallengePrompt") + " @");

        long startTime = System.currentTimeMillis();
        int userGuess = scanner.nextInt();
        scanner.nextLine(); // För att hantera newline efter siffran
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;

        long actualCount = text.chars().filter(ch -> ch == '@').count();
        boolean isCorrect = userGuess == actualCount;

        if (isCorrect) {
            currentUser.addPoang(pointsToAdd);
            System.out.println(messages.getString("correctAnswer") + " " + messages.getString("completedIn") + " " + duration + " " + messages.getString("seconds"));
        } else {
            currentUser.removePoints(pointsToDeduct);
            System.out.println(messages.getString("wrongAnswer") + " " + actualCount + ".");
        }

        // Här antar vi att du har en metod för att hämta den senaste sekvensen av korrekta svar i rad
        int antalRattIRad = spelDataService.hittaSenasteFleraRattForAnvandare(currentUser);
        if (isCorrect) {
            antalRattIRad++; // Om svaret är korrekt, öka antalet rätta svar i rad
        } else {
            antalRattIRad = 0; // Annars, nollställ
        }

        // Anropa avslutaSpel med rätt parametrar
        spelDataService.avslutaSpel(currentUser, duration, isCorrect, antalRattIRad, text.toString());

        // Uppdatera användaren i databasen
        userService.saveUser(currentUser);
        System.out.println("Your total score is now: " + currentUser.getPoang());
    }

    private StringBuilder generateRandomText(int minCount, int maxCount) {
        int targetCharCount = minCount + random.nextInt(maxCount - minCount + 1);
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            text.append((char) ('a' + random.nextInt(26)));
        }

        for (int i = 0; i < targetCharCount; i++) {
            int position = random.nextInt(text.length() + 1);
            text.insert(position, '@');
        }
        return text;
    }
}