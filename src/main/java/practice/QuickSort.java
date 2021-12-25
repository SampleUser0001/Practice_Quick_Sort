package practice;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * クイックソートする
 */
public class QuickSort {
    private static Logger logger = LogManager.getLogger();

    private ExecutorService exec;

    public QuickSort() {
        exec = Executors.newCachedThreadPool();
    }

    public double[] sort(double[] list) throws InterruptedException {
        double work[] = new double[list.length];
        CountDownLatch countDownLatch = new CountDownLatch(list.length);
        for(int i=0; i<list.length ;i++){
            work[i] = list[i];
        }
        exec.submit(new SortTask(exec, countDownLatch, work, 0, work.length-1));

        countDownLatch.await();

        exec.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            if (!exec.awaitTermination(60, TimeUnit.SECONDS)) {
                exec.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!exec.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
                }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            exec.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }

        return work;
    }

    public static void main( String[] args ) throws InterruptedException {
        logger.info("app start.");
        int argsIndex = 0;
        QuickSort sorter = new QuickSort();

        int size = Integer.parseInt(args[argsIndex++]);
        double[] list = new Random().doubles().limit(size).toArray();

        logger.info("sort start.");
        double[] sorted = sorter.sort(list);
        logger.info("sort finish.");

        logger.info("before : ");
        for(int i=0 ; i<list.length ; i++){
            logger.info("{} : {} ", i, list[i]);
        }
        logger.info("after : ");
        for(int i=0; i<sorted.length ; i++){
            logger.info("{} : {} ", i, sorted[i]);
        }
        logger.info("app finish.");
       
    }
}
