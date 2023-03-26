package edu.school21.sockets.app;

import edu.school21.sockets.models.Tank;
import edu.school21.sockets.utils.Field;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class TanksApplication extends Application {
    public static Canvas canvas;

    public static Tank player;
    public static Tank enemy;
    public static GraphicsContext gc;
    public static AnimationTimer animationTimer;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Tanks!");
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        canvas = new Canvas(Field.WIDTH, Field.HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKRED);

        Image background = new Image("field.jpg", Field.WIDTH, Field.HEIGHT, false, false);
        stage.show();
        player = new Tank(new Image("BottomTank.png", Field.TANK_WIDTH, Field.TANK_HEIGHT, false, false),
                Field.BORDER_LEN, Field.HEIGHT - Field.BORDER_LEN - Field.TANK_HEIGHT);
        enemy = new Tank(new Image("TopTank.png", Field.TANK_WIDTH, Field.TANK_HEIGHT, false, false),
                Field.WIDTH - Field.BORDER_LEN - Field.TANK_WIDTH, Field.BORDER_LEN );

        scene.setOnKeyPressed(event -> {
            updteTank(player, event.getCode().toString());
            // send action info to server
        });

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    //if hp == 0 game over
                    gc.clearRect(0, 0, Field.WIDTH, Field.HEIGHT);
                    gc.drawImage(background, 0, 0);
                    player.draw(gc);
                    enemy.draw(gc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        animationTimer.start();

    }
    public void updteTank(Tank tank, String code) {
        switch (code) {
            case "LEFT":
                System.out.println("Left");
                tank.moveLeft();
                break;
            case "RIGHT":
                System.out.println("Right");
                tank.moveRight();
                break;
            case "SPACE":
                System.out.println("Shoot");
                // shoot
                break;
            default:
                System.out.println(code);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
