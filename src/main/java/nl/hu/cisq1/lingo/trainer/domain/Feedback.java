package nl.hu.cisq1.lingo.trainer.domain;


import nl.hu.cisq1.lingo.trainer.exception.InvalidFeedbackException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feedback")
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feedback_id")
    private Long id;

    @Column
    private String attempt;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Mark.class)
    private List<Mark> markList;

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

    public boolean isWordGuessed() {
        return this.markList.stream()
                .allMatch(markList -> markList == Mark.CORRECT);
    }

    public boolean isGuessValid() {
        return this.markList.stream()
                .noneMatch(mark -> mark == Mark.ILLEGAL);
    }

    public boolean isGuessInvalid() {
        return !isGuessValid();
    }

    public String giveHint(String previousHint, String wordToGuess) {
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
}
