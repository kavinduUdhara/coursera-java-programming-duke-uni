public class SystemOut{
  public static void main(String[] args){
    //i: print hello world
    System.out.println("Hello! Welcome to SLIIT");

    //ii:
    System.out.println("first line");
    System.out.println("This is the second line");

    //iii:
    for (int i = 0; i <= 6; i++){
      if (i == 0 || i == 6){
        System.out.println("  x  ");
      } else if (i == 1 || i == 5){
        System.out.println(" x x ");
      } else {
        System.out.println("x   x");
      }
    }
  }
}