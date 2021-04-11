package nl.hu.cisq1.lingo.trainer.presentation.DTO;

import javax.validation.constraints.NotNull;

public class GuessDTO {
    @NotNull
    public String attempt;

    public String getAttempt() {
        return attempt;
    }
}
