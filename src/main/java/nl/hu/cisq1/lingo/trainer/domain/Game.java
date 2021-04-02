package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private Long id;

    @Column
    private int score;

    @Enumerated(EnumType.ORDINAL)
    private GameState gameState = GameState.END_GAME; // Set the standard state in END_GAME

    @OneToMany
    @JoinColumn() // TODO: fix later
    private List<Round> roundList = new ArrayList<>();

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

    /*
    Method to start a new round if the game is not active
     */
    public void startNewRound(String wordToGuess){
        if (gameState != GameState.IN_GAME) {
            Round round = new Round(wordToGuess);
            roundList.add(round);
            gameState = GameState.IN_GAME;
        }else {
            throw new IllegalStateException("The game is still active");
        }
    }

}
