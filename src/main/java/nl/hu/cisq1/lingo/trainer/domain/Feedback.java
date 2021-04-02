package nl.hu.cisq1.lingo.trainer.domain;


import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import javax.persistence.*;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feedback_id")
    private Long id;

    @Column
    private String attempt;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Mark.class)
    private List<Mark> markList;

    public Feedback(String attempt, List<Mark> markList) {
        if (attempt.length() != markList.size()) {
            throw new InvalidFeedbackException(attempt.length(), markList.size());
        }
        this.attempt = attempt;
        this.markList = markList;
    }

    public Feedback() {

    }

    public List<Mark> getMarkList() {
        return markList;
    }

    public Mark isWordGuessed() {
        for (Mark mark : markList) {
            if (mark != Mark.CORRECT) {
                if (mark == Mark.ILLEGAL) {
                    return Mark.ILLEGAL;
                } else {
                    return Mark.ABSENT;
                }
            }
        }
        return Mark.CORRECT;
    }

    public String giveHint(String previousHint, String wordToGuess) {
        if (attempt.length() != markList.size()) {
            throw new InvalidFeedbackException(attempt.length(), markList.size());
        }

        String[] splitWordToGuess = wordToGuess.split("");
        String[] splitPreviousHint = previousHint.split("");

        List<String> hintList = new ArrayList<>();

        for (int i = 0; i < splitWordToGuess.length; i++) {
            if (markList.get(i) == Mark.CORRECT) {
                hintList.add(splitWordToGuess[i]);
            } else {
                hintList.add(splitPreviousHint[i]);
            }
        }

        return String.join("", hintList);
    }

//    @Override
//    public String toString() {
//
//        return markList.toString();
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(markList, feedback.markList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, markList);
    }
}
