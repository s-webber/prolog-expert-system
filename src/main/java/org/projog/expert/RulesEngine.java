package org.projog.expert;

import static java.util.stream.Collectors.toList;
import static org.projog.core.term.ListUtils.toJavaUtilList;
import static org.projog.core.term.TermUtils.getAtomName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.projog.api.Projog;
import org.projog.api.QueryPlan;
import org.projog.core.predicate.AbstractSingleResultPredicate;
import org.projog.core.predicate.PredicateKey;
import org.projog.core.term.Atom;
import org.projog.core.term.Term;
import org.projog.core.term.TermUtils;
import org.projog.expert.ui.UserInterface;

class RulesEngine {
   /** A reference to the expert system in order to display the question and answers. */
   private final UserInterface ui;
   private final AskUserPredicate askUserPredicate;
   private final QueryPlan birdQuery;

   RulesEngine(UserInterface ui) {
      this.ui = ui;
      this.askUserPredicate = new AskUserPredicate();
      Projog projog = createProjog(askUserPredicate);
      this.birdQuery = projog.createPlan("bird(Bird).");
   }

   private static Projog createProjog(AskUserPredicate askUserPredicate) {
      Projog projog = new Projog();
      projog.addPredicateFactory(new PredicateKey("menuask", 3), askUserPredicate);
      projog.consultResource("birds.pl");
      return projog;
   }

   /**
    * Executes a query to determine a species of birds. Prompts the user to specify the bird's characteristics and then
    * displays the result.
    * <p>
    * Note that this is not thread-safe. It is intended to be run in parallel.
    */
   public void identifyBird() {
      askUserPredicate.reset();

      Optional<String> result = birdQuery.createStatement().findFirstAsOptionalAtomName();
      ui.displayResultAndWait(result);
   }

   /**
    * A Prolog built-in predicate which prompts the user for an answer to a question.
    * <p>
    * See: <a href="http://projog.org/extending-prolog-with-java.html">Adding Extra Functionality to Prolog Using
    * Java</a>
    */
   private class AskUserPredicate extends AbstractSingleResultPredicate {
      /** An answer that is always available for when none of the other answers are appropriate. */
      private static final String NONE_OF_THE_ABOVE = "none of the above";

      /**
       * Keeps track what questions have already been asked, and the answer that the user selected.
       * <p>
       * Key = Attribute, Value = Answer selected by user
       */
      private final Map<Term, Term> known = new HashMap<>();

      /**
       * @param attribute the attribute that the user is asked to select a value for (e.g. "size", "voice")
       * @param expectedAnswer the answer (contained in {@code possibleAnswers} that the calling code requires the user
       * to select in order for this predicate to succeed
       * @param possibleAnswers a {@code org.projog.term.List} of atoms representing each of the possible answers (e.g.
       * if {@code attribute} is "size" then the possible answers would be [large,plump,medium,small])
       * @return {@code true} if the value selected by the user matches {@code expectedAnswer}, else {@code false}
       */
      @Override
      public boolean evaluate(Term attribute, Term expectedAnswer, Term possibleAnswers) {
         // If this question has already been asked then use the answer that was previously selected,
         // else display the attribute and possible answers to the user and wait for them to select an answer.
         if (!known.containsKey(attribute)) {
            displayMenu(attribute, possibleAnswers);
         }

         // Attempt to unify the answer expected in the Prolog code with the answer selected by the user.
         return expectedAnswer.unify(known.get(attribute));
      }

      private void displayMenu(Term attribute, Term possibleAnswers) {
         String answer = ui.askQuestion(getAtomName(attribute), getPossibleAnswers(possibleAnswers));
         known.put(attribute, new Atom(answer));
      }

      private List<String> getPossibleAnswers(Term prologListOfPossibleAnswers) {
         List<String> result = toJavaUtilList(prologListOfPossibleAnswers).stream().map(TermUtils::getAtomName).collect(toList());
         result.add(NONE_OF_THE_ABOVE);
         return result;
      }

      /** Remove record of any already asked questions and their selected answers. */
      private void reset() {
         known.clear();
      }
   }
}
