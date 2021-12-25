package practice;

import java.util.Arrays;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;


/**
 * Unit test for simple App.
 */
public class QuickSortTest {
    private static Logger logger = LogManager.getLogger();
    
    @Test
    public void sortTest() {
        QuickSort sorter = new QuickSort();

        double[] list = new Random().doubles().limit(1000).toArray();

        try {
            double[] sorted = sorter.sort(list);
            Arrays.sort(list);

            logger.debug("actural");
            printList(sorted);
            logger.debug("collect");
            printList(list);


            for(int i=0; i<list.length ; i++){
                assertThat(sorted[i], equalTo(list[i]));
            }
        } catch(InterruptedException e){
            fail(e.toString());
        }
    }

    private void printList(double[] list){
        for(int i=0 ; i<list.length ; i++) {
            logger.debug("{},{}", i, list[i]);
        }
    }
}
