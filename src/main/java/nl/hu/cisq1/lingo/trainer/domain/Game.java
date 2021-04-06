package nl.hu.cisq1.lingo.trainer.domain;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "game")
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private Long id;

    @Column
    private int score;

    @Enumerated(EnumType.ORDINAL)
    private GameState gameState = GameState.END_GAME; // Set the standard state in END_GAME

    @OneToMany(cascade = CascadeType.ALL)
    private List<Round> roundList = new ArrayList<>();

    @Transient
    private List<Feedback> guesses = new ArrayList<>();

    public Game() {
    }

    public Game(int score) {
        this.score = 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setRoundList(List<Round> roundList) {
        this.roundList = roundList;
    }

    public void setGuesses(List<Feedback> guesses) {
        this.guesses = guesses;
    }



    public List<Round> getRoundList() {
        return roundList;
    }

    public List<Feedback> getGuesses() {
        return guesses;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }


    public void score(int numberOfAttempts) {
        score += + 5 * (5 - numberOfAttempts) + 5;
    }

    //Method to start a new round if the game is not active
    public void startNewRound(String wordToGuess) {
        if (gameState != GameState.IN_GAME) {
            Round round = new Round(wordToGuess);
            roundList.add(round);
            gameState = GameState.IN_GAME;
        } else {
            throw new IllegalStateException("The game is still active");
        }
    }

    // Method to get the active round
    public Round getLastRoundFromList() {
        if (roundList.isEmpty()) {
            return new Round();
        } else {
            return roundList.get(roundList.size() - 1);
        }
    }

    // Make a guess
    public void makeGuess(String attempt) {
        Round round = getLastRoundFromList();
        round.guess(attempt);

        Feedback feedback = round.guess(attempt);
        this.guesses.add(feedback);

        if (getLastRoundFromList().getLastFeedback().isWordGuessed()){
            score(getLastRoundFromList().getFeedbackList().size());
                gameState = GameState.END_GAME;
            }

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return score == game.score && Objects.equals(id, game.id) && gameState == game.gameState && Objects.equals(roundList, game.roundList) && Objects.equals(guesses, game.guesses);
    }
}
