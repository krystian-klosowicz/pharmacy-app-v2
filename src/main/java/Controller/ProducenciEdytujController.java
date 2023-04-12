package Controller;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.persistence.*;
import java.net.URL;
import java.util.ResourceBundle;


public class ProducenciEdytujController implements Initializable {


    @FXML
    private Text alert;
    @FXML
    private ImageView exitImage;
    @FXML
    private Button deleteButton;

    //  elementy do tabeli
    @FXML
    private TableView<Producenci> table;
    @FXML
    private TableColumn<Producenci, Integer> idProducenta;
    @FXML
    private TableColumn<Producenci, String> nip;
    @FXML
    private TableColumn<Producenci, String> nazwa;
    @FXML
    private TableColumn<Producenci, String> adres;

    //    private ProducentService producenciService = new ProducentServiceImpl();
    private ObservableList<Producenci> producenciLista=FXCollections.observableArrayList();
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager em= factory.createEntityManager();




    //Funkcje

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }


//    public void dodajProducenta(Producenci producent) {
//        producenciService.dodajProducenta(producent);
//    }
//
//    public ObservableList<Producenci> getProducenciLista() {
//        if(producenciLista.isEmpty())
//            producenciLista.clear();
//        producenciLista = FXCollections.observableArrayList((List<Producenci>) producenciService.listaProducenci());
//
//        return producenciLista;
//    }



    public void pokazWszystkich()
    {
        EntityManager em= factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Producenci> query = em.createQuery(
                "select p from Producenci p", Producenci.class);


        producenciLista= FXCollections.<Producenci> observableArrayList(query
                .getResultList());

        table.setItems(producenciLista);
        em.getTransaction().commit();
        em.close();
    }

    public void setDeleteButton()
    {


        if(table.getSelectionModel().getSelectedItem()!=null){
            try{
                EntityManager em = factory.createEntityManager();
                em.getTransaction().begin();

                StoredProcedureQuery query2 = em.createNamedStoredProcedureQuery("PRODUCENCI_DEL");
                query2.setParameter("P_ID_PRODUCENTA",table.getSelectionModel().getSelectedItem().getId_producenta());
                query2.execute();
                alert.setText("");
                System.out.println("Usunięto producenta! ");
                em.getTransaction().commit();
                em.close();
                pokazWszystkich();
            } catch (Exception e)
            {
                alert.setText("Nie mozna usunac producenta, ponieważ istnieje jego produkt!");
            }

        }else {
            alert.setText("Nie wybrano producenta! ");
        }

    }



    public void initialize(URL url, ResourceBundle rb) throws NumberFormatException {


        //Wczytanie producentow
        idProducenta.setCellValueFactory(new PropertyValueFactory<Producenci, Integer>("id_producenta"));
        nip.setCellValueFactory(new PropertyValueFactory<Producenci, String>("nip"));
        nazwa.setCellValueFactory(new PropertyValueFactory<Producenci, String>("nazwa_producenta"));
        adres.setCellValueFactory(new PropertyValueFactory<Producenci, String>("adres"));
        pokazWszystkich();
        table.setEditable(true);



        //Edycja

        Callback<TableColumn<Producenci, String>, TableCell<Producenci, String>> defaultCellFactory = TextFieldTableCell.forTableColumn();
        String r1 = "[0-9]{10}"; // nip
        String r2 = "[a-zA-Z]{3,}.*"; //nazwa adres

        //  edytowanie nazwy producenta

        nazwa.setCellFactory(col -> {
            TableCell<Producenci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        nazwa.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Producenci, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Producenci, String> t) {

                if(t.getNewValue().matches(r2))
                {
                    em.getTransaction().begin();
                    ((Producenci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNazwa_producenta(t.getNewValue());
                    Producenci p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    p.setNazwa_producenta(t.getNewValue());
                    StoredProcedureQuery query = em.createNamedStoredProcedureQuery("PRODUCENCI_UPD");
                    query.setParameter("P_ID_PRODUCENTA", p.getId_producenta());
                    query.setParameter("P_NAZWA_PRODUCENTA", p.getNazwa_producenta());
                    query.setParameter("P_NIP", p.getNip());
                    query.setParameter("P_ADRES", p.getAdres());
                    query.execute();
                    em.getTransaction().commit();
                    alert.setText("");

                }else alert.setText("Błędne dane! ");
                pokazWszystkich();

            }
        });

        //  edytowanie nipu



        nip.setCellFactory(col -> {
            TableCell<Producenci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        nip.setOnEditCommit((TableColumn.CellEditEvent<Producenci, String> t) -> {



            if(t.getNewValue().matches(r1))
            {
                em.getTransaction().begin();
                ((Producenci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNip(t.getNewValue());
                Producenci p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                p.setNip(t.getNewValue());
                StoredProcedureQuery query = em.createNamedStoredProcedureQuery("PRODUCENCI_UPD");
                query.setParameter("P_ID_PRODUCENTA", p.getId_producenta());
                query.setParameter("P_NAZWA_PRODUCENTA", p.getNazwa_producenta());
                query.setParameter("P_NIP", p.getNip());
                query.setParameter("P_ADRES", p.getAdres());
                query.execute();
                em.getTransaction().commit();
                alert.setText("");

            }else alert.setText("Błędne dane! ");

            pokazWszystkich();
        });


        //  edytowanie adresu


        adres.setCellFactory(col -> {
            TableCell<Producenci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        adres.setOnEditCommit((TableColumn.CellEditEvent<Producenci, String> t) -> {

            if(t.getNewValue().matches(r2))
            {
                em.getTransaction().begin();
                ((Producenci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAdres(t.getNewValue());
                Producenci p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                p.setAdres(t.getNewValue());
                StoredProcedureQuery query = em.createNamedStoredProcedureQuery("PRODUCENCI_UPD");
                query.setParameter("P_ID_PRODUCENTA", p.getId_producenta());
                query.setParameter("P_NIP", p.getNip());
                query.setParameter("P_NAZWA_PRODUCENTA", p.getNazwa_producenta());
                query.setParameter("P_ADRES", p.getAdres());
                query.execute();
                em.getTransaction().commit();
                alert.setText("");

            }else alert.setText("Błędne dane! ");

            pokazWszystkich();
        });
    }






}




