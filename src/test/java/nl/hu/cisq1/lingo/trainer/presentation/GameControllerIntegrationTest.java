package nl.hu.cisq1.lingo.trainer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.annotate.JsonIgnore;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.DTO.GuessDTO;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
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
    void beforeEach() {
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
    void startNewRound() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/lingoTrainer/"+ game_id +"/startRound");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.game_id", is(notNullValue())))
                .andExpect(jsonPath("$.gameState", is("IN_GAME")));
    }

//    @Test
//    @DisplayName("Test to make a guess")
//    void makeGuess() throws Exception {
//
//        GuessDTO guessDTO = new GuessDTO();
//        guessDTO.attempt = "STERK";
//        String body = new ObjectMapper().writeValueAsString(guessDTO);
//
//
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/lingoTrainer/"+ game_id +"/guess")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(body);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", notNullValue()));
//
//
//    }
}