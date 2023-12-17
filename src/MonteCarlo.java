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





import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.*;

public class MonteCarlo extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int NUM_POINTS = 100;

    private JLabel piLabel;
    private DrawingPanel drawingPanel;

    public MonteCarlo() {
        setTitle("Monte Carlo Pi Simulation");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        piLabel = new JLabel("Estimate of Pi: ");
        JButton startButton = new JButton("Start Simulation");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        drawingPanel = new DrawingPanel();

        setLayout(new BorderLayout());
        add(piLabel, BorderLayout.NORTH);
        add(startButton, BorderLayout.SOUTH);
        add(drawingPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void startSimulation() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Integer>> futures = new CopyOnWriteArrayList<>();

        for (int i = 0; i < NUM_POINTS; i++) {
            futures.add(executorService.submit(new MonteCarloTask()));
        }

        executorService.shutdown();

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                int insideCircleCount = 0;

                for (Future<Integer> future : futures) {
                    insideCircleCount += future.get();
                }

                double piEstimate = (4.0 * insideCircleCount) / NUM_POINTS;

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        piLabel.setText("Estimate of Pi: " + piEstimate);
                        drawingPanel.repaint();
                    }
                });

                return null;
            }
        };

        worker.execute();
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);

            // Draw square
            g.drawRect(50, 50, 400, 400);

            // Draw circle
            g.drawOval(50, 50, 400, 400);

            // Draw balls (points) based on simulation
            for (int i = 0; i < NUM_POINTS; i++) {
                double x = Math.random() * 400 + 50;
                double y = Math.random() * 400 + 50;

                if (x * x + y * y <= 40000) {
                    g.setColor(Color.RED); // Inside circle
                } else {
                    g.setColor(Color.BLUE); // Outside circle
                }

                g.fillOval((int) x - 2, (int) y - 2, 4, 4);
            }
        }
    }

    private class MonteCarloTask implements Callable<Integer> {
        @Override
        public Integer call() {
            double x = Math.random() * 400 + 50;
            double y = Math.random() * 400 + 50;

            // Check if the point is inside the unit circle
            if (x * x + y * y <= 40000) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MonteCarlo();
            }
        });
    }
}













