package unit13.assignment2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import unit13.backtracker.Backtracker;
import unit13.backtracker.Configuration;

public class HangmanConfiguration implements Configuration<HangmanConfiguration> {
    private Hangman hangman;
    private Set<Character> previousGuesses;

    public HangmanConfiguration(Hangman hangman, Set<Character> previousGuesses) {
        this.hangman = hangman;
        this.previousGuesses = previousGuesses;
    }

    @Override
    public Collection<HangmanConfiguration> getSuccessors() {
        List<HangmanConfiguration> successors = new ArrayList<>();
        for(char letter = 'A'; letter <= 'Z'; letter++) {
            if(!previousGuesses.contains(letter)) {
                Hangman hangmanCopy = new Hangman(hangman);
                hangmanCopy.guess(letter);
                HashSet<Character> previousGuessesCopy = new HashSet<>(previousGuesses);
                previousGuessesCopy.add(letter);
                HangmanConfiguration hangmanConfigurationCopy = new HangmanConfiguration(hangmanCopy, previousGuessesCopy);
                successors.add(hangmanConfigurationCopy);
            }
        }
        return successors;
    }

    @Override
    public boolean isValid() {
        return hangman.getStatus() != Hangman.Status.LOST;
    }

    @Override
    public boolean isGoal() {
        return hangman.getStatus() == Hangman.Status.WON;
    }

    @Override
    public String toString() {
        return hangman.getGuesses() + ", " + hangman.revealed();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<Character> previousGuesses = new HashSet<>();

        System.out.print("Enter a secret phrase: ");
        String secret = scanner.nextLine();
        Hangman hangman = new Hangman(secret);

        HangmanConfiguration newGame = new HangmanConfiguration(hangman, previousGuesses);
        Backtracker<HangmanConfiguration> backtracker = new Backtracker<>(false);

        HangmanConfiguration solution = backtracker.solve(newGame);

        if(solution != null) {
            System.out.println(solution.toString());
        } else {
            System.out.println("No solution found.");
        }

        scanner.close();
    }   
}
