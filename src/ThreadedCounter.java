package src;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;


/**
 * This class is a counter class that counts up to a specified number.
 * It does so by utilizing multiple threads to count. No parameters are
 * assigned to each thread, rather each thread races to count as many
 * of the number up to the specified number as possible.
 *
 * @author Javier Lores
 * Created on May 21, 2016
 */
public class ThreadedCounter {
    private ExecutorService executor; // Our thread executor to manage threads
    private int threadCount; // The number of threads to use for execution

    public static void main(String args[]) {
        ThreadedCounter counter = new ThreadedCounter(3);
        counter.count(true, 0, 100, 1);
    }

    public ThreadedCounter(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
        this.threadCount = threadCount;
    }

    /**
     * This function performs a counting operation by utilizing multiple
     * simultaneously running threads.
     *
     * @param verbosity whether or not to produce output to console
     * @param start the starting value for the counter
     * @param end the ending value for the counter
     * @param step the step value for the counter
     * @return a list of sets of values corresponding to the values
     *     counted by each thread
     */
    public List<List<Integer>> count(boolean verbosity, int start, int end, int step) {
        // Create worker calls the will be executed
        List<Callable<List<Integer>>> calls = new ArrayList<Callable<List<Integer>>>();
        for (int i = 0; i < this.threadCount; i++)
            calls.add(new IncrementCallable(verbosity, start, end, step));

        try {
            // Invoke calls to threads
            // This call will stay here until execution is complete
            // Once execution is complete, we shutdown our executor
            List<Future<List<Integer>>> futures = executor.invokeAll(calls);
            this.executor.shutdownNow();

            // Retrive the results for our threads
            List<List<Integer>> sets = new ArrayList<List<Integer>>();
            for (Future<List<Integer>> future : futures)
                sets.add(future.get());

            return sets;
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }
}

/**
 * The runnable to be utilized in a thread to perform the counting operation.
 *
 * @author Javier Lores
 * @date May 21st, 2016
 */
class IncrementCallable implements Callable<List<Integer>> {
    // Used to maintain a global counter among threads
    private static Integer count = null;
    // The starting  value for the counter
    private Integer start = null;
    // The ending value for the counter
    private Integer end = null;
    // The step size value for the counter
    private Integer step = null;
    // Whether or not to be verbose with results
    private boolean verbosity;
    // Used to store the values this specific thread has updated
    private List<Integer> values = new LinkedList<Integer>();

    public IncrementCallable(boolean verbosity, int start, int end, int step) {
        this.verbosity = verbosity;
        this.start = start;
        this.end = end;
        this.step = step;
    }

    /**
     * This function counts from {@code start} to {@code end} in steps of
     * {@code step}. Each time it updates the {@code count}, if
     * {@code verbosity) is set to true, the value is printed to the
     * console. Additionally, once the {@code count} has reached
     * {@code end} the values that this specific thread updated (stored in
     * {@code values}) are printed to the console (if {@code verbosity}
     * is set to true).
     */
    @Override
    public List<Integer> call() throws Exception {
        // Initialize the count
        if (this.count == null)
            this.count = this.start;

        // Begin incrementing the counter
        synchronized (this.count) {
            while (this.count < this.end) {
                // Increment old value
                this.count += this.step;

                // Add the new value to this threads collection of values
                this.values.add(this.count);

                // Output new value to console
                if (this.verbosity)
                    System.out.println(this.count);
            }
        }

        // Print this threads collection of values to the console
        if (this.verbosity)
            System.out.println(this.values.toString());

        return this.values;
    }
}
