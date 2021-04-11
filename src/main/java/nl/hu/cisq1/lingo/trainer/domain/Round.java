package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Round implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "round_id")
    private Long id;

    @Column
    private int roundNumber;

    @Column
    private String wordToGuess;

    @Enumerated(EnumType.ORDINAL)
    private GameState gameState;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Feedback> feedbackList = new ArrayList<>();

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.gameState = GameState.IN_GAME;

    }

    public Round() {
    }


    public GameState getGameState() {
        return gameState;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public Feedback getLastFeedback() {
        if (feedbackList.isEmpty()) {
            return new Feedback();
        } else {
            return feedbackList.get(feedbackList.size() - 1);
        }
    }

    public Feedback guess(String attempt) {
        List<Mark> markList = new ArrayList<>();
        Feedback feedback;

        // Check if attempt is legal
        if (attempt.length() == 0 || attempt.length() != wordToGuess.length()) {
            for (int i = 0; i < wordToGuess.length(); i++) {
                markList.add(Mark.ILLEGAL);
            }
            return new Feedback(attempt, markList);
        }

        // Check if letters are correct, present or absent
        for (int i = 0; i < attempt.length(); i++) {
            String presentLetters = attempt.charAt(i) + "";
            if (attempt.charAt(i) == wordToGuess.charAt(i)) {
                markList.add(Mark.CORRECT);
                continue;
            }

            if (wordToGuess.contains(presentLetters)) {
                markList.add(Mark.PRESENT);
                continue;
            }
            markList.add(Mark.ABSENT);
        }
        feedback = new Feedback(attempt, markList);
        feedbackList.add(feedback);

        if (getLastFeedback().isWordGuessed()) {
            gameState = GameState.WIN;
        } else {
            gameState = GameState.LOSE;
        }

        return feedback;
    }
}
