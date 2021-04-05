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

class FeedbackTest {

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("STERK", "STAAR", "S....", List.of(Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.PRESENT), "ST..."),
                Arguments.of("STERK", "START", "ST...", List.of(Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.CORRECT, Mark.ABSENT), "ST.R."),
                Arguments.of("STERK", "STERF", "ST.R.", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT), "STER."),
                Arguments.of("STERK", "STERK", "STER.", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), "STERK")
        );
    }

    // --- TESTS ---
    @Test
    @DisplayName("Word is guessed if all letters are correct")
    void wordIsGuessed() {
        String attempt = "STERK";
        Feedback feedback = new Feedback(attempt, List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));

        assertTrue(feedback.isWordGuessed());

    }

    @Test
    @DisplayName("Word is not guessed if one of the letters is wrong")
    void wordIsNotGuessed() {
        String attempt = "STERK";
        Feedback feedback = new Feedback(attempt, List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT));

        assertFalse(feedback.isWordGuessed());

    }

    @Test
    @DisplayName("Guess is invalid if the word is illegal")
    void guessIsInvalid() {
        String attempt = "ABCDE";
        Feedback feedback = new Feedback(attempt, List.of(Mark.ILLEGAL, Mark.ILLEGAL, Mark.ILLEGAL, Mark.ILLEGAL, Mark.ILLEGAL));

        assertTrue(feedback.isGuessInvalid());
    }

    @Test
    @DisplayName("The length of the Feedback does not match the length of the attempt")
    void invalidFeedback() {
        String attempt = "STERK";
        assertThrows(
                InvalidFeedbackException.class,
                () -> new Feedback(attempt, List.of(Mark.CORRECT))
        );
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("Test method for giving Hints")
    void giveHintTest(String wordToGuess, String attempt, String previousHint, List<Mark> marksList, String nextHint) {
        Feedback feedback = new Feedback(attempt, marksList);

        assertEquals(nextHint, feedback.giveHint(previousHint, wordToGuess));
    }
}