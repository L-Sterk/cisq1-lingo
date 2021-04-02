package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    WordService wordService = mock(WordService.class);
    SpringGameRepository springGameRepository = mock(SpringGameRepository.class);
    GameService gameService = new GameService(wordService, springGameRepository);

    @Test
    void getAllGames() {
    }

    @Test
    void getGameById() {
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