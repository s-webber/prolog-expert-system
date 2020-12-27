package org.projog.expert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.projog.expert.Facts.NONE_OF_THE_ABOVE;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.projog.expert.ui.UserInterface;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/** Tests RulesEngine directly rather than via the UI. */
public class RulesEngineTest {
   @DataProvider
   private Bird[] allBirds() {
      return Bird.values();
   }

   @Test(dataProvider = "allBirds")
   public void test(Bird bird) {
      FakeUserInterface fakeUserInterface = new FakeUserInterface(bird);

      RulesEngine rulesEngine = new RulesEngine(fakeUserInterface);
      rulesEngine.identifyBird();

      fakeUserInterface.verifyResult();
   }

   private static final class FakeUserInterface implements UserInterface {
      final String expectedResult;
      final Map<String, String> facts;
      Optional<String> actualResult;

      FakeUserInterface(Bird bird) {
         this.expectedResult = bird.getName().orElse(null);
         this.facts = bird.getFacts();
      }

      @Override
      public String askQuestion(String attribute, List<String> possibleAnswers) {
         String answer = facts.containsKey(attribute) ? facts.remove(attribute) : NONE_OF_THE_ABOVE;
         assertTrue("Missing: " + answer, possibleAnswers.contains(answer));
         return answer;
      }

      @Override
      public void displayResultAndWait(Optional<String> result) {
         this.actualResult = result;
      }

      void verifyResult() {
         assertNotNull(actualResult);
         assertEquals(expectedResult, actualResult.orElse(null));
      }
   }
}
