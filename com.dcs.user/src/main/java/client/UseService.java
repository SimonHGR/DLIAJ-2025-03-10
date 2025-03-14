package client;

//import servimpls.HackIt;

import java.lang.reflect.Field;

public class UseService {
  public static void main(String[] args) throws Throwable {
    System.out.println("UseService Starting...");
    System.out.println(service.Messages.message);
//    System.out.println(servimpls.Restricted.restrictedMessage);

//    System.out.println(HackIt.getRestrictedMessage()); // split package not permitted

//    System.out.println(service.Messages.secretMessage);
    Class<?> cl = service.Messages.class;
    Field smField = cl.getDeclaredField("secretMessage");
    smField.setAccessible(true);
    Object obj = smField.get(null);
    System.out.println(obj);
  }
}
