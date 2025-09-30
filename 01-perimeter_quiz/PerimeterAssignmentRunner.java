import edu.duke.*;
import java.io.File;
import java.io.FilenameFilter;

import javax.annotation.processing.FilerException;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        int i = 0;
        for(Point currPoint : s.getPoints()){
            i = i + 1;
        }
        return i;
    }

    public double getAverageLength(Shape s) {
        double count = getNumPoints(s);
        double totalDis = 0;
        Point prevPoint = s.getLastPoint();
        for (Point currPoint : s.getPoints()){
            totalDis = totalDis + prevPoint.distance(currPoint);
            prevPoint = currPoint;
        }
        return totalDis / count;
    }

    public double getLargestSide(Shape s) {
    double largestSide = 0;
    Point prevPoint = s.getLastPoint();
    for (Point currPoint : s.getPoints()) {
        double dis = prevPoint.distance(currPoint);
        if (dis > largestSide) {
            largestSide = dis;
        }
        prevPoint = currPoint; // <-- crucial update
    }
    return largestSide;
}


    public double getLargestX(Shape s) {
        int largestX = 0;
        for (Point currPoint : s.getPoints()){
            int x = currPoint.getX();
            if (largestX < x){
                largestX = x;
            }
        }
        return largestX;
    }

    public double getLargestPerimeterMultipleFiles(String[] Files) {
        double LargestPerimeter = 0;
        for (String FileName : Files){
            FileResource fr = new FileResource(FileName);
            Shape s = new Shape(fr);
            double perimeter = getPerimeter(s);
            if (LargestPerimeter < perimeter){
                LargestPerimeter = perimeter;
            }
        }
        return LargestPerimeter;
    }

    public String getFileWithLargestPerimeter(String[] Files) {
        double LargestPerimeter = 0;
        String fn= "";
        for (String FileName : Files){
            FileResource fr = new FileResource(FileName);
            Shape s = new Shape(fr);
            double perimeter = getPerimeter(s);
            if (LargestPerimeter < perimeter){
                LargestPerimeter = perimeter;
                fn = FileName;
            }
        }
        return fn;
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        System.out.println("perimeter = " + length);
    }
    
    public void testPerimeterMultipleFiles(String[] Files) {
        System.out.println(getLargestPerimeterMultipleFiles(Files));
    }

    public void testFileWithLargestPerimeter(String[] Files) {
        System.out.println(getFileWithLargestPerimeter(Files));
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        //pr.testPerimeter();
        FileResource fr = new FileResource("datatest1.txt");
        Shape s = new Shape(fr);
        int totalPoints = pr.getNumPoints(s);
        double avgLen = pr.getAverageLength(s);
        double largestSide = pr.getLargestSide(s);
        double perimeter = pr.getPerimeter(s);
        System.out.printf("perimeter: %.2f avgLen: %.2f largestSide: %.2f%n", perimeter, avgLen, largestSide);

        System.out.println("__________");
        pr.testFileWithLargestPerimeter(new String[] {"example1.txt", "example2.txt", "example3.txt", "example4.txt"});
    }
}
