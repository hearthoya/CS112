
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.GradientPaint;

public class Skyline extends JPanel {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Skyline");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Skyline());
        frame.pack();
        frame.setVisible(true);
    }

    public Skyline() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void paintComponent(Graphics gOri) {
        Graphics2D g = (Graphics2D) gOri;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint sunSet = new GradientPaint(0, 0, Color.BLACK, 0, HEIGHT, new Color(86, 45, 129, 255));
        g.setPaint(sunSet);
        g.fill(new Rectangle2D.Double(0, 0, WIDTH, HEIGHT));
        Random rand = new Random();
        //Your code
        int height = rand.nextInt(95, 106);
        //stars
        g.setColor(new Color(255, 255, 255));
        for (int i = 0; i < 100; i++) {
            Ellipse2D.Double star = new Ellipse2D.Double(rand.nextInt(WIDTH + 1), rand.nextInt(HEIGHT + 1), 1, 1);
            g.fill(star);
            g.draw(star);
        }
        //create array for lines on horizon
        Rectangle2D.Double[] horizon = new Rectangle2D.Double[WIDTH];
        //set line color
        g.setColor(new Color(176, 172, 172));
        //draw initial line
        horizon[0] = new Rectangle2D.Double(0, HEIGHT - height, 1, height);
        //create horizon lines
        for (int j = 1; j < WIDTH; j++) {
            horizon[j] = new Rectangle2D.Double(j, HEIGHT - (horizon[j - 1].height + (rand.nextInt(-5, 6))), 1, (horizon[j - 1].height + (rand.nextInt(-5, 6))));
        }
        for (Rectangle2D.Double k : horizon) {
            g.draw(k);
            g.fill(k);
        }
    }
}