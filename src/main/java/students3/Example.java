package students3;

import java.util.List;

// records are subtypes of java.lang.Record, they MUST NOT have an extends clause
// and they are final types, so cannot be extended
// they CAN implement interfaces
// this is meant to be "immutable" (but it will not succeed if the components are mutable types)
record Student(String name, double gpa, List<String> courses) {
  private static String prefix = "Mx. ";
//  private int myCount; // NOT permitted

  public static Student of(String name, double gpa, String... courses) {
    return new Student(name, gpa, List.of(courses));
  }

  // Define our own canonical constructor
  // default one -- created by compiler if we do nothing, looks like this
  // if we create our own, we MUST:
  // - use the identical formal parameter list (including names!!!!)
  // - initialize the private final fields once (and only once)
//  Student(String name, double gpa, List<String> courses) {
//    this.name = name;
//    this.gpa = gpa;
//    this.courses = courses;
//  }

// we might want to add stuff... e.g. validation and/or "normalization"
//  Student(String name, double gpa, List<String> courses) {
//    if (!isValid(name, gpa, courses)) throw new IllegalArgumentException("bad student params");
//    name = prefix + name; // manipulate formal params, NOT the fields.
//    this.name = name;
//    this.gpa = gpa;
//    this.courses = courses;
//  }

  // compact constructor -- becomes a PREFIX to the initialization of the fields
  Student {
    if (!isValid(name, gpa, courses)) throw new IllegalArgumentException("bad student params");
    name = prefix + name; // manipulate formal params, NOT the fields.
  }

  Student(String name) {
    this(name, 0.0, List.of());
  }

  public static boolean isValid(String name, double gpa, List<String> courses) {
    return name != null && name.length() > 0; // and more
  }

  @Override // not really an override, it's a replacment
  // Not permitted to throw declare checked exceptions
  // return type MUST match the component type
  public String name() {
//    return this.name;
//    return super.name(); // NO NO, not possible, no super()
    return "I'm a special name: " + this.name;
  }
}

public class Example {
  public static void main(String[] args) {
    Student s1 = new Student("Hua", 3.9, List.of("Math", "Quantum Mechnics"));
//    Student s2 = new Student("Hua", 3.9, List.of("Math", "Quantum Mechnics"));
    Student s2 = Student.of("Hua", 3.9, "Math", "Quantum Mechnics");
    System.out.println("s1 == s2? " + (s1 == s2));
    System.out.println("s1 " + s1);
    System.out.println("s1.equals(s2)? " + s1.equals(s2));
    System.out.println("s1.name? " + s1.name());
    // also hashCode
  }
}

