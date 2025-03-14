package threadsandrunnables;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProdCons {
  public static void main(String[] args) throws Throwable {
    BlockingQueue<int[]> queue = new ArrayBlockingQueue<>(10);
    Runnable producer = () -> {
      System.out.println("producer starting");
      try {
        for (int i = 0; i < 10_000; i++) {
          int[] data = {0, i}; // transactionally "bad"
          if (i < 500) {
            Thread.sleep(1);
          }
          data[0] = i;
          if (i == 5_000) data[0] = -1; // TEST THE TEST
          queue.put(data);
          data = null; // MUST NOT USE THIS OBJECT AGAIN!!! IT"S SHARED
        }
      } catch (InterruptedException ie) {
        System.out.println("unexpected interrupt");
      }
      System.out.println("producer completed");
    };
    Runnable consumer = () -> {
      System.out.println("Consumer starting");
      try {
        for (int i = 0; i < 10_000; i++) {
          int [] d = queue.take();
          if (d[0] != d[1] || d[0] != i) {
            System.out.println("**** ERROR at index " + i + ": " + Arrays.toString(d));
          }
          if (i > 9_500) {
            Thread.sleep(1);
          }
        }
      } catch (InterruptedException ie) {
        System.out.println("unexpected interrupt");
      }
      System.out.println("Consumer completed");
    };
    Thread prod = new Thread(producer);
    Thread cons = new Thread(consumer);
    prod.start();
    cons.start();
    prod.join();
    cons.join();
    System.out.println("All finished");
  }
}
