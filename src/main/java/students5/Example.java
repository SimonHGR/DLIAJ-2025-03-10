package students5;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@interface NotNull {}

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
//  void doStuff();
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

// Make a StudentCriterion from hints
// "here's the test method"
// compiler adds class ASDEINafik implements StudentCriterion {
//   public boolean test(Student s) {
//    return s.courses().size() > 2;
//  }
// compiler adds }
// compiler adds: new ASDEINafik();
// That's not quite how it works...
// BECAUSE the interface MUST HAVE EXACTLY ONE abstract method
// the compiler ALSO KNOWS what method needs to be provided,
// FURTHER, we can ONLY USE THIS SYNTAX to provide a SINGLE method, so
// all that's really needed is *not even* the whole method, but
// simply the "formal parameter list" and the "method body"
// ALSO Java's compiler needs "permission" to do this magic :)

interface StudentModifier {
  Student modifyStudent(Student s);
}

class GiveCreditForExtraCourses implements StudentModifier {
  @Override
  public Student modifyStudent(Student s) {
    return s.courses().size() > 2
        ? new Student(s.name(), s.gpa() + 0.2, s.courses())
        : s;
  }
}

class GiveKudosForQM implements StudentModifier {
  @Override
  public Student modifyStudent(Student s) {
    return s.courses().contains("Quantum Mechnics")
        ? new Student(s.name() + " (superstar)", s.gpa(), s.courses())
        : s;
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

interface DoWithTwo {
  int doIt(int a, int b);
}

public class Example {

  public static List<Student> processStudents(List<Student> students, StudentModifier operation) {
    List<Student> results = new ArrayList<>();
    for (Student s : students) {
      Student newStudent = operation.modifyStudent(s);
      results.add(newStudent);
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

    doToAll(roster, new SimpleStudentPrinter());
    System.out.println("----------------------------");
    doToAll(getInteresting(roster, new SmartStudent(3.2)), new CongratulateStudent());
    // ??? MUST be a StudentCriterion, or things cannot compile
    // -- perhaps we do NOT need to provide an entire StudentCriterion, instead
    //    we MIGHT (if the language supports it!) provide "stuff from which a
    //    StudentCriterion can unambiguously be made... And then the compiler
    //    makes it FOR US
//    doToAll(getInteresting(roster, ???), new CongratulateStudent());
    System.out.println("Unenthusiastic: ----------------------------");
//    doToAll(getInteresting(roster,
//        (Student s) -> {
//          return s.courses().size() > 3;
//        }
//    ), new CongratulateStudent());
    StudentCriterion unenthusiastic = s -> s.courses().size() > 3;
    doToAll(getInteresting(roster, unenthusiastic), new CongratulateStudent());
    System.out.println("----------------------------");
    doToAll(getInteresting(roster, new SmartStudent(2.2)), new SimpleStudentPrinter());
    System.out.println("----------------------------");
    doToAll(getInteresting(roster, new EnthusiasticStudent()), new CongratulateStudent());
    System.out.println("with extra credit ----------------------------");
    doToAll(processStudents(roster, new GiveCreditForExtraCourses()), new SimpleStudentPrinter());
    System.out.println("We love Quantum Mechanics ----------------------------");
    doToAll(processStudents(roster, new GiveKudosForQM()), new SimpleStudentPrinter());
    System.out.println("----------------------------");
    System.out.println("unenthusiastic is: " + unenthusiastic);
    System.out.println("unenthusiastic type is: " + unenthusiastic.getClass().getName());
    Class cl = unenthusiastic.getClass();
    Method [] methods = cl.getMethods();
    for (Method m : methods) {
      System.out.println("> " + m);
    }
  }

  void doStuff() {

//    class pqoiweur implements StudentCriterion {
//      public boolean test(Student s) {
//        return s.courses().size() > 3;
//      }
//    }
//    StudentCriterion crit =
//        new pqoiweur();

//    StudentCriterion crit = (Student s) -> { return s.gpa() > 3.0; };
//    StudentCriterion crit = (@NotNull Student s) -> { return s.gpa() > 3.0; };
//    StudentCriterion crit = (@NotNull var s) -> { return s.gpa() > 3.0; };
//    StudentCriterion crit = (s) -> { return s.gpa() > 3.0; };
//    StudentCriterion crit = s -> { return s.gpa() > 3.0; };
    StudentCriterion crit = s -> s.gpa() > 3.0     ;

//    DoWithTwo dwt = (int a, int b) -> { return a + b; };
//    DoWithTwo dwt = (var a, var b) -> { return a + b; };
// NONONNO!!! all types, or no types in formal parameter list
//    DoWithTwo dwt = (int a, b) -> { return a + b; };
//    DoWithTwo dwt = (a, b) -> { return a + b; };
    DoWithTwo dwt = (a, b) -> a + b   ;
  }
}


