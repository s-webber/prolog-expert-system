package org.projog.expert.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;

/** Provides static helper methods for constructing the UI. */
final class Utils {
   private static final Dimension VERTICAL_SPACING = new Dimension(0, 10);
   private static final Dimension BUTTON_SIZE = new Dimension(300, 25);

   private Utils() {
   }

   static Component createVerticalSpacing() {
      return Box.createRigidArea(VERTICAL_SPACING);
   }

   static JLabel createLabel(String text, String name) {
      JLabel questionLabel = new JLabel(text);
      questionLabel.setName(name);
      questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      return questionLabel;
   }

   static JButton createButton(String text, String name, MouseAdapter mouseAdapter) {
      JButton b = new JButton(text);
      b.setName(name);
      b.setAlignmentX(Component.CENTER_ALIGNMENT);
      b.setMaximumSize(BUTTON_SIZE);
      b.addMouseListener(mouseAdapter);
      return b;
   }
}
