package nl.hu.cisq1.lingo.trainer.presentation.DTO;

import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Round;

import java.util.List;

public class RoundDTO {
    public RoundDTO(Round round) {
        Long round_id = round.getId();
        int roundNumber = round.getRoundNumber();
        String wordToGuess = round.getWordToGuess();
        GameState gameState = round.getGameState();
        Game game = round.getGame();
    }
}
