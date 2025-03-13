package students7;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

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

//interface Predicate<E> {
//  boolean test(E s);
//}

interface Function<A, R> {
  R apply(A a);
}
//interface UnaryOperator<E> {
//  E apply(E s);
//}

//interface Consumer<E> {
//  void accept(E s);
//}

public class Example {

  public static <E, F> List<F> map(List<E> items, Function<E, F> operation) {
    List<F> results = new ArrayList<>();
    for (E s : items) {
      F newItem = operation.apply(s);
      results.add(newItem);
    }
    return List.copyOf(results);
  }

  //  public static <E> List<E> map(List<E> items, UnaryOperator<E> operation) {
//    List<E> results = new ArrayList<>();
//    for (E s : items) {
//      E newItem = operation.apply(s);
//      results.add(newItem);
//    }
//    return List.copyOf(results);
//  }
//
  public static <E> void forEach(List<E> items, Consumer<E> operation) {
    for (E s : items) {
      operation.accept(s);
    }
  }

  public static <E> List<E> filter(List<E> items, Predicate<E> crit) {
    List<E> interesting = new ArrayList<>();
    for (E s : items) {
      if (crit.test(s)) {
        interesting.add(s);
      }
    }
    return List.copyOf(interesting); // maybe unnecessarily aggressive, but not a bad plan
  }

  public static void main(String[] args) {
    List<Student> roster = List.of(
        Student.of("Ishan", 2.7, "Art", "History"),
        Student.of("Inaya", 3.7, "Math", "Statistics", "Physics", "Quantum Mechnics"),
        Student.of("Ayo", 3.2, "Math"),
        Student.of("Siobhan", 3.5, "Math", "Physics", "Chemistry"),
        Student.of("Hua", 3.9, "Math", "Physics")
    );


    System.out.println("All: ----------------------------");
    forEach(roster, s -> System.out.println("> " + s));

    System.out.println("Smart: ----------------------------");
    forEach(filter(roster, s -> s.gpa() > 3.2),
        s -> System.out.println("Congratulations " + s.name() + " your gpa of " + s.gpa() + " is awesome!"));

    System.out.println("Unenthusiastic: ----------------------------");
    forEach(filter(roster, s -> s.courses().size() < 3),
        s -> System.out.println("Congratulations " + s.name() + " your gpa of " + s.gpa() + " is awesome!"));

    System.out.println("Smart-ish:----------------------------");
    forEach(filter(roster, s -> s.gpa() > 2.2), s -> System.out.println("> " + s));

    System.out.println("Enthusiastic: ----------------------------");
    forEach(filter(roster, s -> s.courses().size() > 2),
        s -> System.out.println("Congratulations " + s.name() + " your gpa of " + s.gpa() + " is awesome!"));

    System.out.println("with extra credit: ----------------------------");
//    UnaryOperator<Student> extraCredit =
//        s -> s.courses().size() > 2
//            ? new Student(s.name(), s.gpa() + 0.2, s.courses()) : s;
//    forEach(map(roster, extraCredit), s -> System.out.println("> " + s));

    Function<Student, Student> extraCredit =
        s -> s.courses().size() > 2
            ? new Student(s.name(), s.gpa() + 0.2, s.courses()) : s;
    forEach(map(roster, extraCredit), s -> System.out.println("> " + s));

    System.out.println("We love Quantum Mechanics ----------------------------");
    forEach(map(roster,
        s -> s.courses().contains("Quantum Mechanics")
            ? new Student(s.name() + "(superstar)", s.gpa(), s.courses()) : s
    ), s -> System.out.println("> " + s));

    System.out.println("Messages: ----------------------------");
    forEach(
        map(
            filter(roster, s -> s.gpa() > 3.0),
            s -> "Welcome to school " + s.name()),
        s -> System.out.println("> " + s));
  }
}
