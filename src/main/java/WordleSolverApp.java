import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WordleSolverApp {

    final static Scanner s = new Scanner(System.in);
    //regex: 5 letters, a space, 5 numbers
    private final static Pattern checkInput = Pattern.compile("^[a-zA-Z]{5} [0-2]{5}$");

    public static int maxTries = 6; //number of total tries to check word

    public static void main(String[] args) {

        try {
            WordleSolver.readAnswerWords();
            System.out.println("All possible words Dictionary size: " + WordleSolver.answerWordsList.size());

        } catch (Exception e) {
            System.out.println("Please check the URL is correct for the dictionary source.");
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println(
                "Enter a Wordle check result with 5 letters, a space and 5 numbers from 0 to 2.\n" +
                        "For example: APPLE 10020\n" +
                        "Explanation:\n" +
                        "  0 means the letter is not in the word,\n" +
                        "  1 means the letter is at the right position,\n" +
                        "  2 means the letter is in the wrong position."
        );
        try {
            for (int i = 0; i != maxTries; ) {
                System.out.print("Enter word to check (try " + (i + 1) + "/" + maxTries + "): ");
                String input = s.nextLine().toUpperCase();
                if (checkInput.matcher(input).find() && WordleSolver.answerWordsList.contains(input.substring(0, 5))) //if the input is valid & input word in list
                {
                    String inputWord = input.substring(0, 5);
                    int[] result = new int[5];
                    for (int j = 0; j != 5; j++) result[j] = Integer.parseInt(input.substring(j + 6, j + 7));
                    WordleSolver.calculatePossibleWords(inputWord, result);
                    if (WordleSolver.answerWordsList.size() == 0) {
                        System.out.println("No words left in dictionary!");
                        System.exit(0);
                    } else if (WordleSolver.answerWordsList.size() == 1) {
                        System.out.println("The word is: " + WordleSolver.answerWordsList.get(0));
                        System.exit(0);
                    } else {
                        System.out.println("Possible words: " + WordleSolver.answerWordsList);
                        System.out.println("Words left: " + WordleSolver.answerWordsList.size());
                    }
                    i++;
                } else {
                    System.out.print("Invalid input. Try again? [y/n]\n? ");
                    if (s.nextLine().equalsIgnoreCase("y")) continue;
                    else {
                        System.out.println("Exiting");
                        System.exit(0);
                    }
                }
            }
        } catch (NoSuchElementException e) //or when Ctrl+D is pressed to exit program
        {
            System.out.println("\nExiting");
            System.exit(0);
        }
        System.out.println("Maximum tries exceeded. The program will now exit.\n");
    }
}