package tanks.client.tanksclient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class HelloController {
    private int speed = 5;
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView tank;
    @FXML
    private void handleOnKeyPressed(KeyEvent event) {
        System.out.println(event);
        switch (event.getCode()) {
            case LEFT:
                System.out.println("Left");
                tank.setX(tank.getX() - speed);
                break;
            case RIGHT:
                System.out.println("Right");
                tank.setX(tank.getX() + speed);
                break;
            default:
                System.out.println(event);
        }
    }
}