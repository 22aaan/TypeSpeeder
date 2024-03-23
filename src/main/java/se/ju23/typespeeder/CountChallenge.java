package se.ju23.typespeeder;

import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CountChallenge {

    private final Scanner scanner = new Scanner(System.in);
    private final ResourceBundle messages;
    private final Random random = new Random();
    private final Anvandare currentUser;
    private final UserService userService; // Antag att UserService injiceras eller tilldelas på något sätt

    public CountChallenge(ResourceBundle messages, Anvandare user, UserService userService) {
        this.messages = messages;
        this.currentUser = user;
        this.userService = userService;
    }

    public void startChallenge() {
        System.out.println(messages.getString("selectDifficulty"));
        int levelChoice = Integer.parseInt(scanner.nextLine());
        int pointsToAdd = 0;
        int pointsToDeduct = 0;

        int minCount, maxCount;
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
        boolean correct = userGuess == actualCount;

        if (correct) {
            currentUser.addPoang(pointsToAdd);
            System.out.println(messages.getString("correctAnswer") + " " + messages.getString("completedIn") + " " + duration + " " + messages.getString("seconds"));
        } else {
            System.out.println(messages.getString("wrongAnswer") + " " + actualCount + ".");
            currentUser.removePoints(pointsToDeduct);
        }

        userService.saveUser(currentUser); // Spara den uppdaterade användaren i databasen
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
