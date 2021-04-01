package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private int roundNumber;

    @Column
    private String wordToGuess;

    @Enumerated(EnumType.ORDINAL)
    private GameState gameState;

    @ManyToOne
    @JoinColumn(name = "game") //TODO: fix later
    private Game game;

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.gameState = GameState.IN_GAME;

    }

    public Round() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public GameState getGameState() {
        return gameState;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public Feedback guess(String attempt){
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
