package Controller;



import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;

import javax.persistence.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SprzedazWyswietlController implements Initializable {
    @FXML
    private Text alert;

    @FXML
    private Text alertKlient;
    @FXML
    private ImageView exitImage;
    @FXML
    private ComboBox<String> klientCombo;
    @FXML
    private Label klientLabel;
    //Tabale sprzedaz
    @FXML
    private TableView<Sprzedaz> sprzedazTable;
    @FXML
    private TableColumn<Sprzedaz, String> sprzedaz_data;
    @FXML
    private TableColumn<Sprzedaz, Integer> sprzedaz_id_klienta;
    @FXML
    private TableColumn<Sprzedaz, Integer> sprzedaz_id_sprzedazy;
    @FXML
    private TableColumn<Sprzedaz, Double> sprzedaz_suma;
    @FXML
    private TextField sumaField;
    //Tabela szczegoly
    @FXML
    private TableView<Sprzedaz_produkty> szczegolyTable;
    @FXML
    private TableColumn<Sprzedaz_produkty, Integer> szczegoly_id_produktu;
    @FXML
    private TableColumn<Sprzedaz_produkty, Double> szczegoly_cena;
    @FXML
    private TableColumn<Sprzedaz_produkty, Integer> szczegoly_ilosc_sztuk;

    private ObservableList<Sprzedaz> sprzedazLista=FXCollections.observableArrayList();
    private ObservableList<Sprzedaz_produkty> szczegolyLista=FXCollections.observableArrayList();

    //Metody
    @FXML
    private void exit() {
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }
    public void wyswietlSprzedaz()
    {
        EntityManager em= factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Sprzedaz> query = em.createQuery(
                "select s from Sprzedaz s", Sprzedaz.class);


        sprzedazLista= FXCollections.<Sprzedaz> observableArrayList(query
                .getResultList());
        sprzedazTable.setItems(sprzedazLista);
        sprzedaz_id_sprzedazy.setSortType(TableColumn.SortType.ASCENDING);
        sprzedazTable.getSortOrder().add(sprzedaz_id_sprzedazy);
        sprzedazTable.sort();
        em.getTransaction().commit();
        em.close();
    }



    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager em;


    public void pokazSprzedazKlienta()
    {
        if(klientCombo.getSelectionModel().getSelectedItem()!=null)
        {

            EntityManager em= factory.createEntityManager();
            em.getTransaction().begin();
            String pelna_nazwa= klientCombo.getSelectionModel().getSelectedItem().toString();
            StoredProcedureQuery query1 = em.createNamedStoredProcedureQuery("KLIENCI_SEL_ID_BY_FULL_NAME");
            query1.setParameter("K_FULL_NAME",pelna_nazwa);
            query1.execute();
            List<Number> list1= new ArrayList<>();
            list1 = query1.getResultList();
            Integer id_klienta = Integer.parseInt(list1.get(0).toString());


            TypedQuery<Sprzedaz> query = em.createQuery(
                    "select s from Sprzedaz s WHERE s.id_klienta='"+id_klienta+"'", Sprzedaz.class);
            sprzedazLista= FXCollections.<Sprzedaz> observableArrayList(query
                    .getResultList());
            if(sprzedazLista.size()!=0)
            {
                sprzedazTable.setItems(sprzedazLista);
                sprzedaz_id_sprzedazy.setSortType(TableColumn.SortType.ASCENDING);
                sprzedazTable.getSortOrder().add(sprzedaz_id_sprzedazy);
                sprzedazTable.sort();
                alertKlient.setText("");
            }else{
                alertKlient.setText("Brak sprzedazy!");
            }

            em.getTransaction().commit();
            em.close();


        }
    }

    public void pokazSzczegolySprzedazy()
    {
        if(sprzedazTable.getSelectionModel().getSelectedItem()!=null)
        {
            EntityManager em= factory.createEntityManager();
            em.getTransaction().begin();
            sumaField.setText(sprzedazTable.getSelectionModel().getSelectedItem().getSuma_sprzedazy() + " zł");
            Integer id = sprzedazTable.getSelectionModel().getSelectedItem().getId_sprzedazy();
            System.out.println("ID_SPRZEDAZY" + id);
            TypedQuery<Sprzedaz_produkty> query = em.createQuery(
                    "select s from Sprzedaz_produkty s WHERE s.id_sprzedazy='"+id+"'", Sprzedaz_produkty.class);
            szczegolyLista= FXCollections.<Sprzedaz_produkty> observableArrayList(query
                    .getResultList());
            szczegolyTable.setItems(szczegolyLista);
            szczegoly_id_produktu.setSortType(TableColumn.SortType.ASCENDING);
            szczegolyTable.getSortOrder().add(szczegoly_id_produktu);
            szczegolyTable.sort();
            em.getTransaction().commit();
            em.close();
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        EntityManager em= factory.createEntityManager();
        em.getTransaction().begin();
        StoredProcedureQuery query1 = em.createNamedStoredProcedureQuery("KLIENCI_SEL");
        query1.execute();
        List<String> lista_klienci= new ArrayList<>();
        lista_klienci = query1.getResultList();
        ObservableList<String> klienci = FXCollections.observableArrayList(lista_klienci);
        klientCombo.setItems(klienci);
        em.getTransaction().commit();

        sprzedaz_id_sprzedazy.setCellValueFactory(new PropertyValueFactory<Sprzedaz, Integer>("id_sprzedazy"));
        sprzedaz_id_klienta.setCellValueFactory(new PropertyValueFactory<Sprzedaz,Integer>("id_klienta"));
        sprzedaz_data.setCellValueFactory(new PropertyValueFactory<Sprzedaz, String>("data_sprzedazy"));
        sprzedaz_suma.setCellValueFactory(new PropertyValueFactory<Sprzedaz, Double>("suma_sprzedazy"));
        wyswietlSprzedaz();

        szczegoly_id_produktu.setCellValueFactory(new PropertyValueFactory<Sprzedaz_produkty,Integer>("id_produktu"));
        szczegoly_cena.setCellValueFactory(new PropertyValueFactory<Sprzedaz_produkty,Double>("cena_sprzedazy"));
        szczegoly_ilosc_sztuk.setCellValueFactory(new PropertyValueFactory<Sprzedaz_produkty,Integer>("ilosc_sztuk"));


    }





}
