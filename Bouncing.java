
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

//Sources
//https://www.google.com/search?q=what+is+velocity&oq=what+is+velocity&aqs=chrome..69i57j0i512l9.2058j0j7&sourceid=chrome&ie=UTF-8
public class Bouncing extends JPanel {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;
    public static final int RADIUS = 50;
    public static final int RADIUS2 = 90;
    double positionX;
    double positionY;
    double positionX2;
    double positionY2;
    double velocityX2;
    double velocityY2;

    //Note: The following are not used yet, you should use them in writing your code.
    double velocityX;
    double velocityY;

    double accelerationX;
    double accelerationY;

    class Runner implements Runnable {
        public Runner() {
            //Feel free to change these default values
            positionX = 275;
            positionY = HEIGHT - 220;
            velocityX = 500;
            velocityY = -500;
            accelerationY = 700;
            //your code here for adding the second sphere
            positionX2 = 450;
            positionY2 = HEIGHT - 600;
            velocityX2 = -100;
            velocityY2 = 100;
        }

        public void run() {
            while (true) {
                //your code here
                //Implement Movement  here
                //ball one
                positionX += velocityX / (double) FPS; //delete this line
                positionY += velocityY / (double) FPS; //delete this
                //ball two
                positionX2 += velocityX2 / (double) FPS; //delete this line
                positionY2 += velocityY2 / (double) FPS; //delete this
                //Implement bouncing here
                //X and Y bounds for ball one
                if (positionY > HEIGHT - RADIUS) {
                    velocityY *= -1;
                } else if (positionY <= 0) {
                    velocityY *= -1;
                }
                if (positionX > WIDTH - RADIUS) {
                    velocityX *= -1;
                } else if (positionX <= 0) {
                    velocityX *= -1;
                }
                //X and Y bounds for ball one
                if (positionY2 > HEIGHT - RADIUS2 + 10) {
                    velocityY2 *= -1;
                } else if (positionY2 < 40) {
                    velocityY2 *= -1;
                }
                if (positionX2 > WIDTH - RADIUS2 - 4) {
                    velocityX2 *= -1;
                } else if (positionX2 < 0) {
                    velocityX2 *= -1;
                }
                //Implement gravity here (Bonus)
                velocityY = velocityY + (accelerationY * (1.0 / FPS));
                velocityY2 = velocityY2 + (accelerationY * (1.0 / FPS));
                //don't mess too much with the rest of this method
                repaint();
                try {
                    Thread.sleep(1000 / FPS);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public Bouncing() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Physics!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Bouncing world = new Bouncing();
        frame.setContentPane(world);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //The cannon you see is actually *not* a photograph of a real cannon.
        //It's drawn by the following. 
        g.setColor(Color.ORANGE);
        int xpts[] = {75, 275, 275, 350, 325, 150};
        int ypts[] = {HEIGHT - 50, HEIGHT - 250, HEIGHT - 275, HEIGHT - 175, HEIGHT - 175, HEIGHT - 25};
        g.fillPolygon(xpts, ypts, 6);

        g.setColor(Color.BLUE);
        g.fillOval(150, HEIGHT - 200, 200, 200);

        //this is where the sphere is drawn. As a bonus make it draw something else
        // (e.g., your object from the previous homework).
        g.setColor(Color.WHITE);
        g.drawOval((int) positionX, (int) positionY, RADIUS, RADIUS);
        //your code here for drawing the second sphere
        //body
        g.setColor(new Color(201, 134, 149));
        g.fillOval((int) positionX2, (int) positionY2, 59, 78);
        //nose
        g.fillOval((int) positionX2 + 10, (int) positionY2 + 20, 83, 39);
        //eye
        g.setColor(Color.BLACK);
        g.fillOval((int) positionX2 + 40, (int) positionY2 + 20, 5, 5);
        //mouth
        g.fillRect((int) positionX2 + 40, (int) positionY2 + 65, 10, 3);
        //hat
        g.setColor(new Color(105, 66, 66));
        g.fillRoundRect((int) positionX2 + 5, (int) positionY2, 49, 5, 5, 5);
        g.fillOval((int) positionX2 + 9, (int) positionY2 - 41, 39, 44);
    }
}