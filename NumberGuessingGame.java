import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        int bestScore = 0; // 🏆 En iyi skor kaydı

        System.out.println("🎯 WELCOME TO THE NUMBER GUESSING GAME!");
        System.out.println("---------------------------------------");

        while (playAgain) {
            int[] settings = chooseDifficulty(input);
            int maxNumber = settings[0];
            int maxAttempts = settings[1];

            int secretNumber = random.nextInt(maxNumber) + 1;
            int attempts = 0;
            boolean guessed = false;

            // ⏱️ Zaman ölçümü başlat
            long startTime = System.currentTimeMillis();

            System.out.println("\nI've picked a number between 1 and " + maxNumber + ".");
            System.out.println("Can you find it within " + maxAttempts + " attempts? Let's go!");

            while (attempts < maxAttempts) {
                int guess = getUserGuess(input);
                attempts++;

                if (guess == secretNumber) {
                    // ⏱️ Zaman ölçümü bitir
                    long endTime = System.currentTimeMillis();
                    long durationSeconds = (endTime - startTime) / 1000;

                    int score = (maxAttempts - attempts + 1) * 10;
                    // Zaman faktörü: daha kısa sürede bitirirse bonus
                    if (durationSeconds < 30) score += 10;
                    if (durationSeconds < 15) score += 20;

                    System.out.println("\n🎉 Congratulations! You guessed the number: " + secretNumber);
                    System.out.println("Total attempts: " + attempts);
                    System.out.println("Time taken: " + durationSeconds + " seconds");
                    System.out.println("Your score: " + score);

                    // 🏆 En iyi skor kontrolü
                    if (score > bestScore) {
                        bestScore = score;
                        System.out.println("🥇 NEW HIGH SCORE! Great job!");
                    } else {
                        System.out.println("⭐ Your best score so far: " + bestScore);
                    }

                    guessed = true;
                    break;
                } else if (guess < secretNumber) {
                    System.out.println("⬆️ Try a higher number! | Attempts left: " + (maxAttempts - attempts));
                } else {
                    System.out.println("⬇️ Try a lower number! | Attempts left: " + (maxAttempts - attempts));
                }
            }

            if (!guessed) {
                System.out.println("😢 You ran out of attempts. The correct number was: " + secretNumber);
                System.out.println("Better luck next time!");
                System.out.println("⭐ Your best score so far: " + bestScore);
            }

            System.out.print("\nDo you want to play again? (Y/N): ");
            String again = input.next().trim().toUpperCase();
            playAgain = again.equals("Y");
        }

        System.out.println("\n👋 Thanks for playing! See you next time!");
        input.close();
    }

    // 🎮 Difficulty selection method
    private static int[] chooseDifficulty(Scanner input) {
        System.out.println("\nSelect difficulty level:");
        System.out.println("1 - Easy (1–50, 12 attempts)");
        System.out.println("2 - Medium (1–100, 10 attempts)");
        System.out.println("3 - Hard (1–500, 7 attempts)");
        System.out.print("Your choice: ");

        int choice;
        try {
            choice = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("⚠️ Invalid input! Defaulting to Medium (1–100).");
            input.nextLine();
            return new int[]{100, 10};
        }

        // ⚙️ Zorluk seviyesine göre aralık ve hak sayısı
        return switch (choice) {
            case 1 -> new int[]{50, 12};
            case 2 -> new int[]{100, 10};
            case 3 -> new int[]{500, 7};
            default -> {
                System.out.println("⚠️ Invalid choice! Defaulting to Medium (1–100).");
                input.nextLine(); // 🔧 Profesyonel buffer temizliği
                yield new int[]{100, 10};
            }
        };
    }

    // 🔢 User guess input method
    private static int getUserGuess(Scanner input) {
        while (true) {
            System.out.print("Enter your guess: ");
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Please enter a valid number!");
                input.nextLine(); // Clear invalid input
            }
        }
    }
}



