import java.util.Scanner;
public class FindWH {
  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    System.out.print("Enter perimeter: ");
    int p = input.nextInt();
    float l = (float) p / 2 / (1 + (float) 3 / 4);
    float w = l * 3 /4;
    System.out.printf("length: %.2f | width: %.2f%n", l, w);
  }
}
