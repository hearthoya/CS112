import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

//Sources
//https://docs.oracle.com/javase/tutorial/java/javaOO/objectcreation.html#:~:text=Instantiation%3A%20The%20new%20keyword%20is,which%20initializes%20the%20new%20object.
//https://docs.oracle.com/javase/8/docs/api/
//https://stackoverflow.com/questions/42855224/how-to-add-rgb-values-into-setcolor-in-java
//https://stackoverflow.com/questions/1783793/java-difference-between-the-setpreferredsize-and-setsize-methods-in-compone#:~:text=setSize%20will%20resize%20the%20component,re%2Dsized%20the%20component%20manually.
//went to two TA hours

public class Spinning extends JPanel {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;

    public static final double CENTERX = WIDTH / 2.0;
    public static final double CENTERY = HEIGHT / 2.0;
    public static Sphere[] spheres;

    public Spinning() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void Go() {
        while (true) {
            for (Sphere s : spheres) {
                s.update(1.0 / (double) FPS);
            }
            //don't mess too much with the rest of this method
            repaint();
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) {
        int numSpheres = 3;
        //conditionals
        if (args.length < 1) {
            System.out.println("When you run this, you can specify the number of spheres.");
            System.out.println("e.g., java Spinning 10");
        } else {
            System.out.println("You specified that there should be " + args[0] + " spheres.");
            numSpheres = Integer.parseInt(args[0]);
        }
        spheres = new Sphere[numSpheres];
        for (int i = 0; i < spheres.length; i++) {
            spheres[i] = new Sphere(700 * Math.random(), 700 * Math.random(), Math.random() * 500, Math.random() * 400, (int) (Math.random() * 50 + 10), (int) (Math.random() * 10), new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }
        JFrame frame = new JFrame("Spinning Spheres");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Spinning world = new Spinning();
        frame.setContentPane(world);
        frame.pack();
        frame.setVisible(true);
        world.Go();
    }

    public void paintComponent(Graphics g) {
        //background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for (Sphere s : spheres) {
            g.setColor(s.color);
            s.draw(g);
        }
    }

    static class Sphere {
        double positionX;
        double positionY;
        double velocityX;
        double velocityY;
        int diameter;
        int directionControl;
        Color color;

        public Sphere(double positionX, double positionY, double velocityX, double velocityY, int diameter, int directionControl, Color color) {
            //This is the constructor
            this.positionX = positionX;
            this.positionY = positionY;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.diameter = diameter;
            this.directionControl = directionControl;
            this.color = color;
        }
        
        public void update(double time) {
            //alter delta based on distance from center
            double deltaX = positionX - CENTERX;
            double deltaY = positionY - CENTERY;
            //change velocity based on delta
            if (this.directionControl < 5) {
                velocityX = deltaY;
                velocityY = -deltaX;
            } else {
                velocityX = -deltaY;
                velocityY = deltaX;
            }
            //Change pos based on velocity
            positionX += velocityX * time;
            positionY += velocityY * time;
        }

        public void draw(Graphics g) {
            g.fillOval((int) positionX, (int) positionY, diameter, diameter);
        }
    }
}
