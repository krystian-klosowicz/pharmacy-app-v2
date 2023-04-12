package Controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML
    private Label aptekaLabel;
    @FXML
    private ImageView exitImage;
    @FXML
    private ImageView logoutImage;
    @FXML
    private Pane ProduktyPane;
    @FXML
    private Pane SprzedazPane;
    @FXML
    private Pane KlienciPane;
    @FXML
    private Pane ProducenciPane;
    @FXML
    private Pane KategoriePane;

    //ZMIANY SCEN
    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void logout() throws IOException {

        //System.out.println("Wylogowano");
        //Pobranie Stage obecnego i zamkniecie
        Stage current_stage = (Stage) logoutImage.getScene().getWindow();
        current_stage.close();


        //nowy stage, przejscie do logowania
        Stage home_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 350);
        home_stage.setScene(scene);
        home_stage.setTitle("Login.fxml");
        home_stage.show();
    }
    @FXML
    private void changeToSprzedaz() throws IOException {
        //System.out.println("changeToSprzedaz");
        Stage current_stage = (Stage) SprzedazPane.getScene().getWindow();
        FXMLLoader sprzedaz= new FXMLLoader(Main.class.getResource("/fxml/Sprzedaz.fxml"));
        Scene sceneSprzedaz= new Scene(sprzedaz.load(), 750, 500);
        current_stage.setScene(sceneSprzedaz);
        current_stage.setTitle("Sprzedaz.fxml");
    }
    @FXML
    private void changeToKategorie() throws IOException {
        //System.out.println("changeToKategorie");
        Stage current_stage = (Stage) KategoriePane.getScene().getWindow();
        FXMLLoader kategorie = new FXMLLoader(Main.class.getResource("/fxml/Kategorie.fxml"));
        Scene sceneKategorie = new Scene(kategorie.load(), 750, 500);
        current_stage.setScene(sceneKategorie);
        current_stage.setTitle("Kategorie.fxml");


    }
    @FXML
    private void changeToKlienci() throws IOException {
        Stage current_stage = (Stage) KlienciPane.getScene().getWindow();
        FXMLLoader klienci = new FXMLLoader(Main.class.getResource("/fxml/Klienci.fxml"));
        Scene sceneKlienci = new Scene(klienci.load(), 750, 500);
        current_stage.setScene(sceneKlienci);
        current_stage.setTitle("Klienci.fxml");
    }
    @FXML
    private void changeToProducenci() throws IOException {
        //System.out.println("changeToProducenci");
        Stage current_stage = (Stage) ProducenciPane.getScene().getWindow();
        FXMLLoader producenci = new FXMLLoader(Main.class.getResource("/fxml/Producenci.fxml"));
        Scene sceneProducenci = new Scene(producenci.load(), 750, 500);
        current_stage.setScene(sceneProducenci);
        current_stage.setTitle("Producenci.fxml");
    }
    @FXML
    private void changeToProdukty() throws IOException {
        //System.out.println("changeToProdukty");
        Stage current_stage = (Stage) ProduktyPane.getScene().getWindow();
        FXMLLoader produkty = new FXMLLoader(Main.class.getResource("/fxml/Produkty.fxml"));
        Scene sceneProdukty = new Scene(produkty.load(), 750, 500);
        current_stage.setScene(sceneProdukty);
        current_stage.setTitle("Produkty.fxml");
    }






}