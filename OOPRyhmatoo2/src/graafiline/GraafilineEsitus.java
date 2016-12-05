package graafiline;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class GraafilineEsitus extends Application {
    @Override
    public void start(Stage peaLava){

        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Canvas lõuend = new Canvas();
        Text pealKiri = new Text("Ultimate Tetris");
        Scene stseen1 = new Scene(lõuend, 500, 600);
        Rectangle klotsiTükk = new Rectangle(stseen1.getWidth()/25,stseen1.getHeight()/25);
        Canvas lõuend = new Canvas(stseen1.getHeight(), stseen1.getWidth());
        GraphicsContext gc = lõuend.getGraphicsContext2D();
        gc.setFill(Color.ORANGE);
        gc.fillRect(100,100,stseen1.getWidth()/25,stseen1.getWidth()/25);

        klotsiTükk.setFill(Color.ORANGE);


        hBox.setAlignment(Pos.CENTER);

        //pealKiri.setFont(new Font(40));
        //pealKiri.setFill(Color.WHITE);

        //vBox.getChildren().add(pealKiri);
        //hBox.getChildren().add(lõuend);


        vBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        stseen1.widthProperty().addListener(observable -> {
                    double stseeniLaius = stseen1.getWidth();
                    klotsiTükk.setWidth(stseeniLaius / 25);
                    klotsiTükk.setHeight(stseeniLaius / 25);
                    lõuend.setWidth(stseeniLaius);
                });

        stseen1.heightProperty().addListener(observable -> {
            lõuend.setHeight(stseen1.getHeight());
        });

        peaLava.setScene(stseen1);
        peaLava.setTitle("Ultimate Tetris");
        peaLava.show();

    }
}
