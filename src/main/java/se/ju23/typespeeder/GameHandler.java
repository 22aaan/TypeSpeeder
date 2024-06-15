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

    @Autowired
    public GameHandler(SpeldataService spelDataService, UserService userService) {
        this.spelDataService = spelDataService;
        this.userService = userService;
    }

    @PostConstruct
    private void init() {
        this.messages = ResourceBundle.getBundle("MessagesBundle", new Locale("sv", "SE"));

    }


    public void startGame() {
        System.out.println(messages.getString("gameStart"));
    }
}


