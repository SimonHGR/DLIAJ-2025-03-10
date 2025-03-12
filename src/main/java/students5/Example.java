package students5;

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

class SmartStudent implements StudentCriterion {
  private double threshold;
  public SmartStudent(double threshold) {
    this.threshold = threshold;
  }
  @Override
  public boolean test(Student s) {
    return s.gpa() > threshold;
  }
}

class EnthusiasticStudent implements StudentCriterion {
  @Override
  public boolean test(Student s) {
    return s.courses().size() > 2;
  }
}

interface StudentUser {
  void accept(Student s);
}

// could write student to database, or across a network
// or send them a letter
class SimpleStudentPrinter implements StudentUser {
  @Override
  public void accept(Student s) {
    System.out.println("> " + s);
  }
}

class CongratulateStudent implements StudentUser {

  @Override
  public void accept(Student s) {
    System.out.println("Congratulations " + s.name() + " your gpa of " + s.gpa() + " is awesome!");
  }
}

public class Example {

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
        Student.of("Inaya", 3.7, "Math", "Statistics", "Physics", "Quantum mechnics"),
        Student.of("Ayo", 3.2, "Math"),
        Student.of("Siobhan", 3.5, "Math", "Physics", "Chemistry"),
        Student.of("Hua", 3.9, "Math", "Physics")
    );

    doToAll(roster, new SimpleStudentPrinter());
    System.out.println("----------------------------");
    doToAll(getInteresting(roster, new SmartStudent(3.2)), new CongratulateStudent());
    System.out.println("----------------------------");
    doToAll(getInteresting(roster, new SmartStudent(2.2)), new SimpleStudentPrinter());

    System.out.println("----------------------------");
    doToAll(getInteresting(roster, new EnthusiasticStudent()), new CongratulateStudent());
  }
}
