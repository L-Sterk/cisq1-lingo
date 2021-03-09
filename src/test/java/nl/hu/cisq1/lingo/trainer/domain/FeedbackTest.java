package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @Test
    @DisplayName("Word is guessed if all letters are correct")
    void wordIsGuessed(){
        String attempt = "STERK";
        Feedback feedback = new Feedback(attempt, List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));

        assertTrue(feedback.isWordGuessed() == Mark.CORRECT);

    }

    @Test
    @DisplayName("Word is not guessed if one of the letters is wrong")
    void wordIsNotGuessed(){
        String attempt = "STERK";
        Feedback feedback = new Feedback(attempt, List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.WRONG));

        assertTrue(feedback.isWordGuessed() == Mark.WRONG);

    }

    @Test
    @DisplayName("Guess is invalid if the word is illegal")
    void guessIsInvalid(){
        String attempt = "ABCDE";
        Feedback feedback = new Feedback(attempt, List.of(Mark.ILLEGAL,Mark.ILLEGAL,Mark.ILLEGAL,Mark.ILLEGAL,Mark.ILLEGAL));

        assertTrue(feedback.isWordGuessed() == Mark.ILLEGAL);
    }
}