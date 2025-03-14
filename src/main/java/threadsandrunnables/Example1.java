package threadsandrunnables;

public class Example1 {
  private static int i = 0;
  public static void main(String[] args) {
    Runnable task = () -> {
      System.out.println("Task starting, thread name is "
          + Thread.currentThread().getName());
      for (; i < 10; i++) {
        System.out.println(Thread.currentThread().getName() + " i is " + i);
      }
      System.out.println("Task completing, thread name is "
          + Thread.currentThread().getName());
    };

    System.out.println("about to call run");
//    task.run();
    Thread t1 = new Thread(task);
    Thread t2 = new Thread(task);
    t1.start();
    t2.start();
    System.out.println("returned from run");
  }
}
