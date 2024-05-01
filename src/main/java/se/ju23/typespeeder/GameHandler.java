package se.ju23.typespeeder;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


@Component
public class GameHandler {
    private final SpeldataService spelDataService;
    private final UserService userService;
    private ResourceBundle messages;

    // Injecta beroenden via konstruktören
    @Autowired
    public GameHandler(SpeldataService spelDataService, UserService userService) {
        this.spelDataService = spelDataService;
        this.userService = userService;
    }

    @PostConstruct
    private void init() {
        this.messages = ResourceBundle.getBundle("MessagesBundle", new Locale("sv", "SE"));
        // Kommentera ut eller ta bort följande rader om 'testKey' inte behövs
        // System.out.println("Testmeddelande: " + messages.getString("testKey"));
    }


    // Metod för att hantera start av spelet
    public void startGame() {
        // Din logik för att starta spelet här
        System.out.println(messages.getString("gameStart")); // Ett exempel på att använda internationaliserade strängar
        // Fler operationer som involverar spellogik...
    }
}


