package pw.proz;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class GameWindow extends Application {

    private static final int WINDOW_WIDTH = 900, WINDOW_HEIGHT = 600;
    public static int getWINDOW_WIDTH() { return WINDOW_WIDTH; }
    public static int getWINDOW_HEIGHT() { return WINDOW_HEIGHT; }
    private static boolean gameRunning, gameOver, keyPressed;
    private long lastSpaceClicked, lastAnimation, lastBonus;
    private long startNanoTime;
    private double elapsedTime;
    private AnimationTimer timer;
    private GraphicsContext graphicsContext;
    private Bird flappyBird;
    private final Pipe[] pipes = new Pipe[2];
    private int pipeIndex;
    private Bonus fruit;
    private Text score;
    private int currentScore;
    private int scoreMultiplier;
    private double speedMultiplier;
    public final static int scoreTableSize = 3;
    public final static int[] scoreTable = new int[scoreTableSize];

    @Override
    public void start(Stage main_stage) throws Exception {
        main_stage.setTitle("Flappy Bird.exe");
        main_stage.setResizable(false);

        Parent root = createGameplayScene();
        Scene main_scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        lastAnimation = System.currentTimeMillis();
        lastBonus = System.currentTimeMillis();
        main_scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                if (!gameOver) {
                    keyPressed = true;
                    if (!gameRunning) {
                        gameRunning = true;
                    } else {
                        lastSpaceClicked = System.currentTimeMillis();
                        flappyBird.getSprite().setVelocity(flappyBird.getSprite().getVelocityX(),  -300 * speedMultiplier);
                    }
                } else {
                    startNewGame();
                }
            }
            else if (event.getCode() == KeyCode.ESCAPE) {
                timer.stop();
                reset();
                App.setGameplay(false);
                main_stage.setScene(App.getMenu());
                main_stage.setTitle("Flappy Bird Main Menu");
                main_stage.show();
            }
        });
        main_stage.setScene(main_scene);
        main_stage.setAlwaysOnTop(true);
        main_stage.show();

        startGame();
    }

    private Parent createGameplayScene() {
        Group root = new Group();

        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);

        graphicsContext = canvas.getGraphicsContext2D();

        ImageView background = new ImageView(new Image(getClass().getResource("/images/background.png").toExternalForm()));
        background.setFitHeight(WINDOW_HEIGHT);
        background.setFitWidth(WINDOW_WIDTH);

        setBird();
        pipeIndex = 0;
        addPipe();

        Text controls = new Text("SPACE BAR -> Fly/Restart \t ESCAPE -> Exit to menu");
        controls.setFont(Font.font("Georgia, serif", FontWeight.EXTRA_BOLD, 30));
        controls.setFill(Color.WHITE);
        controls.setStroke(Color.BLACK);
        controls.setLayoutX(70);
        controls.setLayoutY(GameWindow.getWINDOW_HEIGHT() - 20);

        currentScore = 0;
        scoreMultiplier = 1;
        speedMultiplier = 1;
        score = new Text(String.valueOf(currentScore));
        score.setFont(Font.font("Georgia, serif", FontWeight.EXTRA_BOLD, 30));
        score.setFill(Color.WHITE);
        score.setStroke(Color.BLACK);
        score.setLayoutX(GameWindow.getWINDOW_WIDTH()/2.0 - 20);
        score.setLayoutY(50);

        root.getChildren().addAll(background, canvas, controls, score);
        return root;
    }

    private void setBird() {
        flappyBird = new Bird();
        flappyBird.getSprite().render((graphicsContext));
    }

    private void addPipe() {
        pipes[pipeIndex] = new Pipe();
        pipes[pipeIndex].setVelocity(-200 * speedMultiplier, 0);
        pipeIndex++;
        if (pipeIndex > 1) pipeIndex = 0;
    }

    private void movePipes() {
        for (Pipe pipe : pipes) {
            if (pipe != null) {
                pipe.setVelocity(-200 * speedMultiplier, 0);
                pipe.getSprite(0).updatePosition(elapsedTime);
                pipe.getSprite(0).render((graphicsContext));
                pipe.getSprite(1).updatePosition(elapsedTime);
                pipe.getSprite(1).render((graphicsContext));

                if (pipe.getSprite(0).getPositionX() <= flappyBird.getSprite().getPositionX() && !(pipe.isHalfPassed())) {
                    pipe.setHalfPassed(true);
                    currentScore += scoreMultiplier;
                    score.setText(String.valueOf(currentScore));
                    addPipe();
                }
            }
        }
    }

    private boolean collideWithPipe() {
        boolean collideLower = false;
        boolean collideUpper = false;
        for (Pipe pipe : pipes) {
            if (pipe != null) {
                collideLower = pipe.getSprite(0).intersects(flappyBird.getSprite());
                collideUpper = pipe.getSprite(1).intersects(flappyBird.getSprite());
            }
            if (collideLower ||  collideUpper) return true;
        }
        return false;
    }

    private void addBonus(int index) {
        fruit = new Bonus(index);
        fruit.getSprite().setVelocity(-200 * speedMultiplier, 0);
    }

    private void removeBonus() {
        fruit = null;
        lastBonus = System.currentTimeMillis();
    }

    private void moveBonus() {
        if (fruit != null) {
            fruit.getSprite().setVelocity(-200 * speedMultiplier, 0);
            fruit.getSprite().updatePosition(elapsedTime);
            fruit.getSprite().render((graphicsContext));

            if (fruit.getSprite().getPositionX() < - 30) {
                removeBonus();
            }
        }
    }

    private boolean collideWithBonus() {
        if( fruit != null) {
            return flappyBird.getSprite().intersects(fruit.getSprite());
        }
        return false;
    }

    private void startGame() {
        startNanoTime = System.nanoTime();
        timer = new AnimationTimer() {
            public void handle(long now) {
                elapsedTime = (now - startNanoTime) / 1000000000.0;
                startNanoTime = now;

                graphicsContext.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
                checkTimeBetweenSpaceHits();
                if (gameRunning) {
                    movePipes();
                    moveBonus();
                    checkTimeBetweenBonuses();
                    if (flappyBird.outOfBounds() || collideWithPipe()) {
                        timer.stop();
                        gameOver = true;
                        saveScore(currentScore);
                    }
                    if (collideWithBonus()) {
                        if(fruit.getBonusType() == 0) {
                            scoreMultiplier *= 2;
                        }
                        else {
                            speedMultiplier = 1.5;
                        }
                        removeBonus();
                    }
                }
            }
        };
        timer.start();
    }

    private void startNewGame() {
        reset();
        setBird();
        addPipe();
        startGame();
    }

    private void reset() {
        keyPressed = false;
        gameRunning = false;
        gameOver = false;
        currentScore = 0;
        scoreMultiplier = 1;
        speedMultiplier = 1;
        removeBonus();
        score.setText(String.valueOf(currentScore));
        pipes[0] = null;
        pipes[1] = null;
    }

    private void checkTimeBetweenSpaceHits() {
        long time_between_spaces = (System.currentTimeMillis() - lastSpaceClicked);

        if (time_between_spaces >= 400 && keyPressed) {
            keyPressed = false;
            flappyBird.getSprite().setVelocity(flappyBird.getSprite().getVelocityX(),  300 * speedMultiplier);
            flappyBird.getSprite().updatePosition(elapsedTime);
            flappyBird.getSprite().render(graphicsContext);
        } else {
            flappyBird.getSprite().updatePosition(elapsedTime);
            flappyBird.getSprite().render(graphicsContext);

            long time_between_animation = (System.currentTimeMillis() - lastAnimation);
            if (time_between_animation >= 200) {
                flappyBird.changeImage();
                lastAnimation = System.currentTimeMillis();
            }
        }
    }

    private void checkTimeBetweenBonuses() {
        long time_between_bonuses = (System.currentTimeMillis() - lastBonus);
        int bonusType;
        // bonus appears every 5 seconds
        if (time_between_bonuses >= 3000) {
            // bonus appears roughly halfway between pipes so player can grab it and manage to pass next pipe
            for(Pipe pipe : pipes) {
                if (pipe != null && fruit == null) {
                    if (pipe.getSprite(0).getPositionX() < - (GameWindow.getWINDOW_WIDTH() / 6.0 - 80)) {
                        bonusType = Math.round((float)Math.random()); // sets bonusType to 0 or 1
                        addBonus(bonusType);
                    }
                }
            }
        }
        if (time_between_bonuses >= 6000) {
            speedMultiplier = 1;
            scoreMultiplier = 1;
        }
    }

    public void saveScore(int score) {
        int tmp;
        for (int i = 0; i < scoreTableSize; i++) {
            if (score > scoreTable[i]) {
                tmp = scoreTable[i];
                scoreTable[i] = score;
                saveScore(tmp);
                break;
            }
        }
    }

    public static void main(String[] arguments) {
        launch(arguments);
    }
}