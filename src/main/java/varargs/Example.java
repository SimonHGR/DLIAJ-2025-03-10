package varargs;

import java.util.ArrayList;
import java.util.List;

public class Example {
//  var x = 99; // NOT ALLOWED FOR members!

  // NOT ALLOWED--what should the type be
//  public static void doStuff(var x) {}
  public static void doStuff(int x) {}

//  public static int sum(int [] values) {
  public static int sum(int ... values) {
    // "context sensitive special identifier"
    var var = "var";
//    int goto = 99; // goto is a keyword!!
    System.out.println("summing " + values.length + " values");
    System.out.println("first item is " + values[0]);
//    int sum = 0;
    // cannot split a var!
//    var sum;
//    sum = 0;
    var sum = 0;

    for (int i : values) {
      sum += i;
    }
    return sum;
  }

  public static void doStuff(/*Array*/List<String> als) {}

  public static void main(String[] args) {
//    int [] ia = {1,2,3,4,5,6,7,8,9,10};
//    var ia = {1,2,3,4,5,6,7,8,9,10}; NOPE!!!
    var ia = new int[]{1,2,3,4,5,6,7,8,9,10};
//    int [] ia = new int[]{1,2,3,4,5,6,7,8,9,10};
    System.out.println("sum is " + sum(ia));
    System.out.println("sum is " + sum(1,2,3,4,5,6,7,8,9,10));

//    var cs = "Hello"; // it's a String!!!
    var cs = (CharSequence)"Hello"; // it's a String!!!
//    CharSequence cs = "Hello";

    List<String> als = new ArrayList<String>();
    doStuff(als);

    var thing = true ? "Hello" : 99; // "non-denotable" type
    thing.compareTo(null);
    "Hello".compareTo(null);
    Integer.valueOf(99).compareTo(null);
//    Long l = 99; // NOPE! only autobox int to Integer
    Long l = 99L;

    // NOPE, unreachable
//    while (false) {
//
//    }
    // this is Java's "conditional compilation"
    // this works in switch and conditional operator (?:) too!!!
    final boolean DEBUG = false;
    if (DEBUG) { // or literal false
      System.out.println("debug info");
    }
  }
}
