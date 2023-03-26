package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;
import edu.school21.sockets.client.TankControllerImpl;
import edu.school21.sockets.client.TankController;
import edu.school21.sockets.models.Bullet;
import edu.school21.sockets.models.Tank;
import edu.school21.sockets.utils.Field;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TanksApplication extends Application {
    public static Canvas canvas;
    public static Client client;

    public static Tank player;
    public static Tank enemy;
    public static GraphicsContext gc;
    public static AnimationTimer animationTimer;
    public static List<Bullet> bullets = new ArrayList<>();
    public static Field.GS gameState = Field.GS.CONNECTION_WAITING;
    public static int bulletsShot = 0;
    public static int bulletsHit = 0;
    public static int bulletsShotEnemy = 0;
    public static int bulletsHitEnemy = 0;

    public KeyCode lastkeyCode;

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
        Image gameover = new Image("gameOver.jpeg", Field.WIDTH/2, Field.HEIGHT/2, false, false);
        stage.show();

        try {
            client.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        scene.setOnKeyPressed(event -> {
            if (gameState == Field.GS.WAR) {
                if (Objects.nonNull(lastkeyCode) && lastkeyCode.toString().equals("SPACE") && event.getCode().toString().equals("SPACE")) return;
                lastkeyCode = event.getCode();
                updteTank(player, event.getCode().toString());
                sendToServer(event.getCode().toString());
            } else if (gameState == Field.GS.CONNECTION_WAITING) {
                sendToServer("readyToStart");
            }
        });

        scene.setOnKeyReleased(keyEvent -> lastkeyCode = null);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    checkWarState();
                    if (gameState == Field.GS.CONNECTION_WAITING) {
                        gc.strokeText("Waiting for opponent!", Field.WIDTH/4, Field.HEIGHT/4);
                    }
                    if (gameState == Field.GS.WAR) {
                        gc.clearRect(0, 0, Field.WIDTH, Field.HEIGHT);
                        gc.drawImage(background, 0, 0);
                        player.draw(gc);
                        enemy.draw(gc);
                        gc.strokeText("HP=" + enemy.getHealth(), 5, 15);
                        gc.strokeText("HP=" + player.getHealth(), 5, Field.WIDTH - 5);
                        if (!bullets.isEmpty()) {
                            for (int i = 0; i < bullets.size(); i++) {
                                Bullet b = bullets.get(i);
                                b.move();
                                if (b.getDirection() == Field.UP) {
                                    if (b.checkCollision(enemy)) {
                                        bulletsHit++;
                                    }
                                } else {
                                    b.checkCollision(player);
                                }
                                if (b.isOut()) {
                                    bullets.remove(b);
                                } else {
                                    b.draw(gc);
                                }
                            }
                        }
                    } else if (gameState == Field.GS.GAME_OVER) {
                        gc.strokeText("Y o u : ", 5, Field.HEIGHT/4);
                        gc.strokeText("S h o t s  f i r e d  =  " + bulletsShot, 5, Field.HEIGHT/4 + 15);
                        gc.strokeText("S h o t s  h i t  =  " + bulletsHit, 5, Field.HEIGHT/4 + 30);
                        gc.strokeText("S h o t s  m i s s e d  =  " + bulletsHitEnemy, 5, Field.HEIGHT/4 + 45);
                        gc.strokeText("E n e m y : ", Field.WIDTH/4*3 + 5, Field.HEIGHT/4);
                        gc.strokeText("S h o t s  f i r e d  =  " + bulletsShotEnemy, Field.WIDTH/4*3 + 5, Field.HEIGHT/4 + 15);
                        gc.strokeText("S h o t s  h i t  =  " + bulletsHitEnemy, Field.WIDTH/4*3 + 5, Field.HEIGHT/4 + 30);
                        gc.strokeText("S h o t s  m i s s e d  =  " + bulletsHitEnemy, Field.WIDTH/4*3 + 5, Field.HEIGHT/4 + 45);
                        gc.drawImage(gameover, Field.WIDTH/4, Field.HEIGHT/4);
                    }

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
                tank.moveLeft();
                break;
            case "RIGHT":
                tank.moveRight();
                break;
            case "SPACE":
                player.shoot(Field.UP);
                break;
            default:
                System.out.println(code);
        }
    }

    public void sendToServer(String code) {
        switch (code) {
            case "LEFT":
                client.sendMessage("left");
                break;
            case "RIGHT":
                client.sendMessage("right");
                break;
            case "SPACE":
                bulletsShot++;
                client.sendMessage("shoot " + bulletsShot + " " + bulletsHit);
                break;
            case "readyToStart":
                client.sendMessage("ready");
            default:
                System.out.println(code);
        }
    }
    public void checkWarState() {
        if (gameState != Field.GS.CONNECTION_WAITING) {
            if (gameState != Field.GS.GAME_OVER &&
                    (player.getHealth() <= 0 || enemy.getHealth() <= 0)) {
                gameState = Field.GS.GAME_OVER;
                System.out.println("player hp = " + player.getHealth());
                System.out.println("enemy hp = " + enemy.getHealth());
            }
        }
//        else {
//            while (gameState == Field.GS.CONNECTION_WAITING) {
//                sendToServer("readyToStart");
//                //get info from server
//            }
//        }
    }

    public static void main(String[] args) {
        if (args.length != 2 || !args[0].startsWith("--server-port=") || !args[1].startsWith("--server-ip=")){
            System.err.println("Enter port and ip using --server-port=? --server-ip=?");
            System.exit(1);
        }
        player = new Tank(new Image("BottomTank.png", Field.TANK_WIDTH, Field.TANK_HEIGHT, false, false),
                Field.BORDER_LEN, Field.HEIGHT - Field.BORDER_LEN - Field.TANK_HEIGHT, bullets);
        enemy = new Tank(new Image("TopTank.png", Field.TANK_WIDTH, Field.TANK_HEIGHT, false, false),
                Field.WIDTH - Field.BORDER_LEN - Field.TANK_WIDTH, Field.BORDER_LEN, bullets);
        TankController tankController = new TankControllerImpl(enemy, () -> gameState = Field.GS.WAR);
        try {
            int port = Integer.parseInt(args[0].substring("--server-port=".length()));
            String ip = args[1].substring("--server-ip=".length());
            client = new Client(ip, port, tankController);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        launch();
    }
}
