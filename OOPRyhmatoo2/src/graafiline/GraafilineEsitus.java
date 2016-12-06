package graafiline;

import highscore.Highscore;
import highscore.HighscoreRepository;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Mänguloogika;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class GraafilineEsitus extends Application {

    private Stage stage;
    private Mänguloogika mänguloogika;
    private Thread mänguLoogikaLõim;
    private String playerName;
    private final HighscoreRepository highscoreRepository = new HighscoreRepository();

    @Override
    public void start(Stage peaLava) {
        stage = peaLava;
        String name = "main.fxml";
        Parent scene = loadFxml(name);

        initPrimaryStage(peaLava, scene);
        initStartButton();
    }

    private void initPrimaryStage(Stage peaLava, Parent scene) {
        peaLava.setTitle("Ultimate Tetris (not really ultimate(yet))");
        peaLava.setScene(new Scene(scene));
        peaLava.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (mänguloogika == null) {
                return;
            }
            switch (event.getCode()) {
                case W:
                    mänguloogika.getPraeguneKlots().muudaAsendit(1);
                    break;
                case A:
                    mänguloogika.liiguta(Mänguloogika.VASAKULE);
                    break;
                case S:
                    mänguloogika.liiguta(Mänguloogika.ALLA);
                    break;
                case D:
                    mänguloogika.liiguta(Mänguloogika.PAREMALE);
                    break;
            }
        });
        renderHighscores(highscoreRepository.findAll());
        peaLava.show();
    }

    private void initStartButton() {
        Button startButton = (Button) stage.getScene().lookup("#startGame");
        startButton.setOnAction(event -> {
            if (mänguloogika != null) {
                return;
            }
            TextField nameField = (TextField) stage.getScene().lookup("#name");
            playerName = nameField.getText();
            mänguloogika = new Mänguloogika(this);
            mänguLoogikaLõim = new Thread(mänguloogika);
            mänguLoogikaLõim.start();
        });
    }

    public void renderHighscores(Collection<Highscore> highscores) {
        List<HighscoreRow> highscoreRows = new ArrayList<>();
        int place = 1;
        for (Highscore highscore : highscores) {
            highscoreRows.add(new HighscoreRow(place, highscore.getName(), highscore.getScore()));
            place++;
        }
        Platform.runLater(() -> renderHighscoresInner(highscoreRows));
    }

    @SuppressWarnings("unchecked")
    private void renderHighscoresInner(List<HighscoreRow> highscoreRows) {
        TableView<HighscoreRow> highscoreTable = (TableView) stage.getScene().lookup("#highscores");
        highscoreTable.setItems(FXCollections.observableList(highscoreRows));
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

    private void updateScore(int newScore) {
        Platform.runLater(() -> updateScoreInternal(newScore));
    }

    private void updateScoreInternal(int newScore) {
        Text scoreText = (Text) stage.getScene().lookup("#score");
        scoreText.setText("Skoor: " + newScore);
    }

    public void endGame(int score) {
        highscoreRepository.add(new Highscore(playerName, score));
        renderHighscores(highscoreRepository.findAll());
        mänguLoogikaLõim.interrupt();
        mänguLoogikaLõim = null;
        mänguloogika = null;
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
