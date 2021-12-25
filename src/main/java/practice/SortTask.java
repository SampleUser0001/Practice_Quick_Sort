package practice;

import java.lang.Runnable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SortTask implements Runnable {
    private Logger logger = LogManager.getLogger();

    private ExecutorService exec;
    private CountDownLatch countDownLatch;
    private double[] list;
    private final int headIndex;
    private final int tailIndex;

    public SortTask(ExecutorService exec, CountDownLatch countDownLatch, double[] list, int headIndex, int tailIndex) {
        this.exec = exec;
        this.countDownLatch = countDownLatch;
        this.list = list;
        this.headIndex = headIndex;
        this.tailIndex = tailIndex;
    }

    @Override
    public void run() {
        logger.debug("before devide : {}", this.printlog(this.headIndex, this.tailIndex));
        int boundaryIndex = this.divide();
        logger.debug("after devide : {}", this.printlog(this.headIndex, this.tailIndex));
        logger.debug("after devide : boundaryIndex : {}, headIndex : {}, tailIndex : {}", boundaryIndex, headIndex, tailIndex);

        // 要素数が2以上の場合は分割
        // 配列の小さい方
        if(boundaryIndex-headIndex > 1) {
            exec.submit(new SortTask(exec, countDownLatch, list, headIndex, boundaryIndex-1));
        } else {
            countDownLatch.countDown();
        }

        // 配列の大きい方
        if(tailIndex-boundaryIndex+1 > 1) {
            exec.submit(new SortTask(exec, countDownLatch, list, boundaryIndex, tailIndex));
        } else {
            countDownLatch.countDown();
        }

    }

    /**
     * 基準値より小さい値を添え字が小さい方に、大きい方を添え字が大きい方に寄せる
     * @return 小さい方のうち、一番大きい添字を返す。
     */
    private int divide() {
        double baseValue = (this.list[this.headIndex] + this.list[tailIndex]) / 2 ;
        int index[] = {this.headIndex, this.tailIndex};
        while(index[0] != index[1]) {
            index = this.swap(baseValue, index[0], index[1]);
        }
        return index[0];
    }

    /**
     * メンバ変数のlistから、基準値より小さい値と大きい値を探して入れ替える。
     * 同値は小さい方に寄せる。
     * @param baseValue 基準値
     * @param head 小さい側のインデックス
     * @param tail 大きい側のインデックス
     * @return
     */
    private int[] swap(double baseValue, int head, int tail) {

        logger.debug("baseValue : {} , head : {} , tail : {}", baseValue, head, tail);

        logger.debug("before search : head : {} , this.list[head] : {}", head, this.list[head]);
        while(head < tail){
            logger.trace("head : {} , this.list[head] : {}", head, this.list[head]);
            if (this.list[head] >= baseValue){
                logger.trace("head : break");
                break;
            } else {
                head++;
            }
        }
        logger.debug("after search : head : {}", head);

        logger.debug("before search : tail : {} , this.list[tail] : {}", tail, this.list[tail]);
        while(head < tail){
            logger.trace("tail : {} , this.list[tail] : {}", tail, this.list[tail]);
            if (this.list[tail] < baseValue){
                logger.trace("tail : break");
                break;
            } else {
                tail--;
            }
        }
        logger.debug("after search : tail : {}", tail);
        
        logger.debug("head: index {} , value : {}", head, list[head]);
        logger.debug("tail: index {} , value : {}", tail, list[tail]);

        double tmp = list[head];
        list[head] = list[tail];
        list[tail] = tmp;

        return new int[]{head, tail};
    }

    private String printlog(int head, int tail) {
        StringBuffer buf = new StringBuffer();
        buf.append("headIndex : " + head + " , tailIndex : " + tail + " , ");
        buf.append("list : [");
        for(int i = head ; i<=tail ; i++) {
            buf.append(this.list[i]);
            if(i != tail){
                buf.append(", ");
            }
        }
        buf.append("]");
        return buf.toString();
    }
}
