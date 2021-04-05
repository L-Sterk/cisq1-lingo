package nl.hu.cisq1.lingo.trainer.domain;


import nl.hu.cisq1.lingo.trainer.exception.InvalidFeedbackException;

import javax.persistence.*;
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
    private List<Mark> markList = new ArrayList<>();

    @ElementCollection
    private List<String> hintList = new ArrayList<>();


    public Feedback(String attempt, List<Mark> markList) {
        if (attempt.length() != markList.size()) {
            throw new InvalidFeedbackException(attempt.length(), markList.size());
        }
        this.attempt = attempt;
        this.markList = markList;
    }

    public Feedback() {

    }

    public List<String> getHintList() {
        return hintList;
    }

    public List<Mark> getMarkList() {
        return markList;
    }

    public Mark isWordGuessedMark() {
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

    public boolean isWordGuessed(){
        return markList.stream()
                .allMatch(e -> e.equals(Mark.CORRECT));
    }

    public boolean isGuessValid(){
        return this.markList.stream()
                .noneMatch(mark -> mark == Mark.ILLEGAL);
    }

    public boolean isGuessInvalid(){
        return !isGuessValid();
    }

    public String giveHint(String previousHint, String wordToGuess) {
        if (attempt.length() != markList.size()) {
            throw new InvalidFeedbackException(attempt.length(), markList.size());
        }

        String[] splitWordToGuess = wordToGuess.split("");
        String[] splitPreviousHint = previousHint.split("");

        for (int i = 0; i < splitWordToGuess.length; i++) {
            if (markList.get(i) == Mark.CORRECT) {
                hintList.add(splitWordToGuess[i]);
            } else {
                hintList.add(splitPreviousHint[i]);
            }
        }

        return String.join("", hintList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(markList, feedback.markList);
    }
}
