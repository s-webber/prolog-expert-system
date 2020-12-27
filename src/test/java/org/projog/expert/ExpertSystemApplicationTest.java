package org.projog.expert;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.timing.Pause.pause;
import static org.assertj.swing.timing.Timeout.timeout;
import static org.junit.Assert.assertEquals;
import static org.projog.expert.Facts.NONE_OF_THE_ABOVE;

import java.awt.Frame;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.testng.testcase.AssertJSwingTestngTestCase;
import org.assertj.swing.timing.Condition;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/** Performs end-to-end tests of expert system using the UI. */
@Ignore // Ignoring these tests so they don't run as part of build. Take a while to run and also take control of mouse pointer.
public class ExpertSystemApplicationTest extends AssertJSwingTestngTestCase {
   private static final Logger LOG = Logger.getLogger(ExpertSystemApplicationTest.class.getName());

   private static final int TIMEOUT = 10000; // TODO shouldn't need to be so high, try lowering

   @Override
   protected void onSetUp() {
      application(ExpertSystemApplication.class).start();
   }

   @DataProvider
   private Bird[] allBirds() {
      return Bird.values();
   }

   @Test(dataProvider = "allBirds")
   public void individual_test(Bird bird) {
      assertBird(bird);
   }

   /**
    * Combines the smaller tests listed above into one test.
    * <p>
    * Tests that are no issues caused by using the application to identify multiple birds. Should be OK as the reset
    * button, clicked between each identification, should remove any state accumulated from a previous identification.
    */
   @Test
   public void uber_test() {
      List<Bird> all = Arrays.asList(Bird.values());
      Collections.shuffle(all);

      for (Bird bird : all) {
         assertBird(bird);
         clickReset();
      }
   }

   private final void assertBird(Bird bird) {
      LOG.info("assert " + bird);
      FrameFixture frame = createFrameFixture();

      // key = question, value = answer
      Map<String, String> factMap = bird.getFacts().entrySet().stream().collect(Collectors.toMap(//
                  e -> "What is the value for " + e.getKey() + "?", //
                  e -> e.getValue()));
      String previousQuestion = null;
      while (!factMap.isEmpty()) {
         String question = getLabelText(frame, "question");
         if (question.equals(previousQuestion)) {
            throw new RuntimeException();
         }

         String answer = factMap.containsKey(question) ? factMap.remove(question) : NONE_OF_THE_ABOVE;

         LOG.info(question + " = " + answer);
         clickAnswer(frame, answer);

         previousQuestion = question;
      }

      String expectedResult = getExpectedResult(bird);
      String actualResult = getLabelText(frame, "result");
      assertEquals(expectedResult, actualResult);
   }

   private String getExpectedResult(Bird bird) {
      Optional<String> name = bird.getName();
      if (name.isPresent()) {
         return "The bird is a " + name.get() + ".";
      } else {
         return "I can't identify that bird.";
      }
   }

   private FrameFixture createFrameFixture() {
      return findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
         protected boolean isMatching(Frame frame) {
            return frame.isShowing();
         }
      }).using(robot());
   }

   private String getLabelText(FrameFixture frame, String labelName) {
      pause(new Condition(labelName + " not found") {
         public boolean test() {
            try {
               frame.label(labelName);
               return true;
            } catch (ComponentLookupException e) {
               return false;
            }
         }
      }, timeout(TIMEOUT));

      return frame.label(labelName).target().getText();
   }

   private void clickAnswer(FrameFixture frame, String buttonName) {
      JButtonFixture button = frame.button(buttonName);
      assertEquals(buttonName, button.target().getText());
      pause(new Condition(buttonName + " still enabled") {
         public boolean test() {
            LOG.info("clicking " + buttonName);
            button.click();
            return !button.isEnabled();
         }
      }, timeout(TIMEOUT));
   }

   private void clickReset() {
      JButtonFixture button = createFrameFixture().button("reset");
      LOG.info("clicking " + button.text());
      button.click();
   }
}
