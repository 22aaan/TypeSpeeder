package se.ju23.typespeeder;

import java.util.*;
import java.util.stream.Collectors;

public class WordChallenge {

    private final Scanner scanner = new Scanner(System.in);
    private final ResourceBundle messages;
    private final List<String> swedishWords = List.of("fotboll", "handboll", "basket", "volleyboll", "golf", "tennis", "bordtennis", "badminton", "baseboll", "rugby");
    private final List<String> englishWords = List.of("soccer", "handball", "basketball", "volleyball", "golf", "tennis", "table tennis", "badminton", "baseball", "rugby");

    public WordChallenge(ResourceBundle messages) {
        this.messages = messages;
    }

    public void startChallenge() {
        System.out.println(messages.getString("selectDifficulty"));
        int levelChoice = scanner.nextInt();
        scanner.nextLine(); // Läs in radslutet efter int-input

        int wordsToGuess = switch (levelChoice) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            default -> {
                System.out.println(messages.getString("invalidChoice"));
                yield -1;
            }
        };

        if (wordsToGuess == -1) return;

        List<String> words = new ArrayList<>(messages.getLocale().getLanguage().equals("sv") ? swedishWords : englishWords);
        Collections.shuffle(words);

        List<String> targetWords = words.subList(0, wordsToGuess);
        String displayString = words.stream()
                .map(word -> targetWords.contains(word) ? "(" + word + ")" : word)
                .collect(Collectors.joining(" "));

        System.out.println(messages.getString("writeMarkedWords"));
        System.out.println(displayString);

        long startTime = System.currentTimeMillis();

        // Användaren matar in sina gissningar
        String userResponse = scanner.nextLine().trim();
        List<String> userWords = Arrays.asList(userResponse.split("\\s*,\\s*"));
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;

        boolean allCorrect = userWords.size() == targetWords.size() && userWords.containsAll(targetWords);

        if (allCorrect) {
            System.out.println(messages.getString("correctAnswer") + " " + messages.getString("completedIn") + " " + duration + " " + messages.getString("seconds"));
        } else {
            System.out.println(messages.getString("wrongAnswer") + ": " + String.join(", ", targetWords));
        }
    }
}
