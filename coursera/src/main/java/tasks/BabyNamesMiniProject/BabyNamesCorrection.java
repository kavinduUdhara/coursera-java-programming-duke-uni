package tasks.BabyNamesMiniProject;

import edu.duke.*;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.*;

public class BabyNamesCorrection {

  // Simple Record class to hold name, gender, and count
  public static class Record {
    public String name;
    public String gender;
    public int count;

    public Record(String name, String gender, int count) {
      this.name = name;
      this.gender = gender;
      this.count = count;
    }
  }

  // CORRECTED: totalBirths method - now prints number of names, not just births
  public void totalBirths(FileResource fr){
    int totalBirths = 0;
    int totalBoys = 0;
    int boysNames = 0;
    int girlsNames = 0;
    int totalNames = 0;
    
    for (CSVRecord record : fr.getCSVParser(false)){
      totalBirths += Integer.parseInt(record.get(2));
      totalNames++;
      
      if (record.get(1).equals("M")){
        totalBoys += Integer.parseInt(record.get(2));
        boysNames++;
      } else {
        girlsNames++;
      }
    }
    
    System.out.format("%-20s %10d%n", "Total Boys Names:", boysNames);
    System.out.format("%-20s %10d%n", "Total Girls Names:", girlsNames);
    System.out.format("%-20s %10d%n", "Total Names:", totalNames);
    System.out.format("%-20s %10d%n", "Total Boys Births:", totalBoys);
    System.out.format("%-20s %10d%n", "Total Girls Births:", totalBirths - totalBoys);
    System.out.format("%-20s %10d%n", "Total Births:", totalBirths);
  }
  
  // CORRECTED: getRank method - now properly handles gender-specific ranking
  public int getRank(int year, String name, String gender){
    String fname = String.format("coursera\\src\\main\\java\\tasks\\BabyNamesMiniProject\\data\\us_babynames_by_year\\yob%d.csv", year);
    FileResource fr = new FileResource(fname);
    
    List<Record> records = new ArrayList<>();
    for (CSVRecord record : fr.getCSVParser(false)){
      String recordName = record.get(0);
      String recordGender = record.get(1);
      int count = Integer.parseInt(record.get(2));
      
      // Only include records with the matching gender
      if (recordGender.equals(gender)) {
        records.add(new Record(recordName, recordGender, count));
      }
    }
    
    // Sort by count in descending order (highest births first)
    records.sort((a, b) -> Integer.compare(b.count, a.count));
    
    // Find the rank of the specified name
    for (int i = 0; i < records.size(); i++) {
      if (records.get(i).name.equals(name)) {
        return i + 1; // Rank is 1-based
      }
    }
    
    return -1; // Name not found
  }

  // CORRECTED: getName method - now properly finds name by rank and gender
  public String getName(int year, int rank, String gender){
    String fname = String.format("coursera\\src\\main\\java\\tasks\\BabyNamesMiniProject\\data\\us_babynames_by_year\\yob%d.csv", year);
    FileResource fr = new FileResource(fname);
    
    List<Record> records = new ArrayList<>();
    for (CSVRecord record : fr.getCSVParser(false)){
      String recordName = record.get(0);
      String recordGender = record.get(1);
      int count = Integer.parseInt(record.get(2));
      
      // Only include records with the matching gender
      if (recordGender.equals(gender)) {
        records.add(new Record(recordName, recordGender, count));
      }
    }
    
    // Sort by count in descending order (highest births first)
    records.sort((a, b) -> Integer.compare(b.count, a.count));
    
    // Check if rank is valid
    if (rank > 0 && rank <= records.size()) {
      return records.get(rank - 1).name; // Convert to 0-based index
    }
    
    return "NO NAME";
  }

  // CORRECTED: whatIsNameInYear method - now uses proper gender pronouns
  public void whatIsNameInYear(String name, int year, int newYear, String gender){
    int rank = getRank(year, name, gender);
    if (rank == -1) {
      System.out.format("%s with gender %s was not found in year %d%n", name, gender, year);
      return;
    }
    
    String newName = getName(newYear, rank, gender);
    String pronoun = gender.equals("F") ? "she" : "he";
    
    System.out.format("%s born in %d would be %s if %s was born in %d.%n", 
                     name, year, newName, pronoun, newYear);
  }

  // CORRECTED: yearOfHighestRank method - now finds the year with the BEST rank (lowest number)
  public int yearOfHighestRank(String name, String gender){
    int bestRank = Integer.MAX_VALUE; // Best rank is the lowest number
    int bestYear = -1;

    DirectoryResource dr = new DirectoryResource();
    for (File file : dr.selectedFiles()){
      Matcher matcher = Pattern.compile("^yob(\\d{4})(?:short)?\\.csv$").matcher(file.getName());
      if (matcher.matches()){
        int year = Integer.parseInt(matcher.group(1));
        int rankInFile = getRank(year, name, gender); 
        
        if (rankInFile != -1 && rankInFile < bestRank){
          bestRank = rankInFile;
          bestYear = year;
        }
      }
    }
    
    return bestYear;
  }

  // The getAverageRank method looks correct in your original code

  public double getAverageRank(String name, String gender){
    int count = 0;
    double total = 0.0;
    DirectoryResource dr = new DirectoryResource();
    
    for (File file : dr.selectedFiles()){
      Matcher matcher = Pattern.compile("^yob(\\d{4})(?:short)?\\.csv$").matcher(file.getName());
      if (matcher.matches()){
        int year = Integer.parseInt(matcher.group(1));
        int rankInFile = getRank(year, name, gender);
        if (rankInFile != -1){
          count++;
          total += rankInFile;
        }
      }
    }
    
    if (count == 0){
      return -1.0;
    } else{
      return total / count;
    }
  }

  // CORRECTED: getTotalBirthsRankedHigher method - parameters in correct order
  public int getTotalBirthsRankedHigher(int year, String name, String gender){
    String fname = String.format("coursera\\src\\main\\java\\tasks\\BabyNamesMiniProject\\data\\us_babynames_by_year\\yob%d.csv", year);
    FileResource fr = new FileResource(fname);
    
    List<Record> records = new ArrayList<>();
    for (CSVRecord record : fr.getCSVParser(false)){
      String recordName = record.get(0);
      String recordGender = record.get(1);
      int count = Integer.parseInt(record.get(2));
      
      // Only include records with the matching gender
      if (recordGender.equals(gender)) {
        records.add(new Record(recordName, recordGender, count));
      }
    }
    
    // Sort by count in descending order (highest births first)
    records.sort((a, b) -> Integer.compare(b.count, a.count));
    
    int totalBirths = 0;
    for (Record r : records){
      if (r.name.equals(name)){
        return totalBirths; // Found the name, return total births of higher ranked names
      }
      totalBirths += r.count;
    }
    
    return 0; // Name not found, return 0
  }

  // Test methods
  public void testTotalBirths(){
    FileResource fr = new FileResource("coursera\\src\\main\\java\\tasks\\BabyNamesMiniProject\\data\\us_babynames_by_year\\yob1900.csv");
    totalBirths(fr);
  }

  public void testGetRank(){
    int rank = getRank(1960, "Emily", "F");
    System.out.format("Emily rank in 1960 (F): %d %n", rank);
    
    rank = getRank(1971, "Frank", "M");
    System.out.format("Mason rank in 2012 (F): %d (should be -1)%n", rank);
  }

  public void testGetName(){
    String name = getName(1980, 350, "F");
    System.out.println(name);
    String name2 = getName(1982, 450, "M");
    System.out.println(name2);
  }

  public void testWhatIsNameInYear(){
    whatIsNameInYear("Isabella", 2012, 2014, "F");
  }

  public void testYearOfHighestRank(){
    int year = yearOfHighestRank("Genevieve", "F");
    System.out.format("Highest ranking was in " + year);
    int year2 = yearOfHighestRank("Mich", "M");
    System.out.format("Highest ranking was in " + year2);
  }

  public void testGetAverageRank(){
    Double avgRank = getAverageRank("Susan", "F");
    System.out.println("Rank: " + avgRank);
    Double avgRank1 = getAverageRank("Robert", "M");
    System.out.println("Rank: " + avgRank1);
  }

  public void testGetTotalBirthsRankedHigher(){
    int totalBirths = getTotalBirthsRankedHigher(2012, "Ethan", "M");
    System.out.format("Total births ranked higher than Ethan in 2012: %d%n", totalBirths);
  }

  public static void main(String[] args) {
    BabyNamesCorrection bn = new BabyNamesCorrection();
    
    // System.out.println("=== Testing Corrected Methods ===");
    
    // System.out.println("\n1. Testing totalBirths:");
    // bn.testTotalBirths();
    
    // System.out.println("\n2. Testing getRank:");
    // bn.testGetRank();
    
    // System.out.println("\n3. Testing getName:");
    // bn.testGetName();
    
    // System.out.println("\n4. Testing whatIsNameInYear:");
    // bn.testWhatIsNameInYear();
    
    // System.out.println("\n5. Testing yearOfHighestRank:");
    // bn.testYearOfHighestRank();
    
    System.out.println("\n6. Testing getAverageRank:");
    bn.testGetAverageRank();
    
    // System.out.println("\n7. Testing getTotalBirthsRankedHigher:");
    // bn.testGetTotalBirthsRankedHigher();
  }
}