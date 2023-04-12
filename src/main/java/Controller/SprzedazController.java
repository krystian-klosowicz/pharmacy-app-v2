package Controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class SprzedazController {
    @FXML
    private ImageView exitImage;
    @FXML
    private ImageView backImage;

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
        //nowy stage, otwieranie SprzedazDodaj.fxml
        Stage home_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/SprzedazDodaj.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 900);
        home_stage.setTitle("SprzedazDodaj.fxml");
        home_stage.setScene(scene);
        home_stage.show();
    }

    @FXML
    private void switchToSprzedazWyswietl() throws IOException {
        //nowy stage, otwieranie FakturyDodaj.fxml
        Stage home_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/SprzedazWyswietl.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 900);
        home_stage.setTitle("SprzedazWyswietl.fxml");
        home_stage.setScene(scene);
        home_stage.show();


    }

}
