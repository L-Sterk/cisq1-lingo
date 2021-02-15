Feature: Training for Lingo
  As a Lingo player,
  I want to practice Lingo by using the trainer
  In order to easily win the game

Feature: Start a new Lingo game
  As a Lingo player,
  I want to start a game of Lingo
  In order to practice the game

Feature: Guessing a word
  As a Lingo player,
  I want to guess a 5, 6 or 7 letter word
  In order to receive feedback after my guess

Scenario: Start a new Lingo game
  When: I start a new game
  Then: The first letter of the 5, 6 or 6 letter word shows

Scenario: Guessing a word
  When: I guess a word by spelling out the letters
  Then: The correct letters stay in the right place (Red outline), the letters guessed but not on the right place will have a yellow outline

Scenario Outline: Guessing a word
  Given: The <word> to guess
  When: I attempt to guess the <word> as <entry>
  Then: I receive an <feedback> output

  Examples:
    | word | entry | feedback
    | STERK | STAAL | CORRECT, CORRECT, NOT_PRESENT, NOT_PRESENT, NOT_PRESENT
    | STERK | STEEK | CORRECT, CORRECT, CORRECT, NOT_PRESENT, CORRECT
    | STERK | STERK | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT
