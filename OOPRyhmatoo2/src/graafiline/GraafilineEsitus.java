package graafiline;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Mänguloogika;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class GraafilineEsitus extends Application {

    private Stage stage;
    private Mänguloogika mänguloogika;

    @Override
    public void start(Stage peaLava) {
        stage = peaLava;
        String name = "main.fxml";
        Parent scene = loadFxml(name);

        initPrimaryStage(peaLava, scene);
    }

    private void initPrimaryStage(Stage peaLava, Parent scene) {
        peaLava.setTitle("Ultimate Tetris (not really ultimate(yet))");
        peaLava.setScene(new Scene(scene));
        peaLava.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case A:
                    mänguloogika.liiguta(-1);
                    break;
                case S:
                    mänguloogika.liiguta(0);
                    break;
                case D:
                    mänguloogika.liiguta(1);
                    break;
            }
        });
        peaLava.show();
    }

    public void renderGame(int[][] gameState) {
        Platform.runLater(() -> renderGameInner(gameState));
    }

    //võtab ette inti maatriksi ja vaatab , mis kohad on täidetud ja kuvab canvase vastavalt sellele
    private void renderGameInner(int[][] gameState) {
        Canvas field = (Canvas) stage.getScene().lookup("#field"); //otsitakse element IDga field
        int blockSizeX = (int) field.getWidth() / gameState[0].length;
        int blockSizeY = (int) field.getHeight() / gameState.length;
        GraphicsContext graphicsContext = field.getGraphicsContext2D(); //joonistada saab stuffi
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, field.getWidth(), field.getHeight());
        graphicsContext.setFill(Color.RED);

        //käiakse elemendid läbi.. kui on midagi, siis värvitakse kast punaseks
        for(int y = 0; y < gameState.length; y++) {
            for(int x = 0; x < gameState[0].length; x++) {
                if (gameState[y][x] > 0) {
                    graphicsContext.fillRect(x * blockSizeX, y * blockSizeY, blockSizeX, blockSizeY);
                }
            }
        }
    }

    //laeb Fxml failist kasutajaliidese kirjelduse
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
