package superiterable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Collections {
  public static String getGradeLetter(Student s) {
    double gpa = s.gpa();
    if (gpa > 3.6) return "A";
    if (gpa > 3.0) return "B";
    if (gpa > 2.5) return "C";
    return "D";
  }
  public static void main(String[] args) {
    List<Student> roster = List.of(
        Student.of("Ishan", 2.7, "Art", "History"),
        Student.of("Inaya", 3.7, "Math", "Statistics", "Physics", "Quantum Mechnics"),
        Student.of("Ayo", 3.2, "Math"),
        Student.of("Siobhan", 3.5, "Math", "Physics", "Chemistry"),
        Student.of("Hua", 3.9, "Math", "Physics")
    );

    Map<String, List<Student>> map = roster.stream()
        .collect(Collectors.groupingBy(s -> Collections.getGradeLetter(s)));
    map.forEach((k, v) -> System.out.println("Students with grade " + k + " are " + v));

    Map<String, List<String>> map2 = roster.stream()
        .collect(Collectors.groupingBy(s -> Collections.getGradeLetter(s),
            Collectors.mapping(s -> s.name() + " has grade " + s.gpa(),
                Collectors.toList())));
    map2.forEach((k, v) -> System.out.println(k + " : " + v));

    Map<String, String> map3 = roster.stream()
        .collect(Collectors.groupingBy(s -> Collections.getGradeLetter(s),
            Collectors.mapping(s -> s.name() + " grade " + s.gpa(),
                Collectors.joining(", "))));
    map3.forEach((k, v) -> System.out.println(k + " : " + v));
  }
}
