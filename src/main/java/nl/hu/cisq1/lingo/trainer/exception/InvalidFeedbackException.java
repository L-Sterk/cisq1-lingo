package nl.hu.cisq1.lingo.trainer.exception;

public class InvalidFeedbackException extends RuntimeException {
    public InvalidFeedbackException(int feedbackLength, int attemptLength) {
        super("The length of the feedback is not the same as the length of the attempt: \n " +
                "Feedback: " + feedbackLength + "\n" +
                "Attempt: " + attemptLength);
    }
}
