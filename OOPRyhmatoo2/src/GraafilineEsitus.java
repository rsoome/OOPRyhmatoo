import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
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
        Text pealKiri = new Text("Ultimate Tetris");

        hBox.setAlignment(Pos.CENTER);

        pealKiri.setFont(new Font(40));
        pealKiri.setFill(Color.WHITE);

        vBox.getChildren().add(pealKiri);
        hBox.getChildren().add(vBox);

        vBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene stseen1 = new Scene(hBox, 500, 600, Color.BLACK);
        peaLava.setScene(stseen1);
        peaLava.setTitle("Ultimate Tetris");
        peaLava.show();

    }
}
