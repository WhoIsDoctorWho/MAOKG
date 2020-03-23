import skeletons.Skeleton;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tymchenko_KP73_Lab2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(new Skeleton(frame.getSize(), frame.getInsets()));
        frame.setVisible(true);
    }

}