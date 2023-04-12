package Controller;

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
import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProduktyDodajController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private Text alert;
    @FXML
    private TextField cenaField;
    @FXML
    private Label cenaLabel;
    @FXML
    private Button clearButton;
    @FXML
    private ImageView exitImage;
    @FXML
    private TextField iloscField;
    @FXML
    private Label iloscLabel;
    @FXML
    private ComboBox<String> kategoriaCombo;
    @FXML
    private Label kategoriaLabel;
    @FXML
    private TextField nazwaField;
    @FXML
    private Label nazwaLabel;
    @FXML
    private ComboBox<String> producentCombo;
    @FXML
    private Label producentLabel;
    @FXML
    private TextField rabatField;

    @FXML
    private Label rabatLabel;

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
//    private static EntityManager em = factory.createEntityManager();
    //Funkcje

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }

    public void clear(){
        nazwaField.setText("");
        cenaField.setText("");
        iloscField.setText("");
        kategoriaCombo.getSelectionModel().clearSelection();
        producentCombo.getSelectionModel().clearSelection();
        nazwaLabel.setTextFill(Color.BLACK);
        cenaLabel.setTextFill(Color.BLACK);
        iloscLabel.setTextFill(Color.BLACK);
        rabatField.setText("");
        alert.setText("");

    }

    public void add(){

        String r1 = "[a-zA-Z]{3,}.*";// do nazwy
        String r2 = "[0-9]{1,}";
        String r3 = "[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";

        if(!nazwaField.getText().matches(r1)) nazwaLabel.setTextFill(Color.RED);
        else nazwaLabel.setTextFill(Color.BLACK);
        if(producentCombo.getSelectionModel().isEmpty())producentLabel.setTextFill(Color.RED);
        else producentLabel.setTextFill(Color.BLACK);
        if(kategoriaCombo.getSelectionModel().isEmpty())kategoriaLabel.setTextFill(Color.RED);
        else kategoriaLabel.setTextFill(Color.BLACK);
        if(iloscField.getText().matches(r2) && Integer.parseInt(iloscField.getText())>0) iloscLabel.setTextFill(Color.BLACK);
        else iloscLabel.setTextFill(Color.RED);
        if(cenaField.getText().matches(r3)&& Float.parseFloat(cenaField.getText())>0) cenaLabel.setTextFill(Color.BLACK);
        else cenaLabel.setTextFill(Color.RED);
        if(rabatField.getText().matches(r3)&& Float.parseFloat(rabatField.getText())>=0) rabatLabel.setTextFill(Color.BLACK);
        else rabatLabel.setTextFill(Color.RED);


        if(rabatField.getText()!="" && nazwaField.getText().matches(r1) && !producentCombo.getSelectionModel().isEmpty() && !kategoriaCombo.getSelectionModel().isEmpty() && iloscField.getText().matches(r2) && Integer.parseInt(iloscField.getText())>0 && cenaField.getText().matches(r3) && Float.parseFloat(cenaField.getText())>0&& rabatField.getText().matches(r3) && Float.parseFloat(rabatField.getText())>=0)
        {
            EntityManager em = factory.createEntityManager();
            em.getTransaction().begin();
            List<Number> list1 = new ArrayList<>();
            List<Number> list2= new ArrayList<>();
            //Pobranie id kategeorii oraz producenta
            StoredProcedureQuery query1 = em.createNamedStoredProcedureQuery("KATEGORIE_SEL_ID_BY_NAME");
            StoredProcedureQuery query2 = em.createNamedStoredProcedureQuery("PRODUCENCI_SEL_ID_BY_NAME");
            query1.setParameter("K_NAZWA_KATEGORII",kategoriaCombo.getSelectionModel().getSelectedItem());
            query2.setParameter("P_NAZWA_PRODUCENTA",producentCombo.getSelectionModel().getSelectedItem());
            query1.execute();
            query2.execute();
            list1 = query1.getResultList();
            list2= query2.getResultList();
            int id_kat = Integer.parseInt(list1.get(0).toString());
            int id_prod= Integer.parseInt(list2.get(0).toString());



            alert.setText("");
            //wywolanie procedury dodajacej produkt
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("PRODUKTY_INS");
            query.setParameter("P_ID_PRODUCENTA", id_prod);
            query.setParameter("P_ID_KATEGORII", id_kat);
            query.setParameter("P_NAZWA_PRODUKTU", nazwaField.getText());
            query.setParameter("P_CENA_PRODUKTU", Double.parseDouble(cenaField.getText()));
            query.setParameter("P_ILOSC_SZTUK", Integer.parseInt(iloscField.getText()));
            query.setParameter("P_RABAT", Integer.parseInt(rabatField.getText()));
            query.execute();

            //dodanie tego samego produktu na magazyn
            List<Number> list3 = new ArrayList<>();
            StoredProcedureQuery query3= em.createNamedStoredProcedureQuery("PRODUKTY_SELECT_ID_MAX");
            query3.execute();
            list3 = query3.getResultList();
            int id_prod_max = Integer.parseInt(list3.get(0).toString());
            StoredProcedureQuery query4 = em.createNamedStoredProcedureQuery("MAGAZYN_INS");
            query4.setParameter("P_ID_PRODUKTU", id_prod_max);
            query4.setParameter("P_CENA_HURTOWA", Double.parseDouble(cenaField.getText())*0.8);
            query4.setParameter("P_ILOSC_SZTUK", 10000);
            query4.execute();




            em.getTransaction().commit();
            em.close();
            clear();
            alert.setText("Dodano produkt!");

        }else alert.setText("Błąd danych!");

    }

    public void initialize(URL url, ResourceBundle rb)
    {
            List<String> list1 = new ArrayList<>();
            List<String> list2 = new ArrayList<>();
            EntityManager em= factory.createEntityManager();
            em.getTransaction().begin();
            StoredProcedureQuery query1 = em.createNamedStoredProcedureQuery("KATEGORIE_SEL");
            StoredProcedureQuery query2 = em.createNamedStoredProcedureQuery("PRODUCENCI_SEL");
            query1.execute();
            query2.execute();

            list1 = query1.getResultList();
            list2 = query2.getResultList();

            ObservableList<String> kategorie = FXCollections.observableArrayList(list1);
            ObservableList<String> producenci = FXCollections.observableArrayList(list2);

            kategoriaCombo.setItems(kategorie);
            producentCombo.setItems(producenci);

            em.getTransaction().commit();
            em.close();


    }
}
