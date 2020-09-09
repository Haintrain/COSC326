import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class QuiltingBee extends JFrame {

    static int defaultScale;

    static ArrayList<Values> values = new ArrayList<>();
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public QuiltingBee(){

        setTitle("Quilting Bee");
        setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        double totalScale = 0;

        try {
            Scanner sc = new Scanner(System.in);

            while (sc.hasNextLine()) {
                String[] inputs = sc.nextLine().split(" ");
                values.add(new Values(Double.parseDouble(inputs[0]), Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]), Integer.parseInt(inputs[3])));
            }

            sc.close();
        }
        catch(Exception e){
            System.out.println(e);
        }

        for(int i = 0; i < values.size(); i++){
            totalScale += values.get(i).scale;
        }

        defaultScale = (int)((screenSize.getHeight() - 100)/(totalScale * 2));
        QuiltingBee main = new QuiltingBee();
    }

    public void paintRecursive(Graphics g, int x, int y, int depth, int previousScale) {
        if(depth >= values.size()){
            return;
        }

        Values value = values.get(depth);
        Color newColor;

        int newScale;
        int newX, newY;
        newScale = (int) (defaultScale * value.scale);

        if (depth == 0) {
            newColor = new Color(value.rgb[0], value.rgb[1], value.rgb[2]);
            g.setColor(newColor);
            g.fillRect(x, y, newScale, newScale);
            paintRecursive(g, x, y, depth + 1, newScale);
        }
        else {
            // Bottom Right Square
            newX = x + previousScale - (int)(newScale * 0.5);
            newY = y + previousScale - (int)(newScale * 0.5);
            newColor = new Color(value.rgb[0], value.rgb[1], value.rgb[2]);
            g.setColor(newColor);
            g.fillRect(newX, newY, newScale, newScale);
            paintRecursive(g, newX, newY, depth + 1, newScale);
            // Bottom Left Square
            newX = x - (int)(newScale * 0.5);
            newY = y + previousScale - (int)(newScale * 0.5);
            newColor = new Color(value.rgb[0], value.rgb[1], value.rgb[2]);
            g.setColor(newColor);
            g.fillRect(newX, newY, newScale, newScale);
            paintRecursive(g, newX, newY, depth + 1, newScale);
            // Top Left Square
            newX = x - (int)(newScale * 0.5);
            newY = y - (int)(newScale * 0.5);
            newColor = new Color(value.rgb[0], value.rgb[1], value.rgb[2]);
            g.setColor(newColor);
            g.fillRect(newX, newY, newScale, newScale);
            paintRecursive(g, newX, newY, depth + 1, newScale);
            // Top Right Square
            newX = x + previousScale - (int)(newScale * 0.5);
            newY = y - (int)(newScale * 0.5);
            newColor = new Color(value.rgb[0], value.rgb[1], value.rgb[2]);
            g.setColor(newColor);
            g.fillRect(newX, newY, newScale, newScale);
            paintRecursive(g, newX, newY, depth + 1, newScale);
        }
    }

    public void paint(Graphics g) {
        paintRecursive(g, (int)(screenSize.getWidth()/2 - defaultScale * 0.5), (int)(screenSize.getHeight()/2 - defaultScale * 0.5), 0, defaultScale);
    }

}

class Values{
    double scale;
    int[] rgb = new int[3];

    Values(double scale, int r, int g, int b){
        this.scale = scale;
        rgb[0] = r;
        rgb[1] = g;
        rgb[2] = b;
    }
}
