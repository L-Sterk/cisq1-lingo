package nl.hu.cisq1.lingo.trainer.presentation;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.GuessDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class GameControllerTest {
    private GameService gameService;
    private GameController gameController;
    private Game game;

    @BeforeEach
    void beforeEach() {
        gameService = mock(GameService.class);
        gameController = new GameController(gameService);
        game = new Game();
    }

    @Test
    @DisplayName("Start a new game")
    void startGame() {
        when(gameService.startNewGame()).thenReturn(game);

        assertNotNull(gameController.startNewGame());
    }

    @Test
    @DisplayName("Start a new round")
    void startRound() {
        try {
            when(gameService.startNewRound(anyLong())).thenReturn(game);

            assertNotNull(gameController.startNewRound(anyLong()));

        } catch (NotFoundException nfe) {
            nfe.getMessage();
        }
    }

    @Test
    @DisplayName("Make a guess test")
    void makeGuess() throws NotFoundException {
        game.startNewRound("STERK");
        when(gameService.makeGuess(anyLong(), anyString())).thenReturn(game);

        GuessDTO guessDTO = new GuessDTO();
        guessDTO.attempt = "BAARD";
        assertNotNull(gameController.makeGuess(11L, guessDTO));

    }
}