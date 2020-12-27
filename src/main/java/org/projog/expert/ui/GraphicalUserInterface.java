package org.projog.expert.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class GraphicalUserInterface extends JFrame implements UserInterface {
   private static final long serialVersionUID = 1L;

   public static UserInterface createUserInterface() {
      try {
         UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      } catch (Exception e) {
         e.printStackTrace(); // TODO use logger
      }
      UIManager.put("swing.boldMetal", Boolean.FALSE);

      UserInterface[] frame = new UserInterface[1];
      invokeAndWait(() -> {
         frame[0] = new GraphicalUserInterface();
      });
      return frame[0];
   }

   private GraphicalUserInterface() {
      super("Expert System");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
      setSize(380, 500);
      setVisible(true);
   }

   @Override
   public String askQuestion(String attribute, List<String> possibleAnswers) {
      BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

      invokeAndWait(() -> {
         QuestionPanel panel = new QuestionPanel(attribute, possibleAnswers, queue);
         replaceAll(panel);
      });

      try {
         // queue.take() will block until an answer is added to the queue by QuestionPanel
         return queue.take();
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public void displayResultAndWait(Optional<String> result) {
      CountDownLatch latch = new CountDownLatch(1);

      invokeAndWait(() -> {
         ResultPanel panel = new ResultPanel(result, latch);
         replaceAll(panel);
      });

      try {
         // latch.await() will block until reset button is clicked
         latch.await();
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
   }

   private void replaceAll(Component component) {
      getContentPane().removeAll();
      getContentPane().add(component, BorderLayout.CENTER);
      validate();
   }

   private static void invokeAndWait(Runnable runnable) {
      try {
         SwingUtilities.invokeAndWait(runnable);
      } catch (InvocationTargetException | InterruptedException e) {
         throw new RuntimeException(e);
      }
   }
}
