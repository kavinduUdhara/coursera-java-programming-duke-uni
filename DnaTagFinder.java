public class DnaTagFinder {
  public static void main(String[] args){
    DnaTagFinder tf = new DnaTagFinder();
    String portion = tf.findProtein("cccatggggtttaaataataataggagagagagagagagttt");
    System.out.println(portion);
    System.out.println(portion.length());
  }

  public String findProtein(String dna){
    dna = dna.toLowerCase();
    String tag = "";
    int start = dna.indexOf("atg");
    if (start == -1){
      return tag;
    }
    int end = dna.indexOf("tag", start+3);
    if (end == -1){
      return tag;
    }
    if ((end - start) % 3 != 0){
      return tag;
    }
    tag = dna.substring(start, end+3);
    return tag;
  }
}
