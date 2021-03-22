package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    @DisplayName("If all letters are correct, the word is guessed")
    void guess() {
        Round round = new Round(1, "STERK", GameState.IN_GAME);

        assertEquals(List.of(Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.PRESENT), round.guess("STAAR").getMarkList());
//        assertEquals(List.of(Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.CORRECT, Mark.ABSENT), round.guess("START").getMarkList());
        assertEquals(List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT), round.guess("STERF").getMarkList());
        assertEquals(List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), round.guess("STERK").getMarkList());

    }
}