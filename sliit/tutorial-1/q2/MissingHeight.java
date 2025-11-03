import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MissingHeight {
  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    // List<Integer> heights = new ArrayList<>();
    int total = 0;
    for(int i = 1; i <= 3; i++){
      System.out.printf("Enter Height %d: ",i);
      total += input.nextInt();
      // heights.add(input.nextInt());
    }
    System.out.print("Enter computed average: ");
    float avg = input.nextInt();

    System.out.printf("Missing height is: %.2f%n", (avg * 5 - total) / 2);
  }
}
