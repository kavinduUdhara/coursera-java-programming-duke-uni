import edu.duke.FileResource;
import java.util.ArrayList;

public class DnaTagFinderThreeCodonsUpdated {
  public static void main(String[] args) {
    DnaTagFinderThreeCodonsUpdated tf = new DnaTagFinderThreeCodonsUpdated();
    FileResource fr = new FileResource("GRch38dnapart.fa");
    ArrayList<String> genes = tf.findAllGenes(fr.asString());

    for (String gene : genes) {
      System.out.println("Gene: " + gene);
      System.out.println("Length: " + gene.length());
      System.out.println("CG ratio: " + tf.getCGRatio(gene));
      System.out.println("-----------------------------");
    }
  }

  /** ✅ Calculate CG ratio */
  public double getCGRatio(String gene) {
    int count = 0;
    for (char ch : gene.toCharArray()) {
      if (ch == 'c' || ch == 'g') {
        count++;
      }
    }
    return (double) count / gene.length();
  }

  public int findStopCodon(String dnaStr, int startIndex, String codon) {
    int end = dnaStr.indexOf(codon, startIndex + 3);
    while (end != -1 && (end - startIndex) % 3 != 0) {
      end = dnaStr.indexOf(codon, end + 1); // ✅ fixed
    }
    return end;
  }

  public int[] findGene(String dna) {
    dna = dna.toLowerCase();
    int start = dna.indexOf("atg");
    if (start == -1) {
      return new int[] { -1, -1 };
    }

    int taaEnd = findStopCodon(dna, start, "taa");
    int tagEnd = findStopCodon(dna, start, "tag");
    int tgaEnd = findStopCodon(dna, start, "tga");

    int min = -1;
    for (int stop : new int[] { taaEnd, tagEnd, tgaEnd }) {
      if (stop != -1) {
        if (min == -1 || stop < min) {
          min = stop;
        }
      }
    }

    if (min == -1) {
      return new int[] { -1, -1 };
    } else {
      return new int[] { start, min };
    }
  }

  public ArrayList<String> findAllGenes(String dna) {
    dna = dna.toLowerCase();
    ArrayList<String> genes = new ArrayList<>();
    int[] indices = findGene(dna);

    while (indices[0] != -1 && indices[1] != -1) {
      int start = indices[0];
      int end = indices[1];
      String gene = dna.substring(start, end + 3);
      genes.add(gene);

      dna = dna.substring(end + 3); // move past this gene
      indices = findGene(dna);      // look for next gene
    }
    return genes;
  }
}
