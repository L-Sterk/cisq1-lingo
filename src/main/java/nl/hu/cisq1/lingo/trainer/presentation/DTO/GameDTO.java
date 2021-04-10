package nl.hu.cisq1.lingo.trainer.presentation.DTO;

import nl.hu.cisq1.lingo.trainer.domain.GameState;

public class GameDTO {
    private  Long game_id;
    private int score;
    private GameState gameState;

    public GameDTO(Long game_id, GameState gameState) {
        this.game_id = game_id;
        this.gameState = gameState;
    }

    public Long getGame_id() {
        return game_id;
    }

    public GameState getGameState() {
        return gameState;
    }

}