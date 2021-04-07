package nl.hu.cisq1.lingo.trainer.presentation;

import net.minidev.json.annotate.JsonIgnore;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
@Transactional
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class GameControllerIntegrationTest {
    private Game game = new Game();
    private Long game_id;

    @Autowired
    GameController gameController;

    @Autowired
    GameService gameService;

    @Autowired
    @MockBean
    SpringGameRepository springGameRepository;

    @Autowired
    WordService wordService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("STERK");

        //when(game.getId()).thenReturn(anyLong());

        gameService = new GameService(wordService, springGameRepository);
        game = this.gameService.startNewGame();

        game_id = game.getId();
    }

//    @Test
//    void startNewGame() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/lingoTrainer/startGame");
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                ;
//
//    }

//    @Test
//    void cannotStartNewGame() throws Exception{
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/lingoTrainer/startGame");
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isNotFound());
//    }


}