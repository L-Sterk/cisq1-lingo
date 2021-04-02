package nl.hu.cisq1.lingo.trainer.application;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Mark;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
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
    static Game game4 = new Game();

    static List<Game> gameList = new ArrayList<>();

    private static Stream<Arguments> provideGames(){
        return Stream.of(
                Arguments.of(game1, 11L),
                Arguments.of(game2, 22L),
                Arguments.of(game3, 33L),
                Arguments.of(game4, 44L)
        );
    }

    // --- TESTS ---

    @BeforeAll
    static void beforeAll() {
//        when(wordService.provideRandomWord(5)).thenReturn("APPEL");
//        when(wordService.provideRandomWord(anyInt())).thenReturn("APPEL");
//        verify(wordService, times(1)).provideRandomWord(5);

        // Add the four games to a list [getAllGames() method]
        when(springGameRepository.findAll()).thenReturn(List.of(game1, game2, game3, game4));

        // Give games id's [getGameById() method]
        when(springGameRepository.findById(11L)).thenReturn(java.util.Optional.ofNullable(game1));
        when(springGameRepository.findById(22L)).thenReturn(java.util.Optional.ofNullable(game2));
        when(springGameRepository.findById(33L)).thenReturn(java.util.Optional.ofNullable(game3));
        when(springGameRepository.findById(44L)).thenReturn(java.util.Optional.ofNullable(game4));

    }

    @Test
    @DisplayName("Check if the four games are in the list")
    void getAllGames() throws NotFoundException {
        gameList = gameService.getAllGames();
        assertEquals(4, gameList.size());
    }

    @ParameterizedTest
    @MethodSource("provideGames")
    @DisplayName("Check if the games can be found by id")
    void getGameById(Game game, Long id) throws NotFoundException {
        assertEquals(game, gameService.getGameById(id));
    }

    @Test
    void startNewGame() {
    }

    @Test
    void startNewRound() {
    }

    @Test
    void makeGuess() {
    }

}