package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    //private Game game;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Feedback> feedbackList = new ArrayList<>();

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

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setWordToGuess(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public Feedback getLastFeedback(){
        if (feedbackList.isEmpty()){
            return new Feedback();
        }else {
            return feedbackList.get(feedbackList.size() - 1);
        }
    }

    public Feedback guess(String attempt){
        List<Mark> markList = new ArrayList<>();
        Feedback feedback;

        // Check if attempt is legal
        if (attempt.length() == 0 || attempt.length() != wordToGuess.length()){
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
        feedback = new Feedback(attempt, markList);
        feedbackList.add(feedback);

        if(getLastFeedback().isWordGuessed()){
            gameState = GameState.WIN;
        }else{
            gameState = GameState.LOSE;
        }

        return feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return roundNumber == round.roundNumber && Objects.equals(id, round.id) && Objects.equals(wordToGuess, round.wordToGuess) && gameState == round.gameState;
    }
}
