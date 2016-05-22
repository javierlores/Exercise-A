package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import test.ThreadedCounterTests;


/**
 * This classes runs the tests located {@link test.ThreadedCounterTests}
 * for the {@link src.ThreadedCounter} class.
 *
 * @author Javier Lores
 * Created on May 21, 2016
 */
public class ThreadedCounterTestsRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ThreadedCounterTests.class);
        for (Failure failure : result.getFailures())
            System.out.println(failure.toString());
        System.out.println(result.wasSuccessful());
    }
}
