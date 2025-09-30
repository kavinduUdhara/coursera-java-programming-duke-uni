import edu.duke.FileResource;

public class DnaTagFinder {
  public static void main(String[] args){
    FileResource fr = new FileResource("Axl2p.fa");
    DnaTagFinder tf = new DnaTagFinder();
    String portion = tf.findProtein(fr.asString());
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
    while ((end - start) % 3 != 0){
      end = dna.indexOf("tag", end + 3);
      // if((end - start) % 3 == 0){
      //   tag = dna.substring(start, end);
      //   return tag;
      // }
      if (end == -1){
        return tag;
      }
    }
    tag = dna.substring(start, end+3);
    return tag;
  }

}

