package reduction;

import java.util.stream.Stream;

public class Example {
  public static void main(String[] args) {
    var sum = Stream.iterate(1, x -> x + 1)
        .limit(100)
        .reduce(0, (a, b) -> a + b);
    // three variants of reduce (one is relevant to producing a result of a different type)
    System.out.println(sum);
  }
}
