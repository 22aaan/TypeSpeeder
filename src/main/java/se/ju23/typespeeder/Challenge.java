package se.ju23.typespeeder;

public class Challenge {

    public void startChallenge() {
        try {
            Thread.sleep(100); // 100 ms fördröjning
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public String lettersToType() {
        return "abc";
    }
}
