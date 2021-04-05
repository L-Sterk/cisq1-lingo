package nl.hu.cisq1.lingo.trainer.application;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Mark;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    static WordService wordService = mock(WordService.class);
    static SpringGameRepository springGameRepository = mock(SpringGameRepository.class);
    static GameService gameService = new GameService(wordService, springGameRepository);

    // initialize games
    static Game game1 = new Game();
    static Game game2 = new Game();
    static Game game3 = new Game();

    static List<Game> gameList = new ArrayList<>();

    // Stream for the test method: getGameById()
    private static Stream<Arguments> provideGames() {
        return Stream.of(
                Arguments.of(game1, 11L),
                Arguments.of(game2, 22L),
                Arguments.of(game3, 33L)
        );
    }

    // --- TESTS ---

    @BeforeAll
    static void beforeAll() {
        // Provide a random
        when(wordService.provideRandomWord(5)).thenReturn("STERK");

        // Add the four games to a list [getAllGames() method]
        when(springGameRepository.findAll()).thenReturn(List.of(game1, game2, game3));

        // Give games id's [getGameById() method]
        when(springGameRepository.findById(anyLong())).thenReturn(Optional.ofNullable(game1)); // AnyLong so you can test with any id
        when(springGameRepository.findById(22L)).thenReturn(Optional.ofNullable(game2));
        when(springGameRepository.findById(33L)).thenReturn(Optional.ofNullable(game3));
    }

    @AfterEach
    @DisplayName("Set the game state back to END_GAME after each test")
    void afterEach() {
        try {
            for (Game game : gameService.getAllGames()) {
                game.setGameState(GameState.END_GAME);
                game.getRoundList().clear();
            }

        } catch (NotFoundException nfe) {
            nfe.getMessage();
        }
    }

    @Test
    @DisplayName("Check if the three games are in the list")
    void getAllGames() {
        try {
            gameList = gameService.getAllGames();
            assertEquals(3, gameList.size());
        } catch (NotFoundException nfe) {
            nfe.getMessage();
        }
    }

    @ParameterizedTest
    @MethodSource("provideGames")
    @DisplayName("Check if the games can be found by id")
    void getGameById(Game game, Long id) throws NotFoundException {
        assertEquals(game, gameService.getGameById(id));
    }

    @Test
    @DisplayName("Start a new Game")
    void startNewGame() {
        // Before starting a new game
        assertEquals(0, game1.getRoundList().size());

        game1 = gameService.startNewGame();
        // After starting a new game
        assertEquals(1, game1.getRoundList().size());
    }

    @Test
    @DisplayName("Start a new Round for game1")
    void startNewRound(){
        try {
            game1.startNewRound(wordService.provideRandomWord(5));
            game1.makeGuess("STERK");
            gameService.startNewRound(1L);

            assertEquals(1, game1.getRoundList().size());
            assertEquals(GameState.IN_GAME, game1.getLastRoundFromList().getGameState());
        } catch (NotFoundException nfe) {
            nfe.getMessage();
        }


    }

    @Test
    @DisplayName("Make a correct guess")
    void makeGuess() {
        game1.startNewRound(wordService.provideRandomWord(5));

        assertDoesNotThrow(() -> gameService.makeGuess(anyLong(), "STERK"));
    }

    @Test
    @DisplayName("Make a wrong guess")
    void makeWrongGuess() {
        try {
            game1.startNewRound(wordService.provideRandomWord(5));
            gameService.makeGuess(anyLong(), "STUUR");
            gameService.makeGuess(anyLong(), "STUUR"); // Multiple wrong guesses
        } catch (NotFoundException nfe) {
            nfe.getMessage();
        }


        assertDoesNotThrow(() -> gameService.makeGuess(anyLong(), "STUUR"));
    }

}