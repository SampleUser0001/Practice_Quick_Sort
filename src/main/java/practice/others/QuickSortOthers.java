package practice.others;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuickSortOthers {
    private static Logger logger = LogManager.getLogger();

    // 配列dのleftからrightまでの間のデータ列をクイックソートする
    static void quick_sort(int[] d, int left, int right) {
        if (left>=right) {
            return;
        }
        int p = d[(left+right)/2];
        int l = left, r = right, tmp;
        while(l<=r) {
            while(d[l] < p) { l++; }
            while(d[r] > p) { r--; }
            if (l<=r) {
                tmp = d[l]; d[l] = d[r]; d[r] = tmp;
                l++; r--;
            }
        }
        quick_sort(d, left, r);  // ピボットより左側をクイックソート
        quick_sort(d, l, right); // ピボットより右側をクイックソート
    }
    // 配列内のデータ列を表示する
    static void print_data(double[] d) {
        for(int i = 0; i < d.length; i++) System.out.print(d[i] + " ");
        System.out.println();
    }
    public static void main(String[] args) {
        logger.info("app start.");

        int argsIndex = 0;
        int size = Integer.parseInt(args[argsIndex++]);

        int[] data = new Random().ints().limit(size).toArray();

        long start = System.currentTimeMillis();
        logger.info("sort start.");
        // print_data(data);
        quick_sort(data, 0, data.length-1);
        // print_data(data);
        logger.info("sort finish.");
        long finish = System.currentTimeMillis();
        logger.info("time : {}", finish-start);
    }
}
