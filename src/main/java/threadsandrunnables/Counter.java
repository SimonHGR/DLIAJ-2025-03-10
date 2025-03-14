package threadsandrunnables;

public class Counter {
  private static /*volatile*/ long count = 0; // volatile DOES NOT FIX "atomicity"

  public static void main(String[] args) throws Throwable {
    Runnable task = () -> {
      for (int i = 0; i < 100_000_000; i++) {
        count++;
      }
    };

    Thread t1 = new Thread(task);
    Thread t2 = new Thread(task);
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(count);
  }
}
