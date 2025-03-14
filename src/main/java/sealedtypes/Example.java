package sealedtypes;

import java.util.ArrayList;
import java.util.List;

sealed interface Transporter permits Truck, Car, Bicycle { }
final class Truck implements Transporter {
  private int capacity;

  public Truck(int capacity) {
    this.capacity = capacity;
  }
  public boolean canCarry(int load) {
    return load <= capacity;
  }

  @Override
  public String toString() {
    return "Truck{" +
        "capacity=" + capacity +
        '}';
  }
}

/*non-sealed*/ sealed class Car implements Transporter permits ReliantRobin {
  private int seatCount;

  public Car(int seatCount) {
    this.seatCount = seatCount;
  }

  public int getSeatCount() {
    return seatCount;
  }

  @Override
  public String toString() {
    return "Car{" +
        "seatCount=" + seatCount +
        '}';
  }
}

final class ReliantRobin extends Car {
  ReliantRobin() {
    super(2);
  }
}

record Bicycle() implements Transporter {

}


public class Example {
  public static boolean canCarry(Transporter t, int load) {
    boolean rv = true;
    if (t instanceof Truck) {
      rv = ((Truck) t).canCarry(load);
    } else if (t instanceof Car c) {
      int seats = c.getSeatCount();
      int capacity = 170 * (seats - 1);
      rv = capacity >= load;
    }
    return rv;

//    return switch(t) {
//      case Car c -> 170 * (c.getSeatCount() - 1) >= load;
//      case Truck t -> t.canCarry(load);
//    };
  }

  public static void main(String[] args) {
    List<Transporter> lt = List.of(
        new Car(5),
        new Car(2),
        new Bicycle(),
        new Truck(10_000),
        new Truck(2000)
    );

    int load = 1600;
    System.out.println("Checking for a load of " + load + "lb");
    for (Transporter t : lt) {
      System.out.println(t + "can carry " + load + "? " + canCarry(t, load));
    }

  }
}
