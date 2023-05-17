import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


//Sources
//http://www.edu4java.com/en/game/game6.html
//https://www.geeksforgeeks.org/java-do-while-loop-with-examples/
//https://stackoverflow.com/questions/18249592/how-to-change-font-size-in-drawstring-java

class Pair {
    public double x;
    public double y;

    public Pair(double initX, double initY) {
        x = initX;
        y = initY;
    }

    public Pair add(Pair toAdd) {
        return new Pair(x + toAdd.x, y + toAdd.y);
    }

    public Pair divide(double denom) {
        return new Pair(x / denom, y / denom);
    }

    public Pair times(double val) {
        return new Pair(x * val, y * val);
    }

    public void flipX() {
        x = -x;
    }

    public void flipY() {
        y = -y;
    }
}

class Paddle {
    Pair position;

    Pair velocity;
    Pair acceleration;

    int width;

    int height;

    double dampening;
    Color color;

    public Paddle(int x, int y) {
        //position of paddle
        position = new Pair(x, y);
        //default size of paddle
        width = 14;
        height = 200;
        //start with no velocity
        velocity = new Pair(0, 0);
        acceleration = new Pair(0, 0);
        //set dampening
        dampening = 1.3;
        //set color
        color = new Color(255, 255, 255);
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

    public double flipX() {
        acceleration.flipX();
        return 0.0;
    }

    public double flipY() {
        acceleration.flipY();
        return 0.0;
    }

    public void draw(Graphics g) {
        //ball
        g.setColor(color);
        g.fillRect((int) position.x, (int) position.y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) position.x, (int) position.y, this.width, this.height);
    }

    //override method
    public Rectangle getBounds(int width) {
        return new Rectangle((int) position.x, (int) position.y, width, this.height);
    }


    private void bounce(World w) {
        //set up and down bounds for paddles
        if (position.y <= 0) {
            velocity = new Pair(0,0);
        } else if (position.y > w.height - (this.height + 126)) {
            velocity = new Pair(0,0);
        }
    }

}

class Sphere {
    Pair position;
    Pair velocity;
    Pair acceleration;
    double radius;
    double dampening;
    Color color;

    public Sphere() {
        //spawn sphere in middle
        position = new Pair(512, 384);
        //start with velocity j going right
        velocity = new Pair(250, 0);
        acceleration = new Pair(0, 0);
        //set rad
        radius = 25;
        //set bounce reduction
        dampening = 1;
        //set color
        color = new Color(255, 255, 255);
    }

    public Rectangle circleBounds() {
        return new Rectangle((int) position.x, (int) position.y, (int) radius, (int) radius);
    }

    public Rectangle getBounds(int x) {
        return new Rectangle((int) (position.x + x), (int) position.y, (int) radius * 2, (int) radius * 2);
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

    public double flipX() {
        acceleration.flipX();
        return 0.0;
    }

    public double flipY() {
        acceleration.flipY();
        return 0.0;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();

        g.setColor(color);
        //ball
        g.drawOval((int) (position.x - radius), (int) (position.y - radius), (int) (2 * radius), (int) (2 * radius));
        g.setColor(c);
    }

    private void bounce(World w) {
        boolean bounced = false;
        //wall bounds
        if (position.x - radius < 0) {
            velocity.flipX();
            position.x = radius;
            bounced = true;
            //score
            w.rightScore += 1;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            w.reset(0);
        } else if (position.x + radius > w.width) {
            velocity.flipX();
            position.x = w.width - radius;
            bounced = true;
            //score
            w.leftScore += 1;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            w.reset(1);
        }
        if (position.y - radius < 0) {
            velocity.flipY();
            bounced = true;
        } else if (position.y + radius > Pong.HEIGHT - 125) {
            velocity.flipY();
            bounced = true;
        }
        if (bounced) {
            velocity = velocity.divide(dampening);
        }
        //create paddle collisions
        if (w.ball.circleBounds().intersects(w.leftPaddle.getBounds(40))) {
            //bounce off paddle
            w.ball.velocity.flipY();
            w.ball.velocity.flipX();
            //increase ball velocity
            if (w.leftPaddle.velocity.y < 0) {
                w.ball.velocity.y += w.leftPaddle.velocity.y * .85;
            } else if (w.leftPaddle.velocity.y > 0) {
                w.ball.velocity.y -= w.leftPaddle.velocity.y * .85;
            }
        }
        if (w.ball.getBounds(-20).intersects(w.rightPaddle.getBounds())) {
            //bounce off paddle
            w.ball.velocity.flipY();
            w.ball.velocity.flipX();
            //increase ball velocity
            if (w.rightPaddle.velocity.y < 0) {
                w.ball.velocity.y += w.rightPaddle.velocity.y * .85;
            } else if (w.rightPaddle.velocity.y > 0) {
                w.ball.velocity.y -= w.rightPaddle.velocity.y * .85;
            }
        }
    }
}

class World {
    int height;
    int width;

    //score
    int rightScore = 0;
    int leftScore = 0;
    //create ball
    Sphere ball = new Sphere();
    //create paddles
    Paddle leftPaddle = new Paddle(89, 250);

    Paddle rightPaddle = new Paddle(935, 250);


    public World(int initWidth, int initHeight) {
        width = initWidth;
        height = initHeight;
    }

    public void drawBall(Graphics g) {
        ball.draw(g);
    }

    public void updateBall(double time) {
        ball.update(this, time);
    }

    public void drawPaddles(Graphics g) {
        //set font
        g.setFont(new Font("TimesRoman", Font.BOLD, 80));
        //left paddle
        leftPaddle.draw(g);
        g.drawString(String.valueOf(leftScore), 256, 150);
        //right paddle
        rightPaddle.draw(g);
        g.drawString(String.valueOf(rightScore), 768, 150);
    }

    public void updatePaddles(double time) {
        leftPaddle.update(this, time);
        rightPaddle.update(this, time);
    }

    public void reset(int direction) {
        if (direction == 1) {
            //reset locations
            ball = new Sphere();
            leftPaddle = new Paddle(89, 250);
            rightPaddle = new Paddle(935, 250);
        } else {
            ball = new Sphere();
            ball.setVelocity(new Pair(-300, 0));
            leftPaddle = new Paddle(89, 250);
            rightPaddle = new Paddle(935, 250);
        }
    }
}

public class Pong extends JPanel implements KeyListener {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;
    World world;

    class Runner implements Runnable {
        public void run() {
            while (true) {
                world.updateBall(1.0 / (double) FPS);
                world.updatePaddles(1.0 / (double) FPS);
                repaint();
                try {
                    Thread.sleep(1000 / FPS);
                } catch (InterruptedException e) {
                }
            }

        }

    }


    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
    }

    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();
    }


    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        switch (c) {
            //Set ball color
            case '1' -> world.ball.color = new Color(255, 0, 0);
            case '2' -> world.ball.color = new Color(0, 255, 0);
            case '3' -> world.ball.color = new Color(0, 0, 255);
            case '4' -> world.ball.color = new Color(((int) (Math.random() * 255)), ((int) (Math.random() * 255)), ((int) (Math.random() * 255)));
            //LEFT PADDLE BINDS
            case 'r' ->
                //left paddle up
                    world.leftPaddle.velocity.y = -250;
            case 'v' ->
                //left paddle downward
                    world.leftPaddle.velocity.y = 250;
            case 'f' ->
                //left paddle stop
                    world.leftPaddle.setVelocity(new Pair(0, 0));
            //RIGHT PADDLE BINDS
            case 'u' ->
                //right paddle up
                    world.rightPaddle.velocity.y = -250;
            case 'n' ->
                //right paddle downward
                    world.rightPaddle.velocity.y = 250;
            case 'j' ->
                //right paddle stop
                    world.rightPaddle.setVelocity(new Pair(0, 0));
        }
        System.out.println("You pressed down: " + c);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public Pong() {
        world = new World(WIDTH, HEIGHT);
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Physics!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Pong mainInstance = new Pong();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //set background color
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //court line
        g.setColor(Color.WHITE);
        g.fillRect(512, 0, 2, 768);
        //draw paddles and ball and other stuff
        world.drawBall(g);
        world.drawPaddles(g);
    }
}
