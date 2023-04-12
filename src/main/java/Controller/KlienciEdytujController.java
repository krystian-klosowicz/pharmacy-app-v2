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

public class KlienciEdytujController implements Initializable {

    @FXML
    private TableColumn<Klienci, Integer> idKlienta;
    @FXML
    private TableColumn<Klienci, String> imie;
    @FXML
    private TableColumn<Klienci, String> nazwisko;
    @FXML
    private TableColumn<Klienci, String> pesel;
    @FXML
    private TableColumn<Klienci, String> adres;
    @FXML
    private Text alert;
    @FXML
    private Button deleteButton;
    @FXML
    private ImageView exitImage;
    @FXML
    private ComboBox<String> kategoriaCombo;
    @FXML
    private TextField imieField;
    @FXML
    private TextField nazwiskoField;
    @FXML
    private ComboBox<String> producentCombo;
    @FXML
    private TableView<Klienci> table;
    @FXML
    private Button wszyscyButton;
    @FXML
    private Button wyszukajButton;

    private ObservableList<Klienci> klienciLista = FXCollections.observableArrayList();
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager em;

    //Funkcje
    @FXML
    private void exit() {
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
        em.close();
    }

    public void pokazWszystkich() {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Klienci> query = em.createQuery("select p from Klienci p", Klienci.class);

        klienciLista = FXCollections.<Klienci>observableArrayList(query
                .getResultList());

        table.setItems(klienciLista);
        em.getTransaction().commit();
        em.close();
    }

    //FUNKCJA DO WYSZUKIWANIA PRODUKTOW PO PRODUCENCIE KATEGORI LUB NAZWIE
    public void wyszukaj()
    {
        EntityManager em= factory.createEntityManager();
        em.getTransaction().begin();

        String imie = imieField.getText();
        String nazwisko = nazwiskoField.getText();


        //---------------------------------------------------------------------------------
        String w1,w2;
        if(imie=="") {
            w1 = "imie IS NOT NULL";
        } else w1 = "imie='"+imie+"'";

        if(nazwisko=="") {
            w2 = "nazwisko IS NOT NULL";
        } else w2 = "nazwisko='"+nazwisko+"'";

        TypedQuery<Klienci> query = em.createQuery(
                "select k from Klienci k where "+w1+" AND "+w2+"", Klienci.class);
        ObservableList<Klienci> klienciList;
        klienciList= FXCollections.<Klienci> observableArrayList(query
                .getResultList());
        table.setItems(klienciList);
        em.getTransaction().commit();
        em.close();
    }

    public void setDeleteButton() {

            if (table.getSelectionModel().getSelectedItem() != null) {
                try {
                EntityManager em = factory.createEntityManager();
                em.getTransaction().begin();
                StoredProcedureQuery query1 = em.createNamedStoredProcedureQuery("KLIENCI_DEL");
                query1.setParameter("P_ID_KLIENTA", table.getSelectionModel().getSelectedItem().getId_klienta());
                query1.execute();
                alert.setText("");
                System.out.println("Usunięto klienta! ");
                em.getTransaction().commit();
                em.close();
                pokazWszystkich();
                } catch (Exception e) {
                    alert.setText("Ten klient posiada sprzedany produkt i nie można go usunać!");
                }
            } else {
                alert.setText("Nie wybrano klienta! ");
            }

    }




    public void initialize(URL url, ResourceBundle rb) throws NumberFormatException {

        //Wczytanie produktów
        em=factory.createEntityManager();
        em.getTransaction().begin();
        idKlienta.setCellValueFactory(new PropertyValueFactory<Klienci, Integer>("id_klienta"));
        imie.setCellValueFactory(new PropertyValueFactory<Klienci, String>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<Klienci, String>("nazwisko"));
        pesel.setCellValueFactory(new PropertyValueFactory<Klienci, String>("pesel"));
        adres.setCellValueFactory(new PropertyValueFactory<Klienci, String>("adres"));
        pokazWszystkich();
        table.setEditable(true);
        em.getTransaction().commit();


        //Edycja
        Callback<TableColumn<Klienci, String>, TableCell<Klienci, String>> defaultCellFactory = TextFieldTableCell.forTableColumn();
        String r1 = "[a-zA-Z]{3,}.*";// do nazwy
        String r2 = "[0-9]{4}[0-3]{1}[0-9]{6}"; //pesel


        //  edytowanie imienia klienta
        imie.setCellFactory(col -> {
            TableCell<Klienci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        imie.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Klienci, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Klienci, String> t) {

                if(t.getNewValue().matches(r1))
                {
                    em.getTransaction().begin();
                    System.out.println("Zaktualizowano nazwe produktu");
                    ((Klienci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setImie(t.getNewValue());
                    Klienci p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    System.out.println(p.toString());
                    p.setImie(t.getNewValue());
                    StoredProcedureQuery query = em.createNamedStoredProcedureQuery("KLIENCI_UPD");
                    query.setParameter("P_ID_KLIENTA", p.getId_klienta());
                    query.setParameter("P_IMIE", p.getImie());
                    query.setParameter("P_NAZWISKO", p.getNazwisko());
                    query.setParameter("P_PESEL", p.getPesel());
                    query.setParameter("P_ADRES", p.getAdres());
                    query.execute();
                    em.getTransaction().commit();
//                    controller.zaktualizujProdukt(p);
                    alert.setText("");

                }else alert.setText("Błędne dane! ");
                pokazWszystkich();
            }
        });


        //  edytowanie nazwiska
        nazwisko.setCellFactory(col -> {
            TableCell<Klienci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        nazwisko.setOnEditCommit((TableColumn.CellEditEvent<Klienci, String> t) -> {

            if (t.getNewValue().matches(r1)) {
                em.getTransaction().begin();
                System.out.println("Zaktualizowano nazwe produktu");
                ((Klienci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNazwisko(t.getNewValue());
                Klienci p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                System.out.println(p.toString());
                p.setNazwisko(t.getNewValue());
                StoredProcedureQuery query = em.createNamedStoredProcedureQuery("KLIENCI_UPD");
                query.setParameter("P_ID_KLIENTA", p.getId_klienta());
                query.setParameter("P_IMIE", p.getImie());
                query.setParameter("P_NAZWISKO", p.getNazwisko());
                query.setParameter("P_PESEL", p.getPesel());
                query.setParameter("P_ADRES", p.getAdres());
                query.execute();
                em.getTransaction().commit();
//                    controller.zaktualizujProdukt(p);
                alert.setText("");

            } else alert.setText("Błędne dane! ");
            pokazWszystkich();
        });


        //  edytowanie peselu
        pesel.setCellFactory(col -> {
            TableCell<Klienci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        pesel.setOnEditCommit((TableColumn.CellEditEvent<Klienci, String> t) -> {

            if (t.getNewValue().matches(r2)) {
                em.getTransaction().begin();
                System.out.println("Zaktualizowano nazwe produktu");
                ((Klienci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPesel(t.getNewValue());
                Klienci p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                System.out.println(p.toString());
                p.setPesel(t.getNewValue());
                StoredProcedureQuery query = em.createNamedStoredProcedureQuery("KLIENCI_UPD");
                query.setParameter("P_ID_KLIENTA", p.getId_klienta());
                query.setParameter("P_IMIE", p.getImie());
                query.setParameter("P_NAZWISKO", p.getNazwisko());
                query.setParameter("P_PESEL", p.getPesel());
                query.setParameter("P_ADRES", p.getAdres());
                query.execute();
                em.getTransaction().commit();
                alert.setText("");

            } else alert.setText("Błędne dane! ");
            pokazWszystkich();
        });


        //  edytowanie adresu
        adres.setCellFactory(col -> {
            TableCell<Klienci, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        adres.setOnEditCommit((TableColumn.CellEditEvent<Klienci, String> t) -> {

            if (t.getNewValue().matches(r1)) {
                em.getTransaction().begin();
                System.out.println("Zaktualizowano nazwe produktu");
                ((Klienci) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAdres(t.getNewValue());
                Klienci p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                System.out.println(p.toString());
                p.setAdres(t.getNewValue());
                StoredProcedureQuery query = em.createNamedStoredProcedureQuery("KLIENCI_UPD");
                query.setParameter("P_ID_KLIENTA", p.getId_klienta());
                query.setParameter("P_IMIE", p.getImie());
                query.setParameter("P_NAZWISKO", p.getNazwisko());
                query.setParameter("P_PESEL", p.getPesel());
                query.setParameter("P_ADRES", p.getAdres());
                query.execute();
                em.getTransaction().commit();
                alert.setText("");

            } else alert.setText("Błędne dane! ");
            pokazWszystkich();
        });
    }
}
