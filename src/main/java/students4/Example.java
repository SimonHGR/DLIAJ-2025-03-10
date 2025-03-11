package students4;

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

public class Example {
  public static void showAll(List<Student> students) {
    for (Student s : students) {
      System.out.println("> " + s);
    }
  }

  // identify what changes independently
  // separate it out
  // find an "interface" for the separated part that allows the two
  //   parts to cooperate to fulfil the original task
  // find a way to bring the parts back together to perform the required task
//  private static double threshold = 3.2;
  public static void showAllSmart(List<Student> students, double threshold) {
    for (Student s : students) {
      if (s.gpa() > threshold) { // double threshold
        System.out.println(">> " + s);
      }
    }
  }

  public static void main(String[] args) {
    List<Student> roster = List.of(
        Student.of("Ishan", 2.7, "Art", "History"),
        Student.of("Inaya", 3.7, "Math", "Statistics", "Physics", "Quantum mechnics"),
        Student.of("Ayo", 3.2, "Math"),
        Student.of("Siobhan", 3.5, "Math", "Physics", "Chemistry"),
        Student.of("Hua", 3.9, "Math", "Physics")
    );

    showAll(roster);
    System.out.println("----------------------------");
    showAllSmart(roster, 3.2);
    System.out.println("----------------------------");
    // caller is able, but NOT REQUIRED, to take responsibility for this value
    // UNLESS we make it private and put some checks on the value
//    threshold = 2.2;
//    showAllSmart(roster);

    // ALTERNATIVELY, if it's a parameter, the client MUST KNOW and TAKE RESPONSIBILITY
    // for this value
    showAllSmart(roster, 2.2);

//    showAllFairlySmart(roster);
  }
}
