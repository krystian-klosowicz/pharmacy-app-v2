package Controller;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class KlienciDodajController{

    @FXML
    private TextField imieField;
    @FXML
    private Label imieLabel;
    @FXML
    private TextField nazwiskoField;
    @FXML
    private Label nazwiskoLabel;
    @FXML
    private TextField peselField;
    @FXML
    private Label peselLabel;
    @FXML
    private TextField adresField;
    @FXML
    private Label adresLabel;
    @FXML
    private Button addButton;
    @FXML
    private Button clearButton;
    @FXML
    private Text alert;
    @FXML
    private ImageView exitImage;

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
//    private static EntityManager em = factory.createEntityManager();
    //Funkcje

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }

    public void clear(){
        imieField.setText("");
        imieLabel.setTextFill(Color.BLACK);
        nazwiskoField.setText("");
        nazwiskoLabel.setTextFill(Color.BLACK);
        peselField.setText("");
        peselLabel.setTextFill(Color.BLACK);
        adresField.setText("");
        adresLabel.setTextFill(Color.BLACK);
        alert.setText("");
    }

    public void add(){
        String r1 = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ-]*";
        String r2 = "[0-9]{4}[0-3]{1}[0-9]{6}";
        String r3 = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]*";

        if(!imieField.getText().matches(r3) || imieField.getText().isEmpty()) imieLabel.setTextFill(Color.RED);
        else imieLabel.setTextFill(Color.BLACK);
        if(!nazwiskoField.getText().matches(r1) || nazwiskoField.getText().isEmpty()) nazwiskoLabel.setTextFill(Color.RED);
        else nazwiskoLabel.setTextFill(Color.BLACK);
        if(!peselField.getText().matches(r2)) peselLabel.setTextFill(Color.RED);
        else peselLabel.setTextFill(Color.BLACK);
        if(adresField.getText().isEmpty() || adresField.getText().contains("=")) adresLabel.setTextFill(Color.RED);
        else adresLabel.setTextFill(Color.BLACK);

        if(!imieField.getText().matches(r3) || !nazwiskoField.getText().matches(r1) ||  !peselField.getText().matches(r2) || adresField.getText().isEmpty() || adresField.getText().contains("=") )
        {
            alert.setText("Błąd danych!");
        }else{
            EntityManager em = factory.createEntityManager();
            em.getTransaction().begin();

            alert.setText("");

            //wywolanie procedury dodajacej klienta
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("KLIENCI_INS");
            query.setParameter("P_IMIE", imieField.getText());
            query.setParameter("P_NAZWISKO", nazwiskoField.getText());
            query.setParameter("P_PESEL", (peselField.getText()));
            query.setParameter("P_ADRES", adresField.getText());
            query.execute();

            em.getTransaction().commit();
            em.close();
            clear();
            alert.setText("Klient został dodany!");

        }
    }
}