package students2;

import java.util.List;

class Student {
  private final String name;
  private final double gpa;
  private final List<String> courses;

  private Student(String name, double gpa, List<String> courses) {
    this.name = name;
    this.gpa = gpa;
    this.courses = List.copyOf(courses);
  }

  public static Student of(String name, double gpa, String ... courses) {
    List<String> c = List.of(courses);
    if (!isValid(name, gpa, c)) throw new IllegalArgumentException("Bad Student params");
    return new Student(name, gpa, c);
  }

  public String getName() {
    return name;
  }

  public double getGpa() {
    return gpa;
  }

  public Student withGpa(double gpa) {
    if (!isValid(this.name, gpa, courses)) throw new IllegalArgumentException("Bad gpa");
    return new Student(this.name, gpa, courses);
  }

  public List<String> getCourses() {
    return courses;
  }

  @Override
  public String toString() {
    return "Student{" +
        "name='" + name + '\'' +
        ", gpa=" + gpa +
        ", courses=" + courses +
        '}';
  }

  @Override
  public final boolean equals(Object obj) {
//    if (obj instanceof Student) // then cast yada yads
//    if (obj instanceof Student other) // more efficient
    return obj instanceof Student other
        && other.name.equals(this.name)
        && other.gpa == this.gpa
        && other.courses.equals(this.courses);
  }

  public static boolean isValid(String name, double gpa, List<String> courses) {
    return name != null && name.length() != 0; // and other stuff!
  }
}

public class Example {
  public static void main(String[] args) {
    Student a = Student.of("Ayo", 3.7, "Math", "Physics", "Chemistry");
    // Ayo aces a test... how do we increase the gpa!?
    String lower = "hello";
    String upper = lower.toUpperCase();
    System.out.println(lower);
    System.out.println(upper);

//    Student aPlus = a.setGpa(3.9);
    a = a.withGpa(3.9);
  }
}
