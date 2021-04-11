package nl.hu.cisq1.lingo.trainer.application;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GameService {
    private final WordService wordService;
    private final SpringGameRepository springGameRepository;

    public GameService(WordService wordService, SpringGameRepository springGameRepository) {
        this.wordService = wordService;
        this.springGameRepository = springGameRepository;
    }

    public List<Game> getAllGames() throws NotFoundException {
        List<Game> gameList = springGameRepository.findAll();
        if (gameList.isEmpty()) {
            throw new NotFoundException("No games were found.");
        } else {
            return gameList;
        }
    }

    public Game getGameById(Long id) throws NotFoundException {
        return springGameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game with id: " + id + " is not found."));
    }


    public Game startNewGame() {
        Game game = new Game();

        springGameRepository.save(game);

        return game;
    }

    public Game startNewRound(Long id) throws NotFoundException {
        Game game = getGameById(id);
        game.startNewRound(wordService.provideRandomWord(5));

        springGameRepository.save(game);

        return game;
    }

    public Game makeGuess(Long id, String attempt) throws NotFoundException {
        Game game = getGameById(id);
        game.makeGuess(attempt);

        springGameRepository.save(game);
        return game;
    }
}
