public class FindABC {
  public void findAbc(String input) {
    int index = input.indexOf("abc");
    while (true) {
      // System.out.format("%d,%d%n", index, index+4);
      if (index == -1 || index >= input.length() - 3) {
        break;
      }
      String found = input.substring(index + 1, index + 4);
      System.out.println(found);
      index = input.indexOf("abc", index + 4);
      // System.out.format("%d,%d%n", index, index+4);
    }
  }
  public void findAbcUpdated(String input) {
    int index = input.indexOf("abc");
    while (true) {
      // System.out.format("%d,%d%n", index, index+4);
      if (index == -1 || index >= input.length() - 2) {
        break;
      }
      String found = input.substring(index + 1, index + 4);
      System.out.println(found);
      index = input.indexOf("abc", index + 3);
      // System.out.format("%d,%d%n", index, index+4);
    }
  }

  public void test() {
    
  }

  public static void main(String[] args){
//     "kdabcabcjei"
// "ttabcesoeiabco"
// "abcbabccabcd"
// "qwertyabcuioabcp"
// "abcabcabcabca"
    FindABC f = new FindABC();
    // f.findAbc("abcd");
    String s = "abcabcabcabca";
    f.findAbc(s);
    System.out.println("_____________________________ correct");
    f.findAbcUpdated(s);
  }
}
