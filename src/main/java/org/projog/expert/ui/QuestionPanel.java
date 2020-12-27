package org.projog.expert.ui;

import static org.projog.expert.ui.Utils.createButton;
import static org.projog.expert.ui.Utils.createLabel;
import static org.projog.expert.ui.Utils.createVerticalSpacing;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Displays a question, and its possible answers, regarding the characteristic of the bird being identified. */
class QuestionPanel extends JPanel {
   private static final long serialVersionUID = 1L;

   QuestionPanel(String attribute, List<String> possibleAnswers, BlockingQueue<String> queue) {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      add(createQuestionLabel(attribute));

      for (String possible : possibleAnswers) {
         add(createVerticalSpacing());

         MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
               for (Component c : getComponents()) {
                  c.setEnabled(false);
               }
               queue.add(possible); // notify waiting code that an answer has been selected
            }
         };
         add(createAnswerButton(possible, mouseAdapter));
      }
   }

   private JLabel createQuestionLabel(String question) {
      return createLabel("What is the value for " + question + "?", "question");
   }

   private JButton createAnswerButton(String possible, MouseAdapter mouseAdapter) {
      return createButton(possible, possible, mouseAdapter);
   }
}
