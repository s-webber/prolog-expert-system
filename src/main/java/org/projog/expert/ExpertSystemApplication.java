package org.projog.expert;

import org.projog.expert.ui.GraphicalUserInterface;
import org.projog.expert.ui.UserInterface;

/**
 * An expert system for identifying birds.
 * <p>
 * The UI is implemented in Java and the rules implemented in Prolog. The open source Projog library is used to
 * integrate Java with Prolog.
 * </p>
 * <img src="doc-files/PrologExpertSystem.png">
 */
public class ExpertSystemApplication {
   private final RulesEngine rulesEngine;

   private ExpertSystemApplication(UserInterface ui) {
      this.rulesEngine = new RulesEngine(ui);
   }

   private void identifyBirds() {
      while (true) {
         rulesEngine.identifyBird();
      }
   }

   public static void main(String[] args) {
      UserInterface ui = GraphicalUserInterface.createUserInterface();
      ExpertSystemApplication app = new ExpertSystemApplication(ui);
      new Thread(app::identifyBirds).start();
   }
}
