package newswitch;

enum DayOfWeek {
  MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
}

public class Examples {
  public static void main(String[] args) {
    String msg = "Hello";
    String translated;
    switch (msg) {
      case "Hi":
      case "Hello":
        translated = "Bonjour";
        break;
      case "Goodbye":
        translated = "Au revoir";
        break;
      default:
        translated = "Quoi!?";
        break;
    }
    System.out.println(translated);
    System.out.println("--------------------");
    msg = "Goodbye";
    switch (msg) {
      case "Hi", "Hello" -> // commas for alternation permitted in colon form too
          translated = "Bonjour";
      case "Goodbye" -> { // multiple statements on right of arrow MUST BE in curlies
        String s1 = "Au revoir";
        if (Math.random() > 0.6) break; // can use break in a block
        translated = s1;
      }
      default -> translated = "Quoi!?";
    }
    System.out.println(translated);

    msg = "Hi";
    System.out.println(switch (msg) { // must provide expression value for ALL POSSIBLE input values
      case "Hi", "Hello" -> "Bonjour";
      case "Goodbye" -> { // multiple statements on right of arrow MUST BE in curlies
        String s1 = "Au revoir";
//        if (Math.random() > 0.6) break; // break is NOT permitted in expression form
        yield s1; // produces the expression's value from a block (colon form ALWAYS uses yield)
      }
      default -> /*translated = */"Quoi!?"; // assigment IS an expression, but not needed here
    });

    DayOfWeek today = DayOfWeek.FRIDAY;
    System.out.println(switch(today) {
      case MONDAY, TUESDAY, WEDNESDAY, THURSDAY -> "Keep at it!";
      case FRIDAY -> "Yay, leave early";
      case SATURDAY, SUNDAY -> "Playtime";
    });

    // coming in Java 21...
    // sealed types can limit the number of possible subtypes, making
    // it easier to cover all possibilities
//    Object obj = "XXX";
//    switch (obj) {
//      case String s -> System.out.println("It's a string " + s); // NO ALTERNATION IN THESE PATTERNS
//      // MUST COVER ALL INPUT POSSIBILITIES
//    }
//    3 + 1; // pure expression MUST BE assigned
    // statement expression -- expected to have a side-effect
    "Hello".length(); // function calls, constructor invocation, inc/dec operators, assignments
  }
}
