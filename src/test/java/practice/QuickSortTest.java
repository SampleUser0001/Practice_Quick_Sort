package practice;

import java.util.Arrays;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;


/**
 * Unit test for simple App.
 */
public class QuickSortTest {
    private static Logger logger = LogManager.getLogger();

    private QuickSort sorter;
    @Before
    public void setup(){
        this.sorter = new QuickSort();
    }
    
    @Test
    public void sortRandomTest() {

        int[] list = new Random().ints().limit(1000).toArray();

        try {
            int[] sorted = this.sorter.sort(list);
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

    @Test
    public void sortTest_01(){
        check(new int[]{3,1,2});
    }
    @Test
    public void sortTest_02(){
        check(new int[]{1,3,2});
    }

    private void check(int[] list){
        try {
            int[] sorted = this.sorter.sort(list);
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

    private void printList(int[] list){
        for(int i=0 ; i<list.length ; i++) {
            logger.debug("{},{}", i, list[i]);
        }
    }
}
