package students1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {
  private String name;
  private double gpa;
  private List<String> courses;

  // if we use "courses" directly, can the caller mutate that!? BAD
  public Student(String name, double gpa, List<String> courses) {
    // maybe this is a test (unit test etc.) instead
//    if (!isValid(name, gpa, courses)) throw new IllegalArgumentException("bad proposed student");
    assert isValid(name, gpa, courses) : "Bad student raw material";
    this.name = name;
    this.gpa = gpa;

    this.courses = courses;
//    this.courses = List.copyOf(courses); // Java 10?? --creates unmodifiable structure
  }

  public void setName(String name) {
    if (!isValid(name, this.gpa, this.courses)) throw new IllegalArgumentException("bad student name");
  }

  public String getName() {
    return this.name;
  }

  public List<String> getCourses() {
    // defensive copy -- can do when passing argument INTO functions
    // or with returned values
    // this is expensive at EVERY call!
//    return new ArrayList<>(this.courses);
    // IF (but only if) we have used an unmodifiable structure internally,
    // we can safely return it directly...
//    return this.courses;

    // create an umodifiable "proxy" wrapped around the same data
    // older versions of Java
    // BE CAREFUL with Arrays.asList -- this is NOT UNMODIFIABLE
    return Collections.unmodifiableList(this.courses);
  }

  public static boolean isValid(String name, double gpa, List<String> courses) {
    return !(name == null || name.isEmpty() || gpa < 0 || gpa > 5.0
        || courses == null || courses.size() == 0);
  }

  @Override
  public String toString() {
    return "Student{" +
        "name='" + name + '\'' +
        ", gpa=" + gpa +
        ", courses=" + courses +
        '}';
  }

  public static void main(String[] args) {
    // NOPE!! new Student("", 3.2, List.of("Math"));
    Student s = new Student("Albert", 3.2, new ArrayList<>(List.of("Math")));
    System.out.println("Student is: " + s);
//    List<String> advancedCourses = s.getCourses();
    // user of this might need to make its own copy
    List<String> advancedCourses = new ArrayList<>(s.getCourses());
    advancedCourses.clear();
//    advancedCourses.... remove the non-advanced
    System.out.println("Student is: " + s);
  }
}

// reminder--invoice example!
//class Date {
//  int day, month, year;
//}