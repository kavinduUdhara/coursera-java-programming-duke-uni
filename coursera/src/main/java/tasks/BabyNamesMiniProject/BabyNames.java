package tasks.BabyNamesMiniProject;

import edu.duke.*;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.*;

public class BabyNames {

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

  public void readOneFile(int year){
    String fname = String.format("coursera\\src\\main\\java\\tasks\\BabyNamesMiniProject\\data\\us_babynames_by_year\\yob%d.csv", year);
    FileResource fr = new FileResource(fname);
    CSVParser parser = fr.getCSVParser(false);
    for (CSVRecord record : parser){
      String name = record.get(0);
      String gender = record.get(1);
      String numBoarn = record.get(2);
      System.out.format("Name %s | Gender %s | Num Born %s%n", name, gender, numBoarn);
    }
  }

  public void totalBriths(FileResource fr){
    int totalBirths = 0;
    int totalBoys = 0;
    int totalNames = 0;
    int totalBoyNames = 0;
    for (CSVRecord record : fr.getCSVParser(false)){
      totalBirths += Integer.parseInt(record.get(2));
      totalNames ++;
      if (record.get(1).equals("M")){
        totalBoys += Integer.parseInt(record.get(2));
        totalBoyNames ++;
      }
    }
    System.out.format("%-15s %10d%n", "Total Boys:", totalBoys);
    System.out.format("%-15s %10d%n", "Total Girls:", totalBirths - totalBoys);
    System.out.format("%-15s %10d%n", "Total:", totalBirths);
    System.out.println("________________________________________________________________");
    System.out.format("%-15s %10d%n", "Total Boys Names:", totalBoyNames);
    System.out.format("%-15s %10d%n", "Total Girls Names:", totalNames - totalBoyNames);
    System.out.format("%-15s %10d%n", "Total Names:", totalNames);
  }
  
  public void testTotalBirths(){
    FileResource fr = new FileResource("coursera\\src\\main\\java\\tasks\\BabyNamesMiniProject\\data\\us_babynames_by_year\\yob1905.csv");
    totalBriths(fr);
  }

  public List<Record> rankList(int year, String g){
    List<Record> records = new ArrayList<>();
    String fname = String.format("coursera\\src\\main\\java\\tasks\\BabyNamesMiniProject\\data\\us_babynames_by_year\\yob%d.csv", year);
    FileResource fr = new FileResource(fname);
    for (CSVRecord record : fr.getCSVParser(false)){
      String name = record.get(0);
      String gender = record.get(1);
      int count = Integer.parseInt(record.get(2));
      if (gender.equals(g)){
        records.add(new Record(name, gender, count));
      }
    }
    records.sort((a, b) -> Integer.compare(b.count, a.count));
    return records;
  }

  public Map<String, Integer> rankedMap(int year, String g){
    List<Record> records = rankList(year, g);
    HashMap<String, Integer> rankMap = new HashMap<>();
      // int rank = 1;
      // Record prevRec = records.get(0);
      // rankMap.put(prevRec.name + ":" + prevRec.gender, 1);
      // for (Record r : records.subList(1, records.size())) {
      //   // System.out.format("%s | %s | %d%n", r.name, r.gender, r.count);
      //   if (prevRec.count != r.count){
      //     rank += 1;
      //   }
      //   prevRec = r;
      //   rankMap.put(r.name + ":" + r.gender, rank);
    // }
      int rank = 0;
      for (Record r : records){
        rank ++;
        rankMap.put(r.name + ":" + r.gender, rank);
      }
    // System.out.println(rankMap);
    return rankMap;
  }
  
  public Integer getRank(int year, String name, String gender){
    Map<String, Integer> RankMap = rankedMap(year, gender);
    Integer rank = RankMap.getOrDefault(String.format("%s:%s", name, gender), -1);
    return rank;
  }

  public String getName(int year, int rank, String gender){
    Map<String, Integer> rankMap = rankedMap(year, gender);
    for (Map.Entry<String, Integer> entry : rankMap.entrySet()){
      if (entry.getKey().contains(gender) && entry.getValue() == rank){
        return entry.getKey().split(":")[0];
      }
    }
    return "NO NAME";
  }

  public void testGetName(){
    String name = getName(1980, 350, "F");
    System.out.println(name);
    String name2 = getName(1982, 450, "M");
    System.out.println(name2);
  }

  public void whatIsNameInYear(String name, int year, int newYear, String gender){
    int rank = getRank(year, name, gender);
    String newName = getName(newYear, rank, gender);
    System.out.format("%s born in %d would be %s if she was born in %d.%n", name, year, newName, newYear);
  }

  public int yearOfHighestRank(String name, String gender){
    int heightRankSoFar = Integer.MAX_VALUE;
    int heightYear = 0;

    DirectoryResource dr = new DirectoryResource();
    for (File file : dr.selectedFiles()){
      Matcher matcher = Pattern.compile("^yob(\\d{4})(?:short)?\\.csv$").matcher(file.getName());
      int year = 0;
      if (matcher.matches()){
        year = Integer.parseInt(matcher.group(1));
      }
      int heightRankInFile = getRank(year, name, gender); 
      if (heightRankSoFar > heightRankInFile){
        heightRankSoFar = heightRankInFile;
        heightYear = year;
        // System.out.println(year);
        // System.out.println(heightRankInFile);
      }
    }
    // System.out.println(heightYear);
    return heightYear;
  }

  public void testYearOfHighestRank(){
    int year = yearOfHighestRank("Genevieve", "F");
    System.out.format("Highest ranking was in " + year);
    int year2 = yearOfHighestRank("Mich", "M");
    System.out.format("Highest ranking was in " + year2);
  }

  public Double getAverageRank(String name, String gender){
    int count = 0;
    Double total = 0.0;
    DirectoryResource dr = new DirectoryResource();
    for (File file : dr.selectedFiles()){
      Matcher matcher = Pattern.compile("^yob(\\d{4})(?:short)?\\.csv$").matcher(file.getName());
      int year = 0;
      if (matcher.matches()){
        year = Integer.parseInt(matcher.group(1));
      }
      int rankInFile = getRank(year, name, gender);
      if (rankInFile != -1){
        // System.out.println(year + " " + rankInFile);
        count ++;
        total += rankInFile;
      }
    }
    if (count == 0){
      return -1.0;
    } else{
      return total / count;
    }
  }

  public void testGetAverageRank(){
    Double avgRank = getAverageRank("Susan", "F");
    System.out.println("Rank: " + avgRank);
    Double avgRank1 = getAverageRank("Robert", "M");
    System.out.println("Rank: " + avgRank1);
  }

  public Integer getTotalBirthsRankedHigher(String name, String gender, int year){
    List<Record> records = rankList(year, gender);
    int totalBirths = 0;
    for (Record r : records){
      // System.out.format("%s | %s | %d%n", r.name, r.gender, r.count);
      if (r.name.equals(name) && r.gender.equals(gender)){
        return totalBirths;
      } else{
        totalBirths += r.count;
      }
    }
    return 0;
  }

  public void testGetTotalBirthsRankedHigher(){
    int totalRanks = getTotalBirthsRankedHigher("Emily", "F", 1990);
    System.out.println("total tanks top: " + totalRanks);
    // int totalRanks = getTotalBirthsRankedHigher("Drew", "M", 1990);
    // System.out.println("total tanks top: " + totalRanks);
  }

  public static void main(String[] args) {
    BabyNames bn = new BabyNames();

    // bn.readOneFile(1880);

    // bn.testTotalBirths();

    // int rank = bn.getRank(1960, "Emily", "F");
    // System.out.format("Rank: %d%n", rank);
    // int rank2 = bn.getRank(1971, "Frank", "M");
    // System.out.format("Rank: %d%n", rank2);

    // bn.testGetName();

    // bn.whatIsNameInYear("Susan", 1972, 2014, "F");
    // bn.whatIsNameInYear("Owen", 1974, 2014, "M");

    // bn.testYearOfHighestRank();

    bn.testGetAverageRank();

    // bn.testGetTotalBirthsRankedHigher();
  }
}
