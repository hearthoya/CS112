
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;

class Pair {
    double x;
    double y;

    public Pair(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Pair add(Pair a) {
        return new Pair(x + a.x, y + a.y);
    }

    public Pair times(double time) {
        return new Pair(x * time, y * time);
    }

    public void flipX() {
        x *= -1;
    }

    public void flipY() {
        y *= -1;
    }

    public Pair divide(double a) {
        x = this.x / a;
        y = this.y / a;
        return new Pair(x, y);
    }

}

abstract class Shape {
    Pair position;
    Pair velocity;
    Pair acceleration;
    double width;
    double halfwidth;
    double dampening;
    Color color;

    public Shape() {
        Random rand = new Random();
        position = new Pair(1024 / 2, 100);
        velocity = new Pair((double) (rand.nextInt(1000) - 500), (double) (rand.nextInt(1000) - 500));
        acceleration = new Pair(0.0, 200.0);
        width = 10 + Math.random() * 20;
        halfwidth = width / 2.0;
        dampening = 1.1;
        color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    public void update(World w, double time) {
        position = position.add(velocity.times(time));
        velocity = velocity.add(acceleration.times(time));
        bounce(w);
    }

    public void setPosition(Pair p) {
        position = p;
    }

    public void setVelocity(Pair v) {
        velocity = v;
    }

    public void setAcceleration(Pair a) {
        acceleration = a;
    }

    abstract public void draw(Graphics g);

    private void bounce(World w) {
        Boolean bounced = false;
        if (position.x - halfwidth < 0) {
            velocity.flipX();
            velocity.x *= 1.5;
            position.x = width;
            bounced = true;
        } else if (position.x + halfwidth > w.width) {
            velocity.flipX();
            position.x = w.width - halfwidth;
            velocity.y *= 1.5;
            bounced = true;
        }
        if (position.y - halfwidth < 0) {
            velocity.flipY();
            position.y = halfwidth;
            bounced = true;
        } else if (position.y + halfwidth > w.height) {
            velocity.flipY();
            position.y = w.height - halfwidth;
            bounced = true;
        }
        if (bounced) {
            velocity = velocity.divide(dampening);
        }
    }
}

class Sphere extends Shape {
    public void draw(Graphics g) {
        Color c = g.getColor();

        g.setColor(color);
        g.drawOval((int) (position.x), (int) (position.y), (int) (width), (int) (width));
        g.setColor(c);
    }
}

class Square extends Shape {
    public void draw(Graphics g) {
        Color c = g.getColor();

        g.setColor(color);
        g.drawRect((int) (position.x - width / 2), (int) (position.y - width / 2), (int) (width), (int) (width));
        g.setColor(c);
    }
}

class World {
    int height;
    int width;
    int numShapes;
    Shape shapes[];

    public World(int initWidth, int initHeight, int initNumShapes) {
        width = initWidth;
        height = initHeight;

        numShapes = initNumShapes;
        shapes = new Shape[numShapes];

        for (int i = 0; i < numShapes; i++) {
            if (i % 2 == 0)
                shapes[i] = new Sphere();
            else
                shapes[i] = new Square();
        }
    }


    public void drawShapes(Graphics g) {
        for (int i = 0; i < numShapes; i++) {
            shapes[i].draw(g);
        }
    }

    public void updateShapes(double time) {
        for (int i = 0; i < numShapes; i++) {
            shapes[i].update(this, time);
        }
    }

}

public class OOBouncing extends JPanel {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;
    World world;

    public void run() {
        while (true) {
            world.updateShapes(1.0 / (double) FPS);
            repaint();
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {
            }
        }

    }

    public OOBouncing() {
        world = new World(WIDTH, HEIGHT, 300);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Physics!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        OOBouncing mainInstance = new OOBouncing();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
        mainInstance.run();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        world.drawShapes(g);
    }
}
