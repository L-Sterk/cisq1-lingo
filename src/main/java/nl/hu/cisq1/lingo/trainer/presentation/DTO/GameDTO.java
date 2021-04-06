package nl.hu.cisq1.lingo.trainer.presentation.DTO;

import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Round;

import java.util.ArrayList;
import java.util.List;

public class GameDTO {
    private  Long game_id;
    private int score;
    private GameState gameState;
    private String wordToGuess;
    private int attemptNr;
    private String hint;
    private List<Feedback> feedbackList = new ArrayList<>();

    public GameDTO(Long game_id, GameState gameState) {
        this.game_id = game_id;
        this.gameState = gameState;
    }

//    public GameDTO(Game game) {
//        Long game_id = game.getId();
//        int score = game.getScore();
//        GameState gameState = game.getGameState();
//        List<Round> roundList = game.getRoundList();
//        List<Feedback> guessList = game.getGuesses();
//    }

}