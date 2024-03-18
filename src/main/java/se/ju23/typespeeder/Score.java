package se.ju23.typespeeder;

public class Score {
    private int score = 0; // Initial poängsättning

    // Metod för att lägga till poäng baserat på svårighetsgraden av frågan
    public void updateScore(int pointsToAdd) {
        score += pointsToAdd; // Lägg till de angivna poängen till den totala poängen
    }

    // Metod för att visa den aktuella poängen till användaren
    public void displayScore() {
        System.out.println("Din poäng: " + score); // Skriver ut den aktuella poängen
    }

    // Getter-metod för att hämta den aktuella poängen, om det behövs någon annanstans
    public int getScore() {
        return score;
    }

    // Eventuellt andra hjälpmetoder som kan vara nödvändiga för poänghantering
}
