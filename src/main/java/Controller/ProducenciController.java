package Controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ProducenciController {
    @FXML
    private ImageView exitImage;
    @FXML
    private ImageView backImage;
    @FXML
    private Pane dodajPane;
    @FXML
    private Pane edytujPane;

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void back() throws IOException {
        Stage stage = (Stage) backImage.getScene().getWindow();
        FXMLLoader home = new FXMLLoader(Main.class.getResource("/fxml/Home.fxml"));
        Scene sceneHome = new Scene(home.load(), 750, 500);
        stage.setScene(sceneHome);
        stage.setTitle("Home.fxml");
    }

    @FXML
    private void switchToAdd() throws IOException {
        Stage home_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/ProducenciDodaj.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 550);
        home_stage.setTitle("ProducenciDodaj.fxml");
        home_stage.setScene(scene);
        home_stage.show();
    }

    @FXML
    private void switchToEdit() throws IOException {
        Stage home_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/ProducenciEdytuj.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 550);
        home_stage.setTitle("ProducenciEdytuj.fxml");
        home_stage.setScene(scene);
        home_stage.show();
    }
}
