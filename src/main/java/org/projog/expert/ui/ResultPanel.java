package org.projog.expert.ui;

import static org.projog.expert.ui.Utils.createButton;
import static org.projog.expert.ui.Utils.createLabel;
import static org.projog.expert.ui.Utils.createVerticalSpacing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Displays the result of attempting to identify a bird. */
class ResultPanel extends JPanel {
   private static final long serialVersionUID = 1L;

   ResultPanel(Optional<String> result, CountDownLatch latch) {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      add(createResultLabel(result));
      add(createVerticalSpacing());
      add(createResetButton(latch));
   }

   private JLabel createResultLabel(Optional<String> result) {
      return createLabel(createText(result), "result");
   }

   private String createText(Optional<String> result) {
      if (result.isPresent()) {
         return "The bird is a " + result.get() + ".";
      } else {
         return "I can't identify that bird.";
      }
   }

   private JButton createResetButton(CountDownLatch latch) {
      return createButton("Search again.", "reset", new MouseAdapter() {
         @Override
         public void mouseClicked(final MouseEvent e) {
            setEnabled(false);
            latch.countDown(); // inform waiting code that button has been pressed
         }
      });
   }
}
