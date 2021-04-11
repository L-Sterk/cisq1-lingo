package nl.hu.cisq1.lingo.trainer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import javassist.NotFoundException;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.GuessDTO;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.when;


@SpringBootTest
@Transactional
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    GameController gameController;

    @Autowired
    WordService wordService;

    Long game_id;

    @BeforeEach
    void beforeEach(){
        game_id = gameController.startNewGame().getGame_id();

    }

    @Test
    @DisplayName("Test to start a game")
    void startNewGame() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/lingoTrainer/startGame");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.game_id", is(notNullValue())))
                .andExpect(jsonPath("$.gameState", is("END_GAME")))
        ;
    }

    @Test
    @DisplayName("Test to start a new round")
    void startNewRound() throws Exception {
        startNewGame();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/lingoTrainer/" + game_id + "/startRound");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.game_id", is(notNullValue())))
                .andExpect(jsonPath("$.gameState", is("IN_GAME")));
    }

    @Test
    @DisplayName("Test to make a valid guess")
    void makeGuess() throws Exception {
        startNewGame();
        startNewRound();

        GuessDTO guessDTO = new GuessDTO();
        guessDTO.attempt = "STERK";
        String body = new ObjectMapper().writeValueAsString(guessDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/lingoTrainer/"+ game_id +"/guess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.game_id", notNullValue()));
    }
}