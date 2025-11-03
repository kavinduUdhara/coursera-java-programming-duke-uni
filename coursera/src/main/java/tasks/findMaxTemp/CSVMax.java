package tasks.findMaxTemp;

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.util.regex.*;

public class CSVMax {
  public CSVRecord hottestHourInFile(CSVParser parser){
    CSVRecord largestSoFar = null;
    for (CSVRecord record : parser){
      if(record.get("TemperatureF").equals("-9999")){
        continue;
      }
      if (largestSoFar == null){
        largestSoFar = record;
      } else {
        if ((Double.parseDouble(largestSoFar.get("TemperatureF")) < (Double.parseDouble(record.get("TemperatureF"))))){
          largestSoFar = record;
        }
      }
    }
    return largestSoFar;
  }

  public CSVRecord hottestInManyDays() {
    CSVRecord largestSoFar = null;
    DirectoryResource dr = new DirectoryResource();
    for (File file : dr.selectedFiles()){
      FileResource fr = new FileResource(file);
      CSVRecord largestInFile = hottestHourInFile(fr.getCSVParser());
      if (largestSoFar == null){
        largestSoFar = largestInFile;
      } else if ((Double.parseDouble(largestSoFar.get("TemperatureF"))) < (Double.parseDouble(largestInFile.get("TemperatureF")))){
        largestSoFar = largestInFile;
      }
    }
    return largestSoFar;
  }

  public CSVRecord coldestHourInFile(CSVParser parser){
    CSVRecord coldersSoFar = null;
    for (CSVRecord record : parser){
      if(record.get("TemperatureF").equals("-9999")){
        continue;
      }
      if (coldersSoFar == null){
        coldersSoFar = record;
      } else{
        if(Double.parseDouble(coldersSoFar.get("TemperatureF")) > Double.parseDouble(record.get("TemperatureF"))){
          coldersSoFar = record;
        }
      }
    }
    return coldersSoFar;
  }

  public void testColdestHourInFile(){
    FileResource fr = new FileResource("coursera\\src\\main\\java\\tasks\\findMaxTemp\\data\\2014\\weather-2014-05-01.csv");
    CSVParser parser1 = fr.getCSVParser();
    CSVRecord rec = coldestHourInFile(parser1);
    System.out.format("%s", rec.get("TemperatureF"));
  }

  public String fileWithColdestTemperature() {
    DirectoryResource dr = new DirectoryResource();
    Double coldestSoFar = null;
    String coldestFileName = "";
    for (File file : dr.selectedFiles()){
      CSVRecord coldestInFile = coldestHourInFile(new FileResource(file).getCSVParser());
      if (coldestSoFar == null){
        coldestSoFar = Double.parseDouble(coldestInFile.get("TemperatureF"));
        coldestFileName = file.getName();
      } else if (coldestSoFar > Double.parseDouble(coldestInFile.get("TemperatureF"))){
        coldestSoFar = Double.parseDouble(coldestInFile.get("TemperatureF"));
        coldestFileName = file.getName();

      }
    }
    return coldestFileName;
  }

  public void printAllTheRecordsInFile(CSVParser parser){
    for (CSVRecord record : parser){
      System.out.format("%s: %s%n", record.get("DateUTC"), record.get("TemperatureF"));
    }
  }

  public void testFileWithColdestTemperature(){
    String fileName = fileWithColdestTemperature();
    System.out.println(fileName);
    Pattern pattern = Pattern.compile("weather-(\\d{4})-\\d{2}-\\d{2}\\.csv");
    String fileYr = "";
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.matches()) {
          fileYr = matcher.group(1);
        } else {
          return;
        }
    String fn = String.format("coursera\\src\\main\\java\\tasks\\findMaxTemp\\data\\%s\\%s", fileYr, fileName);
    System.out.format("Coldest day was in file %s%n", fileName);
    FileResource fr = new FileResource(fn);
    CSVRecord rec = coldestHourInFile(fr.getCSVParser());
    System.out.format("Coldest temperature on that day was %s%n", rec.get("TemperatureF"));
    System.out.println("All the Temperatures on the coldest day were:");
    printAllTheRecordsInFile(fr.getCSVParser());
  }

  public CSVRecord lowestHumidityInFile(CSVParser parser){
    CSVRecord lowestSoFar = null;
    for (CSVRecord record : parser){
      if (record.get("Humidity").equals("N/A")){
        continue;
      }
      if (lowestSoFar == null){
        lowestSoFar = record;
      } else if (Double.parseDouble(lowestSoFar.get("Humidity")) > Double.parseDouble(record.get("Humidity"))){
        lowestSoFar = record;
      }
    }
    return lowestSoFar;
  }

  public void testLowestHumidityInFile(){
    FileResource fr = new FileResource("coursera\\src\\main\\java\\tasks\\findMaxTemp\\data\\2014\\weather-2014-07-22.csv");
    CSVParser parser = fr.getCSVParser();
    CSVRecord csv = lowestHumidityInFile(parser);
    System.out.format("Lowest Humidity was %s at %s%n", csv.get("Humidity"), csv.get("DateUTC"));
  }

  public CSVRecord lowestHumidityInManyFiles(){
    DirectoryResource dr = new DirectoryResource();
    CSVRecord lowestSoFar = null;
    for (File file : dr.selectedFiles()){
      CSVRecord lowestInFile = lowestHumidityInFile(new FileResource(file).getCSVParser());
      if (lowestSoFar == null){
        lowestSoFar = lowestInFile;
      } else if (Double.parseDouble(lowestSoFar.get("Humidity")) > Double.parseDouble(lowestInFile.get("Humidity"))){
        lowestSoFar = lowestInFile;
      }
    }
    return lowestSoFar;
  }

  public void testLowestHumidityInManyFiles(){
    CSVRecord rec = lowestHumidityInManyFiles();
    System.out.format("Lowest Humidity was %s at %s%n", rec.get("Humidity"), rec.get("DateUTC"));
  }

  public double averageTemperatureInFile(CSVParser parser){
    int count = 0;
    double total = 0;
    for (CSVRecord record : parser){
      if(record.get("TemperatureF").equals("-9999")){
        continue;
      }
      count++;
      total = total + Double.parseDouble(record.get("TemperatureF"));
    }
    return total / count;
  }

  public void testAverageTemperatureInFile(){
    FileResource fr = new FileResource("coursera\\src\\main\\java\\tasks\\findMaxTemp\\data\\2013\\weather-2013-08-10.csv");
    double avg = averageTemperatureInFile(fr.getCSVParser());
    System.out.format("Average temperature in file is %f", avg);
  }

  public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
    double total = 0;
    int count = 0;
    for (CSVRecord record : parser){
      if(record.get("TemperatureF").equals("-9999") || record.get("Humidity").equals("N/A")){
        continue;
      }
      if (Double.parseDouble(record.get("Humidity")) >= value){
        count++;
        total += Double.parseDouble(record.get("TemperatureF"));
      }
    }
    return total / count;
  }

  public void testAverageTemperatureWithHighHumidityInFile(){
    FileResource fr = new FileResource("coursera\\src\\main\\java\\tasks\\findMaxTemp\\data\\2013\\weather-2013-09-02.csv");
    double avg = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(), 80);
    if (Double.isNaN(avg)){
      System.out.println("No temperatures with that humidity");
    } else {
      System.out.println(avg);
    }
  }

  public static void main(String[] args){
    CSVMax tmax = new CSVMax();
    // CSVParser parser1 = new FileResource("coursera\\src\\main\\java\\tasks\\findMaxTemp\\data\\2015\\weather-2015-01-02.csv").getCSVParser();
    // CSVRecord maxTempRecord = tmax.hottestHourInFile(parser1);
    // System.out.format("Hottest temperature was %sF at %s", maxTempRecord.get("TemperatureF"), maxTempRecord.get("TimeEST"));
    
    // CSVRecord maxTempRec = tmax.hottestInManyDays();
    // System.out.format("Hottest temperature was %sF at %s", maxTempRec.get("TemperatureF"), maxTempRec.get("DateUTC"));

    // tmax.testColdestHourInFile();

    tmax.testFileWithColdestTemperature();

    // tmax.testLowestHumidityInFile();

    // tmax.testLowestHumidityInManyFiles();

    // tmax.testAverageTemperatureInFile();

    // tmax.testAverageTemperatureWithHighHumidityInFile();
    
  }
}
