package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WordChallenge {
    @Autowired
    private SpeldataService spelDataService;
    private final Scanner scanner;
    private final List<String> swedishWords;
    private final List<String> englishWords;
    private Anvandare currentUser;
    private ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());


    @Autowired
    public WordChallenge(SpeldataService spelDataService) {
        this.spelDataService = spelDataService;
        this.scanner = new Scanner(System.in);
        this.swedishWords = List.of("fotboll", "handboll", "basket", "volleyboll", "golf", "tennis", "bordtennis", "badminton", "baseboll", "rugby");
        this.englishWords = List.of("soccer", "handball", "basketball", "volleyball", "golf", "tennis", "table tennis", "badminton", "baseball", "rugby");
    }

    public void setCurrentUser(Anvandare currentUser) {
        this.currentUser = currentUser;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public void startChallenge() {
        if (messages == null || currentUser == null) {
            System.err.println("WordChallenge har inte korrekt initialiserats med alla nödvändiga beroenden.");
            return;
        }

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
        if (isCorrect) {
            currentUser.addPoang(pointsToAdd);
            System.out.println(messages.getString("correctAnswer") + " " + messages.getString("completedIn") + " " + duration + " " + messages.getString("seconds"));
        } else {
            currentUser.removePoints(pointsToDeduct);
            System.out.println(messages.getString("wrongAnswer") + ": " + String.join(", ", targetWords));
        }

        // Skapa och spara Speldata
        Speldata spelData = new Speldata();
        spelData.setAnvandare(currentUser);
        spelData.setTid(duration);
        spelData.setRattaSvar(isCorrect ? targetWords.size() : 0);
        spelData.setSvarIOrdning(userResponse);
        spelData.setIsCorrect(isCorrect);
        spelDataService.saveSpeldata(spelData); // Se till att denna metod existerar och är korrekt implementerad

        System.out.println("Your total score is now: " + currentUser.getPoang());
    }

    // Eventuella ytterligare metoder som behövs för din spellogik...
}
