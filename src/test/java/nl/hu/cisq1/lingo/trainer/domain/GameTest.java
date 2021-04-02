package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game = new Game();
    Round round = new Round("STERK");

    @Test
    @DisplayName("Check if a new round is started and added to the roundlist")
    void startNewRound() {
        this.game.startNewRound(this.round.getWordToGuess());
        this.game.setGameState(GameState.END_GAME); // Round must have state: END_GAME
                                                    // before a new round starts

        this.game.startNewRound("APPEL");
        this.game.setGameState(GameState.END_GAME); // Round must have state: END_GAME
                                                    // before a new round starts

        this.game.startNewRound("BROOD");
        this.game.setGameState(GameState.END_GAME); // Round must have state: END_GAME
                                                    // before a new round starts

        assertEquals(3, this.game.getRoundList().size());
    }

    @Test
    @DisplayName("Check if a round is started while the state is still on IN_GAME")
    void startNewRoundWithActiveRound() {
        this.game.startNewRound(this.round.getWordToGuess());

        assertThrows(IllegalStateException.class, () -> this.game.startNewRound("APPEL"));
    }

    @Test
    @DisplayName("Check if the last round is selected")
    void getLastRoundFromList() {
        this.game.getRoundList().add(this.round);
        assertEquals(this.game.getLastRoundFromList(), this.round);
    }

    @Test
    @DisplayName("Check if the list with guesses increases with each guess")
    void makeGuess() {
        this.game.startNewRound(this.round.getWordToGuess());
        assertEquals(0, this.game.getGuesses().size());

        this.game.makeGuess("STAAR");
        assertEquals(1, this.game.getGuesses().size());

        this.game.makeGuess("STUUR");
        assertEquals(2, this.game.getGuesses().size());
    }
}