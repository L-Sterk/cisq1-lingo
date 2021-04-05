package nl.hu.cisq1.lingo.trainer.application;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
import nl.hu.cisq1.lingo.trainer.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


import javax.transaction.Transactional;



import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(CiTestConfiguration.class)
class GameServiceIntegrationTest {
    static Game game = new Game();

    @Autowired
    GameService gameService;

    @Autowired
    SpringGameRepository springGameRepository;

    @Autowired
    WordService wordService;

    @BeforeEach
    void beforeEach() {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("STERK");


        game = this.gameService.startNewGame();
        gameService = new GameService(wordService, springGameRepository);
    }

    @Test
    @Transactional
    @DisplayName("Check if a game has an id")
    void findGameById() {
        try {
            assertNotNull(gameService.getGameById(game.getId()));
        } catch (NotFoundException nfe) {
            nfe.getMessage();
        }
    }

    @Test
    @Transactional
    @DisplayName("Check if an exception is thrown when the game with id 1234567890 does not exist")
    void findGameByNonExistingId() {
        assertThrows(NotFoundException.class, () -> gameService.getGameById(1234567890L));
    }


    @Test
    @Transactional
    @DisplayName("Check if the guess attempt is valid")
    void validGuess(){
        try {
            gameService.startNewRound(game.getId());
            gameService.makeGuess(game.getId(), "STERK");

            assertTrue(gameService.getGameById(game.getId()).getLastRoundFromList().getLastFeedback().isGuessValid());
        }catch (NotFoundException nfe){
            nfe.getMessage();
        }
    }

    @Test
    @Transactional
    @DisplayName("Check if a new Round is IN_GAME")
    void startNewRound() {
        try {
            gameService.startNewRound(game.getId());

            assertEquals(gameService.getGameById(game.getId()).getLastRoundFromList().getGameState(), GameState.IN_GAME);
        }catch (NotFoundException nfe){
            nfe.getMessage();
        }
    }

    @Test
    @Transactional
    @DisplayName("Check if the attempt is invalid")
    void invalidGuess() {
        assertThrows(InvalidFeedbackException.class,
                () -> gameService.makeGuess(game.getId(), "STERKER"));
    }

}