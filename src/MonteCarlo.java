//import javax.swing.*;
//import java.awt.*;
//
//public class MonteCarlo extends JFrame {
//
//    public MonteCarlo() {
//        setTitle("Circle Inside Square GUI");
//        setSize(400, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        CircleInsideSquarePanel circleInsideSquarePanel = new CircleInsideSquarePanel();
//        add(circleInsideSquarePanel);
//
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new MonteCarlo());
//    }
//}
//
//class CircleInsideSquarePanel extends JPanel {
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        int squareSize = 150; // Adjust the size of the square
//        int squareX = (getWidth() - squareSize) / 2;
//        int squareY = (getHeight() - squareSize) / 2;
//
//        g.setColor(Color.BLUE);
//        g.fillRect(squareX, squareY, squareSize, squareSize); // Draw the square
//
//        int circleDiameter = squareSize;
//        int circleX = squareX + squareSize / 2 - circleDiameter / 2;
//        int circleY = squareY;
//
//        g.setColor(Color.RED);
//        g.fillOval(circleX, circleY, circleDiameter, circleDiameter); // Draw the circle
//    }
//}
//





//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class MonteCarlo extends JFrame {
//    private static final int FRAME_WIDTH = 600;
//    private static final int FRAME_HEIGHT = 600;
//
//    private static final int SQUARE_SIZE = 200;
//    private static final int CIRCLE_DIAMETER = 200;
//
//    private int pointY = 0;
//
//    public MonteCarlo() {
//        setTitle("Circle and Square Animation");
//        setSize(FRAME_WIDTH, FRAME_HEIGHT);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        Timer timer = new Timer(50, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                pointY += 5; // Adjust the speed of falling points
//
//                if (pointY > FRAME_HEIGHT) {
//                    pointY = 0;
//                }
//
//                repaint();
//            }
//        });
//
//        timer.start();
//
//        add(new AnimationPanel());
//
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//
//    private class AnimationPanel extends JPanel {
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//
//            drawSquare(g);
//            drawCircle(g);
//            drawFallingPoint(g);
//        }
//        private void drawSquare(Graphics g) {
//            g.setColor(Color.RED);
//            g.drawRect((FRAME_WIDTH - SQUARE_SIZE) / 2, (FRAME_HEIGHT - SQUARE_SIZE) / 2, SQUARE_SIZE, SQUARE_SIZE);
//        }
//
//        private void drawCircle(Graphics g) {
//            g.setColor(Color.BLUE);
//            g.drawOval((FRAME_WIDTH - CIRCLE_DIAMETER) / 2, (FRAME_HEIGHT - CIRCLE_DIAMETER) / 2, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
//        }
//
//        private void drawFallingPoint(Graphics g) {
//            g.setColor(Color.RED);
//            g.fillOval((FRAME_WIDTH - 10) / 2, pointY, 10, 10);
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new MonteCarlo());
//    }
//}










import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MonteCarlo extends JFrame {

    private MonteCarloPiEstimationPanel monteCarloPanel;

    public MonteCarlo() {
        setTitle("Monte Carlo π Estimation");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        monteCarloPanel = new MonteCarloPiEstimationPanel();
        add(monteCarloPanel, BorderLayout.CENTER);

        JButton estimateButton = new JButton("Estimate π");
        estimateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monteCarloPanel.estimatePi();
                monteCarloPanel.repaint();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(estimateButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MonteCarlo());
    }
}

class MonteCarloPiEstimationPanel extends JPanel {

    private int totalPoints = 0;
    private int pointsInsideCircle = 0;

    public void estimatePi() {
        Random random = new Random();

        int numPoints = 100000; // Adjust the number of points for a better estimation

        for (int i = 0; i < numPoints; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            double distance = Math.sqrt(x * x + y * y);

            if (distance <= 1.0) {
                pointsInsideCircle++;
            }

            totalPoints++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int squareSize = Math.min(getWidth(), getHeight());

        int squareX = (getWidth() - squareSize) / 2;
        int squareY = (getHeight() - squareSize) / 2;

        g.setColor(Color.BLUE);
        g.fillRect(squareX, squareY, squareSize, squareSize); // Draw the square

        g.setColor(Color.RED);
        g.fillArc(squareX, squareY, squareSize, squareSize, 0, 90); // Draw the quarter circle

        g.setColor(Color.BLACK);
        g.drawString("Total Points: " + totalPoints, 10, 20);
        g.drawString("Points Inside Circle: " + pointsInsideCircle, 10, 40);

        if (totalPoints > 0) {
            double piEstimation = 4.0 * pointsInsideCircle / totalPoints;
            g.drawString("π Estimate: " + piEstimation, 10, 60);
        }
    }
}


