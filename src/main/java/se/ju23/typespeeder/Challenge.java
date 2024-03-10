package se.ju23.typespeeder;

public class Challenge {

    // Denna metod simulerar starten av en utmaning
    public void startChallenge() {
        // Simulera en liten fördröjning
        try {
            Thread.sleep(100); // 100 ms fördröjning
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Denna metod returnerar en sträng som representerar bokstäverna att skriva
    public String lettersToType() {
        // Simulera att välja bokstäver att skriva
        // I en riktig applikation skulle detta vara mer komplext
        return "abc";
    }
}

