package tasks.csvprocessing;
import edu.duke.*;
import org.apache.commons.csv.*;

public class CountiresExportData {
  public CSVParser readFile(String FileName){
    FileResource fr = new FileResource(FileName);
    CSVParser parser = fr.getCSVParser();
    return parser;
  }

  public void listExporters(CSVParser parser, String exportOfInterest){
    for (CSVRecord record : parser){
      if (record.get("Exports").contains(exportOfInterest)){
        System.out.println(record.get("Country"));
      }
    }
  }

  public static void main(String[] args){
    CountiresExportData ce = new CountiresExportData();
    CSVParser parser = ce.readFile("coursera\\src\\main\\java\\tasks\\csvprocessing\\exportData\\exportsmall.csv");
    ce.listExporters(parser, "coffee");
  }
}
