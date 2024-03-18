package se.ju23.typespeeder;

import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CountChallenge {

    private final Scanner scanner = new Scanner(System.in);
    private final ResourceBundle messages;
    private final Random random = new Random();

    public CountChallenge(ResourceBundle messages) {
        this.messages = messages;
    }

    public void startChallenge() {
        System.out.println(messages.getString("selectDifficulty"));
        int levelChoice = scanner.nextInt();
        scanner.nextLine(); // Rensa bufferten

        int minCount, maxCount;
        switch (levelChoice) {
            case 1: // Lätt
                minCount = 1;
                maxCount = 5;
                break;
            case 2: // Medel
                minCount = 6;
                maxCount = 10;
                break;
            case 3: // Svårt
                minCount = 11;
                maxCount = 15;
                break;
            default:
                System.out.println(messages.getString("invalidChoice"));
                return;
        }

        int targetCharCount = minCount + random.nextInt(maxCount - minCount + 1);

        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            text.append((char) ('a' + random.nextInt(26))); // Lägg till en slumpmässig bokstav
        }

        // Infoga symbolen 'targetCharCount' gånger på slumpmässiga positioner
        for (int i = 0; i < targetCharCount; i++) {
            int position = random.nextInt(text.length());
            text.insert(position, "@"); // Använder "@" som exempel, kan vara vilken symbol som helst
        }

        String targetSymbol = "@";
        System.out.println(messages.getString("countChallengeText") + "\n" + text);
        System.out.println(messages.getString("countChallengePrompt") + " " + targetSymbol);

        long startTime = System.currentTimeMillis(); // Starta tidmätningen

        int userGuess = scanner.nextInt();

        long endTime = System.currentTimeMillis(); // Avsluta tidmätningen
        long duration = (endTime - startTime) / 1000; // Beräkna tidsåtgången i sekunder

        long actualCount = text.chars().filter(ch -> ch == targetSymbol.charAt(0)).count();

        if (userGuess == actualCount) {
            System.out.println(messages.getString("correctAnswer") + " " + messages.getString("completedIn") + " " + duration + " " + messages.getString("seconds"));
        } else {
            System.out.println(messages.getString("wrongAnswer") + " " + actualCount + "."); // Tar bort tidsmeddelandet för fel svar
        }
    }
}
