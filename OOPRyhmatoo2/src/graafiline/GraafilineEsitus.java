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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class GraafilineEsitus extends Application { //javaFx klass, kus saab peale ehitada

    private Stage stage; //aken
    private Mänguloogika mänguloogika; //senikaua väärtust pole, kuni ei vajutata starti, teavitab graafilist liidest
    private Thread mänguLoogikaLõim;
    private String playerName;
    private final HighscoreRepository highscoreRepository = new HighscoreRepository();

    @Override
    public void start(Stage peaLava) { //kui rakendus tööle pannakse
        stage = peaLava;
        String name = "./resources/main.fxml";
        Parent scene = loadFxml(name); //loeb kasutajaliidese kirjelduse failist

        initPrimaryStage(peaLava, scene); //nuppude handlimine
        initGameOver(scene); //Game over kiri ja nupp
        initStartButton(); //Start nupp
        peaLava.show(); //kuvatakse pealava
    }

    private void initGameOver(Parent scene) {
        Button gameOverDismissButton = (Button) scene.getScene().lookup("#gameOverDismiss");
        gameOverDismissButton.setOnAction(event -> hideGameOver());
        hideGameOver();
    }

    private void initPrimaryStage(Stage peaLava, Parent scene) {
        peaLava.setTitle("Ultimate Tetris");
        peaLava.setScene(new Scene(scene)); //annab aknale sisu
        peaLava.setOnCloseRequest(t -> { //siis, kui ristit kinni paned
            Platform.exit(); //javaFx jaoks
            System.exit(0); //java virtuaalmasina jaoks
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (mänguloogika == null) {
                return;
            }
            boolean consumeEvent = true;
            switch (event.getCode()) { //kui vajutad teatud nuppu, siis asend muutub
                case W:
                case UP:
                    mänguloogika.muudaKlotsiAsendit(1);
                    break;
                case A:
                case LEFT:
                    mänguloogika.liiguta(Mänguloogika.VASAKULE);
                    break;
                case S:
                case DOWN:
                    mänguloogika.liiguta(Mänguloogika.ALLA);
                    break;
                case D:
                case RIGHT:
                    mänguloogika.liiguta(Mänguloogika.PAREMALE);
                    break;
                default:
                    consumeEvent = false;
                    break;
            }
            if (consumeEvent) {  //ei kutsuta vaikimisi eventHandlerit välja
                event.consume();
            }
        });
        renderHighscores(highscoreRepository.findAll());  //teeb tabeli sisu
    }

    private void initStartButton() { //paneb startbuttonile loogika külge
        Button startButton = (Button) stage.getScene().lookup("#startGame");
        startButton.setOnAction(event -> startGame());
    }

    private void startGame() {
        stage.getScene().lookup("#field").requestFocus();
        if (mänguloogika != null) { //mäng juba käib
            return;
        }
        TextField nameField = (TextField) stage.getScene().lookup("#name"); //
        playerName = nameField.getText(); //ignoreeritakse mängu ajal muudetud nime
        mänguloogika = new Mänguloogika(this);
        mänguLoogikaLõim = new Thread(mänguloogika);
        mänguLoogikaLõim.start(); //taustal pannakse käima
    }

    public void renderHighscores(Collection<Highscore> highscores) {
        List<HighscoreRow> highscoreRows = new ArrayList<>();
        int place = 1;
        for (Highscore highscore : highscores) {
            highscoreRows.add(new HighscoreRow(place, highscore.getName(), highscore.getScore()));
            place++;
        }
        Platform.runLater(() -> renderHighscoresInner(highscoreRows)); //runLater, et exceptionit ei tuleks
    }

    @SuppressWarnings("unchecked")
    private void renderHighscoresInner(List<HighscoreRow> highscoreRows) {
        TableView<HighscoreRow> highscoreTable = (TableView<HighscoreRow>) stage.getScene().lookup("#highscores");
        highscoreTable.setItems(FXCollections.observableList(highscoreRows));
    }

    public void renderGame(int[][] gameState) {
        int[][] gameStateCopy = new int[gameState.length][];
        for (int i = 0; i < gameState.length; i++) {
            gameStateCopy[i] = Arrays.copyOf(gameState[i], gameState[i].length); //tehakse igast reast koopia
        }
        Platform.runLater(() -> renderGameInner(gameStateCopy));
    }

    //võtab ette inti maatriksi ja vaatab , mis kohad on täidetud ja kuvab canvase vastavalt sellele
    private void renderGameInner(int[][] gameState) {
        Canvas field = (Canvas) stage.getScene().lookup("#field"); //otsitakse element IDga field
        int blockSizeX = (int) field.getWidth() / gameState[0].length;
        int blockSizeY = (int) field.getHeight() / (gameState.length - 1);
        GraphicsContext graphicsContext = field.getGraphicsContext2D(); //joonistada saab stuffi
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, field.getWidth(), field.getHeight());
        graphicsContext.setFill(Color.RED);

        //käiakse elemendid läbi.. kui on midagi, siis värvitakse kast punaseks
        for(int y = 0; y < gameState.length - 1; y++) {
            for(int x = 0; x < gameState[0].length; x++) {
                if (gameState[y][x] > 0) {
                    graphicsContext.fillRect(x * blockSizeX, y * blockSizeY, blockSizeX, blockSizeY);
                }
            }
        }
    }

    public void updateScore(int newScore) {
        Platform.runLater(() -> updateScoreInternal(newScore));
    }

    private void updateScoreInternal(int newScore) {
        Text scoreText = (Text) stage.getScene().lookup("#score");
        scoreText.setText("Skoor: " + newScore);
    }

    public void endGame(int score) {
        highscoreRepository.add(new Highscore(playerName, score));
        renderHighscores(highscoreRepository.findAll()); //kogu tabel visatakse minema ja kirjutatakase uuesti
        mänguLoogikaLõim.interrupt(); //ütleb lõimele, et pane end kinni (igaks juhuks)
        mänguLoogikaLõim = null; //selleks, et saaks uut mängu alustada
        mänguloogika = null;
        showGameOver(); //mäng lõppeb
    }

    //muudab gameOverModal nähtavaks
    private void showGameOver() {
        Platform.runLater(() -> stage.getScene().lookup("#gameOverModal").setVisible(true));
    }


    private void hideGameOver() {
        Platform.runLater(() -> stage.getScene().lookup("#gameOverModal").setVisible(false));
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

    public static void main(String[] args) {
        GraafilineEsitus.launch();
    }
}
