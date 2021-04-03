package nl.hu.cisq1.lingo.trainer.presentation.DTO;

import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Round;

import java.util.List;

public class GameDTO {
    public GameDTO(Game game) {
        Long game_id = game.getId();
        int score = game.getScore();
        GameState gameState = game.getGameState();
        List<Round> roundList = game.getRoundList();
        List<Feedback> guessList = game.getGuesses();
    }
}