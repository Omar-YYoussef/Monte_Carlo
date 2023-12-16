import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.Scanner;


public class MonteCarloPiEstimation {

    static class MonteCarloTask implements Callable<Integer> {
        private final int numPoints;

        public MonteCarloTask(int numPoints) {
            this.numPoints = numPoints;
        }

        @Override
        public Integer call() {
            int pointsInsideCircle = 0;
            for (int i = 0; i < numPoints; i++) {
                double x = ThreadLocalRandom.current().nextDouble();
                double y = ThreadLocalRandom.current().nextDouble();
                double distance = x * x + y * y;
                if (distance <= 1) {
                    pointsInsideCircle++;
                }

                // Calculate and print the estimation of Pi at each step
                double pi = 4.0 * ((double) pointsInsideCircle / (i + 1));
                System.out.println("Estimation of Pi at step " + (i + 1) + " = " + pi);
            }
            return pointsInsideCircle;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the total number of points: ");
        int totalPoints = scanner.nextInt();

        // Close the scanner
        scanner.close();
        int numTasks = 5; // Number of parallel tasks
//        int totalPoints = 20; // Total number of points for all tasks
        int pointsPerTask = totalPoints / numTasks;

        ExecutorService executorService = Executors.newFixedThreadPool(numTasks);
        List<Future<Integer>> futures = new ArrayList<>();

        // Submit tasks to the executor
        for (int i = 0; i < numTasks; i++) {
            Callable<Integer> task = new MonteCarloTask(pointsPerTask);
            Future<Integer> future = executorService.submit(task);
            futures.add(future);
        }

        // Wait for all tasks to complete and accumulate results
        int totalPointsInsideCircle = 0;
        for (Future<Integer> future : futures) {
            try {
                totalPointsInsideCircle += future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Shut down the executor
        executorService.shutdown();

        // Calculate the final estimation of Pi
        double pi = 4.0 * ((double) totalPointsInsideCircle / totalPoints);
        System.out.println("Final Estimation of Pi = " + pi);
    }
}

