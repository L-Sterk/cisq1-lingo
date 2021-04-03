package nl.hu.cisq1.lingo.trainer.presentation;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.GameDTO;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.RoundDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lingoTrainer")
public class GameController {
    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @RequestMapping("/startGame")
    public GameDTO startNewGame() {
        Game game = gameService.startNewGame();
        return new GameDTO(game);
    }

    @PostMapping
    @RequestMapping("{game_id}/startRound")
    public RoundDTO startNewRound(@PathVariable(value = "game_id") Long game_id) throws NotFoundException {
        Round round = gameService.startNewRound(game_id);
        return new RoundDTO(round);
    }

    @PostMapping
    @RequestMapping("{game_id}/guess")
    public GameDTO makeGuess(@PathVariable(value = "game_id") Long game_id, String attempt /* TODO: is possible with attemptDTO*/) throws NotFoundException {
        Game game = gameService.makeGuess(game_id, attempt);
        return new GameDTO(game);
    }
}
