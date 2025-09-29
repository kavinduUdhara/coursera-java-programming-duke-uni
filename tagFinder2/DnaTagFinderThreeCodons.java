import edu.duke.FileResource;

public class DnaTagFinderThreeCodons{
  public static void main(String[] args){
    DnaTagFinderThreeCodons tf = new DnaTagFinderThreeCodons();
    FileResource fr = new FileResource("Axl2p.fa");
    int[] segment = tf.findGene(fr.asString());
  }

  public int findStopCodon(String dnaStr, int startIndex, String codon){
    int end = dnaStr.indexOf(codon, startIndex + 3);
    while ((end - startIndex) % 3 != 0){
      end = dnaStr.indexOf(dnaStr, end + 3);
      if (end == -1){
        return end;
      }
    }
    return end;
  }

  public int[] findGene(String dna){
    dna = dna.toLowerCase();
    String gene = "";
    int start = dna.indexOf("atg");
    if (start == -1){
      return new int[] {-1, -1};
    }

    int taaEnd = findStopCodon(dna, start, "taa");
    int tagEnd = findStopCodon(dna, start, "tag");
    int tgaEnd = findStopCodon(dna, start, "tga");

    int min = taaEnd;
    for (int i : new int[] {taaEnd, tagEnd, tgaEnd}){
      if (i == -1){
        continue;
      }
      if (min == -1){
        min = i;
      }
      if (i < min){
        min = i;
      }
    }

    if (min == -1){
      return new int[] {-1, -1};
    } else{
      return new int[] {start, min};
    }
  }
}