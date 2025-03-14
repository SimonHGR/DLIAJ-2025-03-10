package textblocks;

public class Example {
  public static void main(String[] args) {
    String template = """
        Hello %s,
          Thank you for joining our       
        %s club, you owe %d in dues
        for your first month membership.
        
        Thank you! 
        """/*.indent(5)*/;
    String output = String.format(template, "Ayo", "birdwatching", 20);
    System.out.println("[" + template + "]");
    System.out.println("[" + output + "]");

    String threeChars = """
        xxx      ""\"""\"    \n""";
    System.out.println(threeChars.length());
  }
}
