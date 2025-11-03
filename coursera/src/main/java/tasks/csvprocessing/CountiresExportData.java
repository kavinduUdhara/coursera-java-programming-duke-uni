package tasks.csvprocessing;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.FileResource;

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

  public String countryInfo(CSVParser parser, String country){
    for (CSVRecord record : parser){
      String recordCountry = record.get("Country");
      if (country.equals(recordCountry)){
        return String.format("%s: %s: %s", record.get("Country"), record.get("Exports"), record.get("Value (dollars)"));
      }
    }
    return "NOT FOUND";
  };

  public void listExportsTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
    for (CSVRecord record : parser){
      if(record.get("Exports").contains(exportItem1) && record.get("Exports").contains(exportItem2)){
        System.out.println(record.get("Country"));
      }
    }
  }

  public int numberOfExports(CSVParser parser, String exportItem){
    int count = 0;
    for (CSVRecord record : parser){
      if (record.get("Exports").contains(exportItem)){
        count++;
      }
    }
    return count;
  }

  public void bigExports(CSVParser parser, String amount){
    for(CSVRecord record : parser){
      if(record.get("Value (dollars)").length() > amount.length()){
        System.out.format("%s %s%n", record.get("Country"), record.get("Value (dollars)") );
      }
    }
  }

  public static void main(String[] args){
    CountiresExportData ce = new CountiresExportData();
    // CSVParser parser = ce.readFile("coursera\\src\\main\\java\\tasks\\csvprocessing\\exportData\\exportdata.csv");
    // String result = ce.countryInfo(parser, "Nauru");
    // System.out.println(result);

    // System.out.format("%nlistExports ________________________________%n");
    // CSVParser parser2 = ce.readFile("coursera\\src\\main\\java\\tasks\\csvprocessing\\exportData\\exportdata.csv");
    // ce.listExporters(parser2, "cocoa");

    // System.out.format("%nlistExportsTwoProducts _____________________________________%n");
    // CSVParser parser3 = ce.readFile("coursera\\src\\main\\java\\tasks\\csvprocessing\\exportData\\exportdata.csv");
    // ce.listExportsTwoProducts(parser3, "cotton", "flowers");

    // CSVParser parser4 = ce.readFile("coursera\\src\\main\\java\\tasks\\csvprocessing\\exportData\\exportdata.csv");
    // int count = ce.numberOfExports(parser4, "cocoa");
    // System.out.format("%nnumberOfExports ____________________________________%n%d%n", count);

    System.out.format("%nbigExports _________________________________________%n");
    CSVParser parser5 = ce.readFile("coursera\\src\\main\\java\\tasks\\csvprocessing\\exportData\\exportdata.csv");
    ce.bigExports(parser5, "$999,999,999,999");
  }
}
