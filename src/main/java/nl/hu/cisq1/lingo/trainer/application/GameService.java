package nl.hu.cisq1.lingo.trainer.application;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService{
    private WordService wordService;
    private SpringGameRepository springGameRepository;

    public GameService(WordService wordService, SpringGameRepository springGameRepository) {
        this.wordService = wordService;
        this.springGameRepository = springGameRepository;
    }

    public List<Game> getAllGames() throws NotFoundException {
        List<Game> gameList = springGameRepository.findAll();
        if (gameList.isEmpty()){
            throw new NotFoundException("No games were found");
        }else{
            return gameList;
        }
    }

    public Optional<Game> getGameById(Long id) throws NotFoundException {
        Optional<Game> game = springGameRepository.findById(id);
                if (game.isEmpty()){
            throw new NotFoundException("Game with id: " + id + " is not found.");
        }else {
            return game;
        }
    }


    public void startNewGame(){
        Game game = new Game();


    }
}
