package streamlab1;


import java.util.List;
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

public class Solution {
  public static void main(String[] args) {
    List<Student> roster = List.of(
        Student.of("Ishan", 2.7, "Art", "History"),
        Student.of("Inaya", 3.7, "Math", "Statistics", "Physics", "Quantum Mechnics"),
        Student.of("Ayo", 3.2, "Math"),
        Student.of("Siobhan", 3.5, "Math", "Physics", "Chemistry"),
        Student.of("Hua", 3.9, "Math", "Physics")
    );

//      .forEach(x -> System.out.println(x));
    System.out.println("print all students -------------------------");
    roster.stream()
        .forEach(x -> System.out.println(x));

    System.out.println("print \"Ishan has grade 3.2\" (or similar!) for all students -------------------------");
    roster.stream()
        .map(s -> s.name() + " has grade " + s.gpa())
        .forEach(x -> System.out.println(x));

    System.out.println("print \"Ishan is smart with grade 3.2\" (or similar!) for all smart students -------------------------");
    roster.stream()
        .filter(s -> s.gpa() > 3)
        .map(s -> s.name() + " has grade " + s.gpa())
        .forEach(x -> System.out.println(x));

    System.out.println("print \"Ishan is enthusiastic and takes 2 courses\" for all smart students -------------------------");
    roster.stream()
        .filter(s -> s.gpa() > 3) // or combine with && -- more efficient!
        .filter(s -> s.courses().size() > 1)
        .map(s -> s.name() + " is enthusiastic and takes " + s.courses().size() + " courses")
        .forEach(x -> System.out.println(x));

    System.out.println("-------------------------");
    boolean both = roster.stream()
        .anyMatch(s -> s.gpa() > 3 && s.courses().size() > 1);
    System.out.println("smart and enthusiatic exists? " + both);

    System.out.println("-------------------------");
    roster.stream()
//        .map(s -> s.courses())
//        . flatten out a pile
        .flatMap(s -> s.courses().stream().map(c -> s.name() + " takes " + c))
//        .flatMap((Student s) -> {
//          return s.courses().stream().map(c -> s.name() + " takes " + c);
//        })
        .forEach(x -> System.out.println(x));

    // print Ayo takes Math for ALL student / course pairs
  }
}

