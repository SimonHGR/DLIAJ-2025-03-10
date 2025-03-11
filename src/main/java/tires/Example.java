package tires;

class Tire {
  private int diameter;
  private int width;

  public Tire(int diameter, int width) {
    this.diameter = diameter;
    this.width = width;
  }

  @Override
  public String toString() {
    return "Tire{" +
        "diameter=" + diameter +
        ", width=" + width +
        '}';
  }
  @Override
  public boolean equals(Object obj) {
    // see below in TruckTire, these are not equivalent, they
    // simply borrow some behaviors
    return obj instanceof Tire t
        && t.diameter == this.diameter
        && t.width == this.width;
  }
}

class TruckTire extends Tire {
  private int weight;

  public TruckTire(int diameter, int width, int weight) {
    super(diameter, width);
    this.weight = weight;
  }

  @Override
  public String toString() {
    return "TruckTire{" +
        "weight=" + weight +
        '}';
  }

  @Override
  public boolean equals(Object obj) {
    return // this.getClass() == obj.getClass() &&
        obj instanceof TruckTire tt // test here is not really needed if the class types are compared
        && tt.weight == this.weight
        && super.equals(tt);
  }
}

public class Example {
  public static void main(String[] args) {
    Tire t1 = new Tire(14, 144);
    Tire t2 = new Tire(14, 144);
    Tire t3 = new Tire(15, 144);
    System.out.println(t1.equals(t2));
    System.out.println(t1.equals(t3));
    TruckTire tt1 = new TruckTire(14, 144, 2000);
    System.out.println("t1.equals(tt1)? " + t1.equals(tt1));
    System.out.println("tt1.equals(t1)? " + tt1.equals(t1));
  }
}
