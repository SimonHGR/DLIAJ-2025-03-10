package polymorph1;

class Parent {
  public void doStuff() {
    System.out.println("Parent.doStuff");
  }
}
class Child extends Parent {
  public void doStuff() {
    System.out.println("Child.doStuff");
  }

  public void doMoreStuff() {
    System.out.println("Child.doMoreStuff");
  }
}

public class Example {
  public static void main(String[] args) {
    Parent p = new Parent();
//    Parent p = new Child();
    p.doStuff(); // behavior is determined by the object REFERRED TO
    //NO! Compiler knows that doMoreStuff might not be available
//    p.doMoreStuff();
    // this is what "casting" is for
    // this is the "classic" way
    if (p instanceof Child) {
      Child c = (Child) p;
      c.doMoreStuff();
    } else {
      System.out.println("Hmm, that's not a Child!");
    }

    System.out.println("newer code -- pattern matching");
    if (p instanceof Child c) {
      c.doMoreStuff();
    } else {
      System.out.println("Hmm, that's not a Child!");
//      System.out.println(c); // NOT IN SCOPE HERE
    }

    CharSequence cs = "Hello World!";
    if (cs instanceof String s && s.length() > 6) {
      System.out.println(s + " is a long string!");
    } else {
      System.out.println("either it's not a string, or it's not long");
    }
  }
}
