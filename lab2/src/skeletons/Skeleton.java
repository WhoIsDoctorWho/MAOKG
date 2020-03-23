package skeletons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Skeleton extends JPanel implements ActionListener {
    public int maxWidth;
    public int maxHeight;
    Timer timer;
    private Graphics2D g2d;
    private double angle = 0;

    private double scale = 1;
    private double delta = 0.01;

    public Skeleton(Dimension size, Insets insets) {
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;
        timer = new Timer(20, this);
        timer.start();

    }


    public void paint(Graphics g) {
        g2d = (Graphics2D) g;
        setQuality();
        drawBackground();
        drawBorder();
        g2d.scale(scale, scale);
        drawClock();
    }

    private void setQuality() {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
    }

    private void drawBackground() {
        g2d.setBackground(new Color(85, 107, 47));
        g2d.clearRect(0, 0, maxWidth, maxHeight);
    }

    private void drawBorder() {
        BasicStroke bs = new BasicStroke(10, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
        g2d.setStroke(bs);
        GradientPaint gp = new GradientPaint(5, 20, Color.darkGray, 15, 5, Color.cyan, true);
        g2d.setPaint(gp);
        g2d.drawRect(5, 5, maxWidth - 10, maxHeight - 40);
    }

    private void drawClock() {
        drawBaseCircles();
        drawInnerCircles();
        g2d.rotate(angle, 500, 500);
        drawHourHand();
        g2d.rotate(angle * 3, 500, 500);
        drawMinuteHand();
    }

    private void drawBaseCircles() {
        g2d.scale(0.5, 0.5);
        g2d.setColor(new Color(255, 215, 0));
        drawFilledCircle(500, 500, 400);
        g2d.setColor(new Color(245, 245, 245));
        drawFilledCircle(500, 500, 350);
    }

    private void drawInnerCircles() {
        GradientPaint gp = new GradientPaint(5, 25, Color.blue, 20, 2, Color.black, true);
        g2d.setPaint(gp);
        final int diameter = 30;
        drawFilledCircle(500, 370, diameter);
        drawFilledCircle(370, 500, diameter);
        drawFilledCircle(630, 500, diameter);
        drawFilledCircle(500, 630, diameter);
    }

    private void drawMinuteHand() {
        g2d.setColor(Color.BLACK);
        int[] xCoordinates = new int[]{500, 490, 500, 510};
        int[] yCoordinates = new int[]{520, 500, 350, 500};
        g2d.fillPolygon(new Polygon(xCoordinates, yCoordinates, 4));
    }

    private void drawHourHand() {
        int[] xCoordinates = new int[]{480, 500, 600, 500};
        int[] yCoordinates = new int[]{500, 480, 500, 520};
        g2d.setColor(Color.BLACK);
        g2d.fillPolygon(new Polygon(xCoordinates, yCoordinates, 4));

        xCoordinates = new int[]{485, 500, 590, 500};
        yCoordinates = new int[]{500, 490, 500, 510};
        g2d.setColor(new Color(245, 245, 245));
        g2d.fillPolygon(new Polygon(xCoordinates, yCoordinates, 4));
    }

    private void drawFilledCircle(int x, int y, int diameter) {
        final int radius = diameter / 2;
        g2d.fillOval(x - radius, y - radius, diameter, diameter);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //scaling
        if (scale < 0.99 || scale > 2)
            delta = -delta;
        scale += delta;

        // turning
        angle += 0.01;

        repaint();
    }
}

