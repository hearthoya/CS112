import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;

//Sources
//https://stackoverflow.com/questions/42855224/how-to-add-rgb-values-into-setcolor-in-java
//https://www.tutorialspoint.com/importance-of-override-annotation-in-java#:~:text=The%20%40Override%20annotation%20indicates%20that,writing%20its%20base%20class%20method.&text=It%20extracts%20a%20warning%20from,readability%20of%20the%20source%20code.
//https://blog.hubspot.com/website/addition-assignment-operator-java#:~:text=What%20does%20%2B%3D%20Operator%20mean,to%20x%3Dx%2By.
public class DrawToScreen extends JPanel {
    public static final int width = 1024;
    public static final int height = 768;

    public DrawToScreen() {
        this.setPreferredSize(new Dimension(width, height));
    }
    // Your code here, if you want to define additional methods.

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // set background
        g.setColor(new Color(255, 255, 255, 255));
        g.fillRect(0, 0, width, height);
        //set default y
        int y = 42;
        //ratios
        double ratiow = .5;
        double ratioh = .5;
        // Your code here: feel free to remove what is below
        for (int cols = 0; cols < 5; cols++) {
            //set default x for each row
            int x = 1;
            ratiow += .2 * cols;
            for (int rows = 0; rows < 5; rows++) {
                //body
                g.setColor(new Color(201, 134, 149));
                g.fillOval(x, y, (int) (59 * ratiow), (int) (ratioh * 78));
                //nose
                g.fillOval(x, y + (int) (25 * ratioh), (int) (83 * ratiow), (int) (ratioh * 39));
                //eye
                g.setColor(Color.BLACK);
                g.fillOval(x + (int) (34 * ratiow), y + (int) (39 * ratioh), (int) (5 * ratiow), (int) (ratioh * 5));
                //mouth
                g.fillRect(x + (int) (40 * ratiow), y + (int) (65 * ratioh), (int) (10 * ratiow), (int) (ratioh * 3));
                //hat
                g.setColor(new Color(105, 66, 66));
                g.fillRoundRect(x + (int) (5 * ratiow), y, (int) (49 * ratiow), (int) (ratioh * 5), (int) (5 * ratiow), (int) (ratioh * 5));
                g.fillOval(x + (int) (10 * ratiow), y - (int) (39 * ratioh), (int) (39 * ratiow), (int) (ratioh * 44));
                x += 150;
                ratioh += .15;
            }
            //draw shapes down by 150 pixels
            y += 150;
            //reset values
            ratioh = .5;
            ratiow = .5;
        }
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("DrawToScreen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new DrawToScreen());
        frame.pack();
        frame.setVisible(true);
    }
}