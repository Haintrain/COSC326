import java.util.*;


class Vertex implements Comparable<Vertex>
{
    public final String name;
    public ArrayList<Edge> adjacencies =  new ArrayList<>();
    public double minDistance = Double.POSITIVE_INFINITY;

    public Vertex previous;

    public Vertex(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

}


class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight) {
        target = argTarget; weight = argWeight;
    }
}

public class BusRoutes {
    public static boolean invalid;

    public static int from = -1;
    public static int to = -1;

    public static boolean isNumeric(String number){
        if(number == null){
            return false;
        }

        try {
            double d = Double.parseDouble(number);
        }
        catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        ArrayList<String> cityNames = new ArrayList<>();
        ArrayList<String> routes = new ArrayList<>();
        Vertex cities[];
        String[] firstLine = null;

        try {
            if (System.in.available() == 0) {
                System.out.println("Invalid: No input");
                System.exit(0);
            }

            Scanner sc = new Scanner(System.in);

            String firstLineLine = sc.nextLine().replaceAll("\\s", "");
            firstLineLine = firstLineLine.toLowerCase();
            firstLine = firstLineLine.split(",");


            if (firstLine.length != 2) {
                System.out.println("Invalid: route");
                invalid = true;
                System.exit(0);
            }

            int i = 0;

            while (sc.hasNextLine() && !invalid) {
                routes.add(sc.nextLine().replaceAll("\\s", "").toLowerCase());
                i++;
            }

            sc.close();
        }
        catch (Exception e){

        }

        for(int k = 0; k < routes.size(); k++){
            String[] routeInfo = routes.get(k).split(",");

            if(routeInfo.length != 3){
                System.out.println("Invalid: route set");
                invalid = true;
            }
            else if(!isNumeric(routeInfo[2])){
                System.out.println("Invalid: route set");
                invalid = true;
            }
            else {
                if (!cityNames.contains(routeInfo[0])) {
                    cityNames.add(routeInfo[0]);
                }
                if (!cityNames.contains(routeInfo[1])) {
                    cityNames.add(routeInfo[1]);
                }
            }
        }

        if(!invalid) {
            cities = new Vertex[cityNames.size()];
            int depature = -1, arrival = -1;

            for (int j = 0; j < cityNames.size(); j++) {
                cities[j] = new Vertex(cityNames.get(j));
            }

            for (String route : routes) {
                String[] routeInfo = route.split(",");

                for (int l = 0; l < cities.length; l++) {
                    if (cities[l].name.equals(firstLine[0])) {
                        from = l;
                    }
                    else if (cities[l].name.equals(firstLine[1])) {
                        to = l;
                    }

                    if (cities[l].name.equals(routeInfo[0])) {
                        depature = l;
                    }
                    else if (cities[l].name.equals(routeInfo[1])) {
                        arrival = l;
                    }
                }


                double cost = Double.parseDouble(routeInfo[2]);
                if(depature != -1 && arrival != -1) {
                    cities[depature].adjacencies.add(new Edge(cities[arrival], cost));
                    cities[arrival].adjacencies.add(new Edge(cities[depature], cost));
                }
            }

            if (!invalid) {
                for (int i = 0; i < cities.length; i++) {
                    if (cities[i].adjacencies != null) {
                        for (int j = 0; j < cities[i].adjacencies.size(); j++) {
                            for (int o = 0; o < cities[i].adjacencies.size(); o++) {
                                if (j != o && cities[i].adjacencies.get(j).target.name.equals(cities[i].adjacencies.get(o).target.name)) {
                                    System.out.println("Invalid: Non-unique routes");
                                    invalid = true;
                                    return;
                                }
                            }
                        }
                    }
                }

                if (!invalid) {
                    computePaths(cities[from]);
                    List<Vertex> shortestPath = getShortestPathTo(cities[to]);
                    String output = shortestPath.get(0).toString().toLowerCase();

                    for (int p = 1; p < shortestPath.size(); p++) {
                        output += "-" + shortestPath.get(p).toString().toLowerCase();
                    }

                    System.out.println(output);
                }
            }
        }
    }


    public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);

                    v.minDistance = distanceThroughU ;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }

}