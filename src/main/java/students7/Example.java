package students7;

import java.util.ArrayList;
import java.util.List;

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

interface StudentCriterion {
  boolean test(Student s);
}

interface Modifier<E> {
  E apply(E s);
}

interface StudentUser {
  void accept(Student s);
}

public class Example {

  public static <E> List<E> map(List<E> items, Modifier<E> operation) {
    List<E> results = new ArrayList<>();
    for (E s : items) {
      E newItem = operation.apply(s);
      results.add(newItem);
    }
    return List.copyOf(results);
  }

  public static void doToAll(List<Student> students, StudentUser operation) {
    for (Student s : students) {
      operation.accept(s);
    }
  }

  // identify what changes independently
  // separate it out
  // find an "interface" for the separated part that allows the two
  //   parts to cooperate to fulfil the original task
  // find a way to bring the parts back together to perform the required task

  public static List<Student> getInteresting(List<Student> students, StudentCriterion crit) {
    List<Student> interesting = new ArrayList<>();
    for (Student s : students) {
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
    doToAll(roster, s -> System.out.println("> " + s));

    System.out.println("Smart: ----------------------------");
    doToAll(getInteresting(roster, s -> s.gpa() > 3.2),
        s -> System.out.println("Congratulations " + s.name() + " your gpa of " + s.gpa() + " is awesome!"));

    System.out.println("Unenthusiastic: ----------------------------");
    doToAll(getInteresting(roster, s -> s.courses().size() < 3),
        s -> System.out.println("Congratulations " + s.name() + " your gpa of " + s.gpa() + " is awesome!"));

    System.out.println("Smart-ish:----------------------------");
    doToAll(getInteresting(roster, s -> s.gpa() > 2.2), s -> System.out.println("> " + s));

    System.out.println("Enthusiastic: ----------------------------");
    doToAll(getInteresting(roster, s -> s.courses().size() > 2),
        s -> System.out.println("Congratulations " + s.name() + " your gpa of " + s.gpa() + " is awesome!"));

    System.out.println("with extra credit: ----------------------------");
    Modifier<Student> extraCredit =
        s -> s.courses().size() > 2
            ? new Student(s.name(), s.gpa() + 0.2, s.courses()) : s;
    doToAll(map(roster, extraCredit), s -> System.out.println("> " + s));

    System.out.println("We love Quantum Mechanics ----------------------------");
    doToAll(map(roster,
        s -> s.courses().contains("Quantum Mechanics")
            ? new Student(s.name() + "(superstar)", s.gpa(), s.courses()) : s
      ), s -> System.out.println("> " + s));
  }
}
