package fractal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fractal extends JFrame implements ActionListener {
    MyPanel drawingArea = null;
    public final int size = 730;

    private static class Point {
        double x, y;

        Point(double _x, double _y) {
            x = _x;
            y = _y;
        }

        Point(int _x, int _y) {
            x = _x;
            y = _y;
        }

        Point(Point p) {
            x = p.x;
            y = p.y;
        }
    }

    private class MyPanel extends JPanel {

        int recursion = 0;  // How many levels of recursion to use
        final int SNOWFLAKE = 1;
        int whichFractal = SNOWFLAKE;

        public void drawSnowFlake(Graphics g, Point p1, Point p2, int n) {

            if (n == 0) {
                g.setColor(Color.red);
                g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
                return;
            }


            Point[] pts = new Point[5];
            // Same as p1
            pts[0] = new Point(p1);
            // 1/3 the way from p1 to p2
            pts[1] = new Point(p1.x + (p2.x - p1.x) / 3.0, p1.y + (p2.y - p1.y) / 3.0);


            Point vec = new Point(p2.x - p1.x, p2.y - p1.y);
            Point midPoint = new Point((p2.x + p1.x) / 2.0, (p2.y + p1.y) / 2.0);

            double x = vec.y * 1;
            double y = -(vec.x * 1);

            double rs = Math.sqrt((x * x) + (y * y));
            x /= rs;
            y /= rs;

            double newLength = Math.sqrt((Math.pow(p2.x - p1.x, 2)) + Math.pow(p2.y - p1.y, 2)) / 3.0;

            pts[2] = new Point(x * newLength + midPoint.x, y * newLength + midPoint.y);
            // 1/3 the way from p2 to p1
            pts[3] = new Point(p1.x + (p2.x - p1.x) * 2.0 / 3.0, p1.y + (p2.y - p1.y) * 2.0 / 3.0);
            // Same as p2
            pts[4] = new Point(p2);

            drawSnowFlake(g, pts[0], pts[1], n - 1);
            drawSnowFlake(g, pts[1], pts[2], n - 1);
            drawSnowFlake(g, pts[2], pts[3], n - 1);
            drawSnowFlake(g, pts[3], pts[4], n - 1);
        }

        public void paintComponent(Graphics g) {
            //snowflake endpoints
            Point p1 = new Point(10, size / 2);
            Point p2 = new Point(size - 10, size / 2);

            if (whichFractal == SNOWFLAKE) {
                drawSnowFlake(g, p1, p2, recursion);
            }

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Levels of recursion: " + recursion, size / 2 - 100, 60);
        }
    }

    public Fractal() {

        drawingArea = new MyPanel();
        drawingArea.setVisible(true);
        setPreferredSize(new Dimension(size, (size / 2) + 95));

        setContentPane(drawingArea);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("Koch Snowflake");

        JButton button1 = new JButton("Less");
        button1.setActionCommand("less");
        button1.addActionListener(this);
        JButton button2 = new JButton("More");
        button2.setActionCommand("more");
        button2.addActionListener(this);

        JButton button3 = new JButton("Koch Snowflake");
        button3.setActionCommand("snowflake");
        button3.addActionListener(this);

        drawingArea.add(button1);
        drawingArea.add(button2);
        drawingArea.add(button3);

        setVisible(true);

        pack();
    }

    public void actionPerformed(ActionEvent e) {

        if ("less".equals(e.getActionCommand())) {
            drawingArea.recursion--;
            if (drawingArea.recursion < 0) drawingArea.recursion = 0;
        }

        else if ("more".equals(e.getActionCommand())) {
            drawingArea.recursion++;
        }

        else if ("snowflake".equals((e.getActionCommand()))) {
            drawingArea.whichFractal = drawingArea.SNOWFLAKE;
        }

        repaint();
    }
}
