package nl.hu.cisq1.lingo.trainer.presentation;

import javassist.NotFoundException;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.GameDTO;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.GuessDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lingoTrainer")
public class GameController {
    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/startGame")
    public GameDTO startNewGame() {
        Game game = gameService.startNewGame();
        return new GameDTO(
                game.getId(),
                game.getGameState()
        );
    }

    @PostMapping("{game_id}/startRound")
    public GameDTO startNewRound(@PathVariable(value = "game_id") Long game_id) throws NotFoundException {
        Game game = gameService.startNewRound(game_id);

        return new GameDTO(
                game.getId(),
                game.getGameState()
        );

    }

    @PostMapping("{game_id}/guess")
    public GameDTO makeGuess(@PathVariable(value = "game_id") Long game_id, @RequestBody GuessDTO guessDTO) throws NotFoundException {
        Game game = gameService.getGameById(game_id);
        gameService.makeGuess(game_id, guessDTO.attempt);

        return new GameDTO(
                game.getId(),
                game.getGameState()
        );
    }
}
