package Controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ProduktyController {
    @FXML
    private ImageView exitImage;
    @FXML
    private ImageView backImage;
    @FXML
    private Pane DodajPane;
    @FXML
    private Pane EdytujPane;

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
        //nowy stage, otwieranie ProduktyDodaj.fxml
        Stage home_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/ProduktyDodaj.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 550);
        home_stage.setTitle("ProduktyDodaj.fxml");
        home_stage.setScene(scene);
        home_stage.show();
    }

    @FXML
    private void switchToEdit() throws IOException {
        //System.out.println("switchToEdit");

        //nowy stage, otwieranie ProduktyEdytuj.fxml
        Stage home_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/ProduktyEdytuj.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 550);
        home_stage.setTitle("ProduktyEdytuj.fxml");
        home_stage.setScene(scene);
        home_stage.show();
    }
}
