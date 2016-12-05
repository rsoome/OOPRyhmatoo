package graafiline;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class GraafilineEsitus extends Application {

    private Stage stage;

    @Override
    public void start(Stage peaLava){
        stage = peaLava;
        String name = "main.fxml";
        Parent scene = loadFxml(name);

        initPrimaryStage(peaLava, scene);
        boolean[][] testSisu = new boolean[22][10];
        for(int i = 0; i < 22; i++) {
            testSisu[i][i % 10] = true;
        }
        renderGame(testSisu);
    }

    private void initPrimaryStage(Stage peaLava, Parent scene) {
        peaLava.setTitle("Ultimate Tetris (not really ultimate(yet))");
        peaLava.setScene(new Scene(scene));
        peaLava.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        peaLava.show();
    }

    public void renderGame(boolean[][] gameState) {
        Canvas field = (Canvas) stage.getScene().lookup("#field");
        int blockSizeX = (int) field.getWidth() / gameState[0].length;
        int blockSizeY = (int) field.getHeight() / gameState.length;
        GraphicsContext graphicsContext = field.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, field.getWidth(), field.getHeight());
        graphicsContext.setFill(Color.RED);
        for(int y = 0; y < gameState.length; y++) {
            for(int x = 0; x < gameState[0].length; x++) {
                if (gameState[y][x]) {
                    graphicsContext.fillRect(x * blockSizeX, y * blockSizeY, blockSizeX, blockSizeY);
                }
            }
        }
    }

    private Parent loadFxml(String name) {
        try {
            URL resource = getClass().getClassLoader().getResource(name);
            if (resource == null) {
                throw new IllegalArgumentException("Unable to locate main scene file");
            }
            return (Parent) FXMLLoader.load(resource);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to load main scene file", e);
        }
    }
}
