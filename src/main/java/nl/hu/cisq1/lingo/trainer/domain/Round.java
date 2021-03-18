package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;
import java.util.ArrayList;
import java.util.List;

public class Round {

    private int roundNumber;
    private String wordToGuess;
    private GameState gameState;

    public Round(int roundNumber, String wordToGuess, GameState gameState) {
        this.roundNumber = roundNumber;
        this.wordToGuess = wordToGuess;
        this.gameState = gameState;
    }

    public Feedback guess(String attempt){
        Feedback feedback;
        List<Mark> markList = new ArrayList<>();

        // Check if attempt is legal
        if (attempt == null || attempt.length() == 0 || attempt.length() != wordToGuess.length()){
            for (int i = 0; i < wordToGuess.length(); i++) {
                markList.add(Mark.ILLEGAL);
            }
            return new Feedback(attempt, markList);
        }

        // Check if letters are correct, present or absent
        for (int i = 0; i < attempt.length(); i++){
            String presentLetters = attempt.charAt(i) + "";
            if (attempt.charAt(i) == wordToGuess.charAt(i)){
                markList.add(Mark.CORRECT);
                continue;
            }

            if (wordToGuess.contains(presentLetters)){
                markList.add(Mark.PRESENT);
                continue;
            }

            markList.add(Mark.ABSENT);
        }

        return new Feedback(attempt, markList);
    }


}
