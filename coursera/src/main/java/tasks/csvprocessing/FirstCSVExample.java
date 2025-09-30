/**
 * Reads a chosen CSV file of our preferences and prints each field.
 * 
 * @author Duke Software Team
 */
package tasks.csvprocessing;
import edu.duke.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class FirstCSVExample {
	public void readFood() {
		FileResource fr = new FileResource("coursera\\src\\main\\java\\tasks\\csvprocessing\\foods.csv");
		// System.out.println(fr.)
		CSVParser parser = fr.getCSVParser();
		for (CSVRecord record : parser){
			System.out.print(record.get("Name") + " ");
			System.out.print(record.get("Favorite Color") + " ");
			System.out.println(record.get("Favorite Food"));
		}
	}

	public static void main(String[] args){
		FirstCSVExample csv = new FirstCSVExample();
		csv.readFood();
	}
}
