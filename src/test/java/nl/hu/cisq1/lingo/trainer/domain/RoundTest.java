package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    // --- TESTS ---

    @Test
    @DisplayName("If all letters are correct, the word is guessed")
    void guess() {
        Round round = new Round("STERK");

        assertEquals(List.of(Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.PRESENT), round.guess("STAAR").getMarkList());
        assertEquals(List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT), round.guess("STERF").getMarkList());
        assertEquals(List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), round.guess("STERK").getMarkList());
    }


    @Test
    @DisplayName("If guess is illegal")
    void invalidGuess() {
        Round round = new Round("STERK");
        assertThrows(InvalidFeedbackException.class, () -> round.guess("ABCDEF"));
    }

    @Test
    @DisplayName("Check if feedback is the same feedback object")
    void checkFeedback() {
        Round round = new Round("STERK");


        assertEquals(round.guess("BAARD"), round.getLastFeedback());
    }

    @Test
    @DisplayName("Test if feedback is created")
    void getFeedbackList() {
        Round round = new Round("STERK");
        round.guess("BAARD");

        assertEquals(round.getFeedbackList().size(), 1);
    }
}