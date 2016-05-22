package test;

import org.junit.Test;
import org.junit.Assert;
import src.ThreadedCounter;
import java.util.List;

/**
 * This classes performs integeration tests on the {@link src.ThreadedCounter}
 * class.
 *
 * @author Javier Lores
 * Created May 21st, 2016
 */
public class ThreadedCounterTests {

    /**
     * This function tests to ensure that the
     * {@link src.ThreadedCounter#count(boolean, int, int ,int)} function
     * is working properly and counting in the appropriate range with the
     * appropriate step size.
     */
    @Test
    public void testCountIfRangeAndStepSizeCorrect() {
        int start = 0;
        int end = 100;
        int step = 1;

        // Create our counter and run our count function
        ThreadedCounter test = new ThreadedCounter(1);
        List<List<Integer>> results = test.count(false, start, end, step);

        // There is only one thread, so grab it
        List<Integer> set = results.get(0);
        for (int i = 0; i < set.size(); i++) {
            // Check the start range
            if (i == 0)
                Assert.assertEquals((Integer)set.get(i), (Integer)(start+1));
            // Check the end range
            else if (i == set.size())
                Assert.assertEquals((Integer)set.get(i), (Integer)end);
            // Check the step size
            else
                Assert.assertEquals((Integer)(set.get(i-1)+step), (Integer)set.get(i));
        }
    }
}
