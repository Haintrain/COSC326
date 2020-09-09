import java.util.*;

public class CordlessPhones {
    private static int numberOfPoints = 1000;
    private static Vector2[] locations;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();

        String firstLine = scanner.nextLine();

        if(firstLine.equals("Telephone sites")){
            while(scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            scanner.close();

            int lineCount = lines.size();
            numberOfPoints = lineCount;

            locations = new Vector2[numberOfPoints];

            for(int i = 0; i < numberOfPoints; i++){
                locations[i] = new Vector2();

                String coord[] = lines.get(i).trim().split("\\s+");

                locations[i].x = Double.valueOf(coord[0]);
                locations[i].y = Double.valueOf(coord[1]);
            }

            CordlessPhones main = new CordlessPhones();
            System.out.println(main.containsTwelvePoints());
        }
        else{
            scanner.close();
            System.out.println("Not telephone sites first line");
        }
    }

    public Double containsTwelvePoints(){
        double minRadii = Double.MAX_VALUE;
        Vector2 minLocation = new Vector2();

        if(numberOfPoints < 12){
            return Double.POSITIVE_INFINITY;
        }

        A:
        for(Vector2 i: locations){
            B:
            for(Vector2 j: locations){
                if(Math.hypot(j.x - i.x, j.y - i.y)/2 > minRadii){
                    //System.out.println("Skipped: " + i.x + " " + i.y + " " + j.x + " " + j.y);
                    continue;
                }
                int num = 0, numEdge = 0;

                Vector2 point = new Vector2(Math.abs(i.x + j.x)/2, Math.abs(i.y + j.y)/2);
                double radius2 = Math.hypot(point.x - j.x, point.y - j.y);

                for(Vector2 k: locations){
                    if(i == j) {
                        continue B;
                    }

                    if(Math.hypot(point.x - k.x, point.y - k.y) < radius2){
                        num++;
                    }
                    else if(Math.hypot(point.x - k.x, point.y - k.y) == radius2){
                        numEdge++;
                    }

                    if(i == k || j == k){
                        continue;
                    }
                    else if(Math.hypot(k.x - i.x, k.y - i.y)/2 > minRadii || Math.hypot(k.x - j.x, k.y - j.y)/2 > minRadii){
                        continue;
                    }
                    else{
                        Vector2 center = calculateCircleCenter(i, j, k);

                        if(center != null) {
                            double radius = Math.hypot(center.x - i.x, center.y - i.y);
                            int numPoints = 0, numPointsEdge = 0;

                            if (radius < minRadii) {
                                for (Vector2 l : locations) {
                                    double distance = Math.hypot(center.x - l.x, center.y - l.y);

                                    if (radius > distance) {
                                        numPoints++;
                                    }
                                    else if(radius == distance){
                                        //System.out.print(l.x + "-" + l.y);
                                        numPointsEdge++;
                                    }
                                }

                                if (numPoints + numPointsEdge > 11 && 11 > numPoints) {
                                    minRadii = radius;
                                    minLocation.x = center.x;
                                    minLocation.y = center.y;

                                    //System.out.println("Three: " + i.x + "-" + i.y + " " + j.x + "-" + j.y + " "  + k.x + "-" + k.y + " " + numPoints + " " + numPointsEdge);
                                    //System.out.println("Three: " + minLocation.x + " " + minLocation.y + " " + minRadii);
                                }
                            }
                        }
                    }
                }

                //System.out.println(i.x + " " + i.y + " " + j.x + " " + j.y + " " + num + " " + numEdge + " " + radius2);

                if (num + numEdge > 11 && 11 > num) {
                    minRadii = radius2;
                    minLocation.x = point.x;
                    minLocation.y = point.y;

                    //System.out.println("Two: " + i.x + "-" + i.y + " " + j.x + "-" + j.y + " " + num + " " + numEdge);
                    //System.out.println("Two: " + minLocation.x + " " + minLocation.y + " " + minRadii);
                }
            }
        }

        //System.out.println(minLocation.x + " " + minLocation.y);
        return minRadii;
    }

    public Vector2 calculateCircleCenter(Vector2 p1, Vector2 p2, Vector2 p3)
    {
        Vector2 swap;

        if((p3.y - p2.y) * (p2.x - p1.x) == (p2.y - p1.y) * (p3.x - p2.x)){
            return null;
        }

        if (p2.x - p1.x == 0) {
            swap = p2;
            p2 = p3;
            p3 = swap;
        }
        else if(p3.x - p2.x == 0){
            swap = p2;
            p2 = p1;
            p1 = swap;
        }

        Vector2 center = new Vector2();
        double ma = (p2.y - p1.y) / (p2.x - p1.x);
        double mb = (p3.y - p2.y) / (p3.x - p2.x);
        center.x = (ma * mb * (p1.y - p3.y) + mb * (p1.x + p2.x) - ma * (p2.x + p3.x)) / (2 * (mb - ma));
        center.y = (-1 / ma) * (center.x - (p1.x + p2.x) * 0.5) + (p1.y + p2.y) * 0.5;

        return center;
    }
}

class Vector2{
    double x;
    double y;

    Vector2(){

    }

    Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }
}


