package Controller;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.persistence.*;



public class ProducenciDodajController {

    @FXML
    private TextField nipField;
    @FXML
    private TextField nazwaField;
    @FXML
    private TextField adresField;
    @FXML
    private Button addButton;
    @FXML
    private Button clearButton;
    @FXML
    private ImageView exitImage;
    @FXML
    private Label nipLabel;
    @FXML
    private Label nazwaLabel;
    @FXML
    private Label adresLabel;
    @FXML
    private Text alert;

    private ObservableList<Producenci> producenciLista=FXCollections.observableArrayList();
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager em= factory.createEntityManager();
    //Funkcje

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }

    public void clear(){
        nipField.setText("");
        nazwaField.setText("");
        adresField.setText("");
        nipLabel.setTextFill(Color.BLACK);
        nazwaLabel.setTextFill(Color.BLACK);
        adresLabel.setTextFill(Color.BLACK);

    }

    public void add(){

        String r1 = "[0-9]{10}"; // nip
        String r2 = "[a-zA-Z]{3,}.*";

        if(!nipField.getText().matches(r1)) nipLabel.setTextFill(Color.RED);
        else nipLabel.setTextFill(Color.BLACK);

        if(!nazwaField.getText().matches(r2)) nazwaLabel.setTextFill(Color.RED);
        else nazwaLabel.setTextFill(Color.BLACK);

        if(!adresField.getText().matches(r2) || adresField.getText().contains("=")) adresLabel.setTextFill(Color.RED);
        else adresLabel.setTextFill(Color.BLACK);

        if(nipField.getText()!="" && nazwaField.getText()!="" && nipField.getText()!="" && adresField.getText()!=""  && nipField.getText().matches(r1) && nazwaField.getText().matches(r2) && adresField.getText().matches(r2))
        {

            EntityManager em = factory.createEntityManager();
            em.getTransaction().begin();

            alert.setText("");

            //wywolanie procedury dodajacej produkt
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("PRODUCENCI_INS");
            query.setParameter("P_NIP", (nipField.getText()));
            query.setParameter("P_NAZWA_PRODUCENTA", nazwaField.getText());
            query.setParameter("P_ADRES", adresField.getText());
            try{
                query.execute();
            }catch (Exception e)
            {
                System.out.println("Błąd!");
                alert.setText("NIP występuje już w bazie!");
            }



            em.getTransaction().commit();
            em.close();
            clear();
            alert.setText("Dodano producenta!");

        }else alert.setText("Błąd danych!");

    }




}