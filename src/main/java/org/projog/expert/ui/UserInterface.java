package org.projog.expert.ui;

import java.util.List;
import java.util.Optional;

public interface UserInterface {
   String askQuestion(String attribute, List<String> possibleAnswers);

   void displayResultAndWait(Optional<String> result);
}
