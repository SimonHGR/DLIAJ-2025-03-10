package superiterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.function.Predicate;
import java.lang.Runnable;
import java.util.concurrent.Callable;
import java.lang.Comparable;
import java.util.Comparator;
import java.util.stream.Stream;

record Student(String name, double gpa, List<String> courses) {
  public static Student of(String name, double gpa, String... courses) {
    return new Student(name, gpa, List.of(courses));
  }

  Student {
    if (!isValid(name, gpa, courses)) throw new IllegalArgumentException("bad student params");
  }

  public static boolean isValid(String name, double gpa, List<String> courses) {
    return name != null && name.length() > 0; // and more
  }
}

public class SuperIterable<E> implements Iterable<E> {
  private Iterable<E> self;
  public SuperIterable(Iterable<E> self) {
    this.self = self;
  }
  // this is forEach -- but it's already defined in Iterable :)
//  public void forEach(Consumer<E> op) {
//    for (E e : this.self) {
//      op.accept(e);
//    }
//  }

  public <F> SuperIterable<F> map(/*SuperIterable<E> this, */Function<E, F> op) {
    List<F> res = new ArrayList<>();
    for (E e : this.self) {
      F f = op.apply(e);
      res.add(f);
    }
    return new SuperIterable<>(res);
  }

  public SuperIterable<E> filter(Predicate<E> crit) {
    List<E> res = new ArrayList<>();
    for (E e : this.self) {
      if (crit.test(e)) {
        res.add(e);
      }
    }
    return new SuperIterable<>(res);
  }

  public Iterator<E> iterator() {
    return this.self.iterator();
  }
}

class Example {
  public static void main(String[] args) {
    SuperIterable<Student> roster = new SuperIterable<>(List.of(
        Student.of("Ishan", 2.7, "Art", "History"),
        Student.of("Inaya", 3.7, "Math", "Statistics", "Physics", "Quantum Mechnics"),
        Student.of("Ayo", 3.2, "Math"),
        Student.of("Siobhan", 3.5, "Math", "Physics", "Chemistry"),
        Student.of("Hua", 3.9, "Math", "Physics")
    ));

    System.out.println("All: -------------------------------");
    roster.forEach(s -> System.out.println(s));

    System.out.println("Smart: -------------------------------");
    roster
        .filter(s -> s.gpa() > 3.0)
        .forEach(s -> System.out.println(s));

    System.out.println("Grades of smart: -------------------------------");
    roster
        .filter(s -> s.gpa() > 3.0)
        .map(s -> "Student " + s.name() + " has grade " + s.gpa())
        .forEach(s -> System.out.println(s));

    System.out.println("Grades of smart: -------------------------------");
    for (Student s : roster) {
      if (!(s.gpa() > 3.0)) {
        continue;
      }
      String msg = "Student " + s.name() + " has grade " + s.gpa();
      System.out.println(msg);
    }
  }
}

class Example2 { // use stream!
  public static void main(String[] args) {
    List<Student> roster = List.of(
        Student.of("Ishan", 2.7, "Art", "History"),
        Student.of("Inaya", 3.7, "Math", "Statistics", "Physics", "Quantum Mechnics"),
        Student.of("Ayo", 3.2, "Math"),
        Student.of("Siobhan", 3.5, "Math", "Physics", "Chemistry"),
        Student.of("Hua", 3.9, "Math", "Physics")
    );

    System.out.println("All: -------------------------------");
    roster.stream().forEach(s -> System.out.println(s));

    System.out.println("Smart: -------------------------------");
    roster
        .stream()
        .filter(s -> s.gpa() > 3.0)
        .forEach(s -> System.out.println(s));

    System.out.println("Grades of smart: -------------------------------");
    roster
        .stream()
        .filter(s -> s.gpa() > 3.0)
        .map(s -> "Student " + s.name() + " has grade " + s.gpa())
        .forEach(s -> System.out.println(s));

    System.out.println("Infinite: -------------------------------");
    boolean somethingDividesBy29 = Stream.iterate(1, x -> x + 1)
        .peek(x -> System.out.println("upstream peek sees: " + x))
        .limit(100)
        .filter(x -> x % 2 == 0)
        .peek(x -> System.out.println("downstream peek sees: " + x))
        .anyMatch(x -> x % 29 == 0);
//        .map(x -> x + " is an even number!")
//        .forEach(x -> System.out.println(x));
    System.out.println("divisible by 29? " + somethingDividesBy29);
  }
}