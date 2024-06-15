package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WordChallenge {
    private final SpeldataService spelDataService;
    private final Scanner scanner = new Scanner(System.in);
    private final List<String> swedishWords;
    private final List<String> englishWords;
    private Anvandare currentUser;
    private ResourceBundle messages;

    @Autowired
    public WordChallenge(SpeldataService spelDataService) {
        this.spelDataService = spelDataService;
        this.swedishWords = List.of("fotboll", "handboll", "basket", "volleyboll", "golf", "tennis", "bordtennis", "badminton", "baseboll", "rugby");
        this.englishWords = List.of("soccer", "handball", "basketball", "volleyball", "golf", "tennis", "table tennis", "badminton", "baseball", "rugby");
    }

    public void setupChallenge(ResourceBundle messages, Anvandare user) {
        this.messages = messages;
        this.currentUser = user;
    }

    public void startChallenge() {
        System.out.println(messages.getString("selectDifficulty"));
        int levelChoice = Integer.parseInt(scanner.nextLine());
        int pointsToAdd = 0, pointsToDeduct = 0;

        int wordsToGuess = switch (levelChoice) {
            case 1 -> { pointsToAdd = 1; pointsToDeduct = 1; yield 1; }
            case 2 -> { pointsToAdd = 2; pointsToDeduct = 1; yield 2; }
            case 3 -> { pointsToAdd = 4; pointsToDeduct = 2; yield 4; }
            default -> {
                System.out.println(messages.getString("invalidChoice"));
                yield -1;
            }
        };

        if (wordsToGuess == -1) return;

        List<String> words = new ArrayList<>(messages.getLocale().getLanguage().equals("sv") ? swedishWords : englishWords);
        Collections.shuffle(words);

        List<String> targetWords = words.subList(0, wordsToGuess);
        String displayString = String.join(", ", targetWords);

        System.out.println(messages.getString("writeMarkedWords"));
        System.out.println(displayString);

        long startTime = System.currentTimeMillis();
        String userResponse = scanner.nextLine().trim();
        List<String> userWords = Arrays.asList(userResponse.split("\\s*,\\s*"));
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;

        boolean isCorrect = userWords.containsAll(targetWords) && targetWords.containsAll(userWords);

        int antalRattIRad = spelDataService.hittaSenasteFleraRattForAnvandare(currentUser);
        antalRattIRad = isCorrect ? antalRattIRad + 1 : 0;

        if (isCorrect) {
            currentUser.addPoang(pointsToAdd);
            System.out.println(messages.getString("correctAnswer") + " " + messages.getString("completedIn") + " " + duration + " " + messages.getString("seconds"));
        } else {
            currentUser.removePoints(pointsToDeduct);
            System.out.println(messages.getString("wrongAnswer") + ": " + String.join(", ", targetWords));
        }

        spelDataService.avslutaSpel(currentUser, duration, isCorrect, antalRattIRad, userResponse);

        System.out.println("Your total score is now: " + currentUser.getPoang());
    }
}
