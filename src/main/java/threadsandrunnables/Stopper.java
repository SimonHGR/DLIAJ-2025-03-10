package threadsandrunnables;

public class Stopper {
  private static volatile boolean stop = false;

  public static void main(String[] args) throws InterruptedException {
    Runnable task = () -> {
      System.out.println(Thread.currentThread().getName() + " startup");
      while (! stop)
        ;
      System.out.println(Thread.currentThread().getName() + " shutdown");
    };

    new Thread(task).start();
    Thread.sleep(2000);
    System.out.println("main thread setting stop flag");
    stop = true;
    System.out.println("main completed, stop is " + stop);
  }
}
