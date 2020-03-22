package HelloWorld;
 
import javafx.scene.paint.Color;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.shape.*;

public class MyJavaFXApplication extends Application {
    private Group root;
    private final int clockCenterX = 150;
    private final int clockCenterY = 150;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        createScene(primaryStage);

        drawBaseCircles();
        drawInnerCircles();
        drawHourHand();
        drawMinuteHand();

        primaryStage.show();
    }
    public void createScene(Stage primaryStage) {
        Scene scene = new Scene(root, 300, 250);
        scene.setFill(Color.DARKOLIVEGREEN);
        primaryStage.setScene(scene);
    }
    public void drawCircle(int x, int y, int radius, Color color) {
        Circle circle = new Circle(x,y,radius);
        circle.setFill(color);
        root.getChildren().add(circle);
    }
    public void drawBaseCircles() {
        drawCircle(clockCenterX,clockCenterY,70, Color.GOLD);
        drawCircle(clockCenterX,clockCenterY,63, Color.WHITESMOKE);
    }
    public void drawInnerCircles() {
        final int radius = 7;
        drawCircle(clockCenterX,clockCenterY+50, radius, Color.GOLD);
        drawCircle(clockCenterX+50, clockCenterY, radius, Color.GOLD);
        drawCircle(clockCenterX,clockCenterY-50, radius, Color.GOLD);
        drawCircle(clockCenterX-50,clockCenterY, radius, Color.GOLD);
    }
    public void drawHourHand() {
        Polygon outerPolygon = new Polygon(
                clockCenterX-7, clockCenterY,
                clockCenterX, clockCenterY - 8,
                clockCenterX+38, clockCenterY,
                clockCenterX-7, clockCenterY,
                clockCenterX, clockCenterY + 8,
                clockCenterX+38, clockCenterY);
        Polygon innerPolygon = new Polygon(
                clockCenterX-4, clockCenterY,
                clockCenterX, clockCenterY - 5,
                clockCenterX+35, clockCenterY,
                clockCenterX-4, clockCenterY,
                clockCenterX, clockCenterY + 5,
                clockCenterX+35, clockCenterY);
        innerPolygon.setFill(Color.WHITESMOKE);
        root.getChildren().addAll(outerPolygon, innerPolygon);
    }
    public void drawMinuteHand() {
        Polygon polygon = new Polygon(
                clockCenterX, clockCenterY+8,
                clockCenterX-3.5, clockCenterY+4,
                clockCenterX, clockCenterY-58,
                clockCenterX, clockCenterY+8,
                clockCenterX+3.5, clockCenterY+4,
                clockCenterX, clockCenterY-58);

        root.getChildren().add(polygon);
    }
}
