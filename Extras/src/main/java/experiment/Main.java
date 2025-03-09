package experiment;

import java.util.Properties;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello Java World");
    Properties prop = System.getProperties();
    prop.entrySet().stream()
//        .filter(e -> e.getKey().equals("java.vm.version"))
        .forEach(System.out::println);

    Object obj = null;
    System.out.println(switch(obj) {
      case null -> "Nothing!";
      default -> "something";
    });
  }
}
