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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProduktyEdytujController implements Initializable {


    @FXML
    private Text alert;
    @FXML
    private TableColumn<Produkty, Double> cena;
    @FXML
    private Button deleteButton;
    @FXML
    private ImageView exitImage;
    @FXML
    private TableColumn<Produkty, Integer> idKategorii;
    @FXML
    private TableColumn<Produkty, Integer> idProducenta;
    @FXML
    private TableColumn<Produkty, Integer> idProduktu;
    @FXML
    private TableColumn<Produkty, Integer> ilośc;
    @FXML
    private ComboBox<String> kategoriaCombo;
    @FXML
    private TableColumn<Produkty, String> nazwa;
    @FXML
    private TextField nazwaField;
    @FXML
    private ComboBox<String> producentCombo;
    @FXML
    private TableView<Produkty> table;
    @FXML
    private Button wszyscyButton;
    @FXML
    private Button wyszukajButton;
    @FXML
    private TableColumn<Produkty, Double> rabat;


//    private ProduktService produktService = new ProduktServiceImpl();
    private ObservableList<Produkty> produktyLista=FXCollections.observableArrayList();
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager em;




    //Funkcje

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
        em.close();
    }


//
//    public ObservableList<Produkty> getWyszukajProduktyLista(String id_produktu, String id_producenta, String id_kategorii, String nazwa_produktu) {
//        if(produktyLista.isEmpty())
//            produktyLista.clear();
//        produktyLista = FXCollections.observableArrayList((List<Produkty>) produktService.wyszukajProdukty(id_produktu,id_producenta,id_kategorii,nazwa_produktu));
//
//        return produktyLista;
//    }





    public void pokazWszystkich()
    {
        EntityManager em= factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Produkty> query = em.createQuery(
                "select p from Produkty p", Produkty.class);


        produktyLista= FXCollections.<Produkty> observableArrayList(query
                .getResultList());

        table.setItems(produktyLista);
        em.getTransaction().commit();
        em.close();
    }
    //FUNKCJA DO WYSZUKIWANIA PRODUKTOW PO PRODUCENCIE KATEGORI LUB NAZWIE
    public void wyszukaj()
    {
        EntityManager em= factory.createEntityManager();
        em.getTransaction().begin();
        List<Number> list_k = new ArrayList<>();
        List<Number> list_p= new ArrayList<>();
        String id_producenta,id_kategorii;

        if(kategoriaCombo.getSelectionModel().getSelectedItem()!=null)
        {
            StoredProcedureQuery query1 = em.createNamedStoredProcedureQuery("KATEGORIE_SEL_ID_BY_NAME");
            query1.setParameter("K_NAZWA_KATEGORII",kategoriaCombo.getSelectionModel().getSelectedItem());
            query1.execute();
            list_k = query1.getResultList();
            Integer id_kat = Integer.parseInt(list_k.get(0).toString());
            id_kategorii= id_kat.toString();
        } else
        {
            id_kategorii="";
        }

        if(producentCombo.getSelectionModel().getSelectedItem()!=null)
        {
            StoredProcedureQuery query2 = em.createNamedStoredProcedureQuery("PRODUCENCI_SEL_ID_BY_NAME");
            query2.setParameter("P_NAZWA_PRODUCENTA",producentCombo.getSelectionModel().getSelectedItem());
            query2.execute();
            list_p= query2.getResultList();
            Integer id_prod= Integer.parseInt(list_p.get(0).toString());
            id_producenta = id_prod.toString();

        }else
        {
            id_producenta="";
        }


        String nazwa = nazwaField.getText();

        //---------------------------------------------------------------------------------
        String w1,w2,w3;
        if(id_kategorii=="") {
            w1 = "id_kategorii IS NOT NULL";
        } else w1 = "id_kategorii='"+id_kategorii+"'";

        if(id_producenta=="") {
            w2 = "id_producenta IS NOT NULL";
        } else w2 = "id_producenta='"+id_producenta+"'";

        if(nazwa=="") {
            w3 = "nazwa_produktu IS NOT NULL";
        } else w3 = "nazwa_produktu='"+nazwa+"'";
        //---------------------------------------------------------------------------------
        String a= "select p from Produkty p where id_kategorii="+w1+" AND id_producenta="+w2+" AND nazwa_produktu="+w3+"";
        System.out.println(a);
        TypedQuery<Produkty> query = em.createQuery(
                "select p from Produkty p where "+w1+" AND "+w2+" AND "+w3+"", Produkty.class);
        ObservableList<Produkty> produktyList;
        produktyList= FXCollections.<Produkty> observableArrayList(query
                .getResultList());
        table.setItems(produktyList);
        em.getTransaction().commit();
        em.close();
    }


    public void zaktualizujProdukt(Produkty produkt){
//        produktService.zaktualizujProdukt(produkt);
    }

    public void setDeleteButton()
    {

            if(table.getSelectionModel().getSelectedItem()!=null){
                try
                {
                EntityManager em = factory.createEntityManager();
                em.getTransaction().begin();
                StoredProcedureQuery query1 = em.createNamedStoredProcedureQuery("MAGAZYN_DEL");
                query1.setParameter("P_ID_PRODUKTU",table.getSelectionModel().getSelectedItem().getId_produktu());
                query1.execute();
                StoredProcedureQuery query2 = em.createNamedStoredProcedureQuery("PRODUKTY_DEL");
                query2.setParameter("P_ID_PRODUKTU",table.getSelectionModel().getSelectedItem().getId_produktu());
                query2.execute();
                alert.setText("");
                System.out.println("Usunięto produkt! ");
                em.getTransaction().commit();
                em.close();
                pokazWszystkich();
                }catch(Exception e)
                {
                    alert.setText("Dany produkt jest na fakturze, błąd usuwania!");
                }
            }else {
                alert.setText("Nie wybrano produktu! ");
            }

    }


    public void initialize(URL url, ResourceBundle rb) throws NumberFormatException {
        //WCZYTANIE KATEGORII I PRODUCENTOW DO COMBOBOX
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        em=factory.createEntityManager();
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

        //Wczytanie produktów
        idProduktu.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_produktu"));
        idProduktu.setSortable(true);
        idProducenta.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_producenta"));
        idKategorii.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_kategorii"));
        nazwa.setCellValueFactory(new PropertyValueFactory<Produkty, String>("nazwa_produktu"));
        cena.setCellValueFactory(new PropertyValueFactory<Produkty, Double>("cena_produktu"));
        ilośc.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("ilosc_sztuk"));
        rabat.setCellValueFactory(new PropertyValueFactory<Produkty, Double>("rabat"));
        pokazWszystkich();
        table.setEditable(true);
        em.getTransaction().commit();












        //Edycja

        Callback<TableColumn<Produkty, String>, TableCell<Produkty, String>> defaultCellFactory = TextFieldTableCell.forTableColumn();
        String r1 = "[a-zA-Z]{3,}.*";// do nazwy
        String r2 = "[0-9]{1,}";
        String r3 = "[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";

        //  edytowanie nazwy produktu

        nazwa.setCellFactory(col -> {
            TableCell<Produkty, String> cell = defaultCellFactory.call(col);
            cell.setAlignment(Pos.CENTER_LEFT);
            return cell;
        });

        nazwa.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Produkty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Produkty, String> t) {

                if(t.getNewValue().matches(r1))
                {
                    em.getTransaction().begin();
                    System.out.println("Zaktualizowano nazwe produktu");
                    ((Produkty) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNazwa_produktu(t.getNewValue());
                    Produkty p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    System.out.println(p.toString());
                    p.setNazwa_produktu(t.getNewValue());
                    StoredProcedureQuery query = em.createNamedStoredProcedureQuery("PRODUKTY_UPD");
                    query.setParameter("P_ID_PRODUKTU", p.getId_produktu());
                    query.setParameter("P_ID_PRODUCENTA", p.getId_producenta());
                    query.setParameter("P_ID_KATEGORII", p.getId_kategorii());
                    query.setParameter("P_NAZWA_PRODUKTU", p.getNazwa_produktu());
                    query.setParameter("P_CENA_PRODUKTU", p.getCena_produktu());
                    query.setParameter("P_ILOSC_SZTUK", p.getIlosc_sztuk());
                    query.setParameter("P_RABAT", p.getRabat());
                    query.execute();
                    em.getTransaction().commit();
//                    controller.zaktualizujProdukt(p);
                    alert.setText("");


                }else alert.setText("Błędne dane! ");
                pokazWszystkich();
            }
        });

        //  edytowanie ceny

        cena.setCellFactory(TextFieldTableCell.<Produkty, Double>forTableColumn(new DoubleStringConverter()));
        //cena.setCellFactory(col -> new EditingIntegerCell<>());

            cena.setOnEditCommit((TableColumn.CellEditEvent<Produkty, Double> t) -> {

                if (t.getNewValue().toString().matches(r3)) {
                    double cena = t.getNewValue();
                    cena = Math.round(cena * 100.0) / 100.0;
                    System.out.println(t.getNewValue());
                    ((Produkty) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setCena_produktu(cena);
                    Produkty p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    p.setCena_produktu(cena);
                    em.getTransaction().begin();
                    StoredProcedureQuery query = em.createNamedStoredProcedureQuery("PRODUKTY_UPD");
                    query.setParameter("P_ID_PRODUKTU", p.getId_produktu());
                    query.setParameter("P_ID_PRODUCENTA", p.getId_producenta());
                    query.setParameter("P_ID_KATEGORII", p.getId_kategorii());
                    query.setParameter("P_NAZWA_PRODUKTU", p.getNazwa_produktu());
                    query.setParameter("P_CENA_PRODUKTU", p.getCena_produktu());
                    query.setParameter("P_ILOSC_SZTUK", p.getIlosc_sztuk());
                    query.setParameter("P_RABAT", p.getRabat());
                    query.execute();
                    em.getTransaction().commit();
                    alert.setText("");


                } else alert.setText("Błędne dane! ");
                pokazWszystkich();

            });


        //  edytowanie ilosci

        ilośc.setCellFactory(TextFieldTableCell.<Produkty, Integer>forTableColumn(new IntegerStringConverter()));
        //cena.setCellFactory(col -> new EditingIntegerCell<>());
        ilośc.setOnEditCommit((TableColumn.CellEditEvent<Produkty, Integer> t) -> {
            if (t.getNewValue().toString().matches(r2)) {
                ((Produkty) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setIlosc_sztuk(t.getNewValue().intValue());
                Produkty p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                p.setIlosc_sztuk(t.getNewValue());
                em.getTransaction().begin();
                StoredProcedureQuery query = em.createNamedStoredProcedureQuery("PRODUKTY_UPD");
                query.setParameter("P_ID_PRODUKTU", p.getId_produktu());
                query.setParameter("P_ID_PRODUCENTA", p.getId_producenta());
                query.setParameter("P_ID_KATEGORII", p.getId_kategorii());
                query.setParameter("P_NAZWA_PRODUKTU", p.getNazwa_produktu());
                query.setParameter("P_CENA_PRODUKTU", p.getCena_produktu());
                query.setParameter("P_ILOSC_SZTUK", p.getIlosc_sztuk());
                query.setParameter("P_RABAT", p.getRabat());
                query.execute();
                em.getTransaction().commit();
                alert.setText("");
            } else alert.setText("Błędne dane! ");
            pokazWszystkich();

        });

        //  edytowanie rabatu

        rabat.setCellFactory(TextFieldTableCell.<Produkty, Double>forTableColumn(new DoubleStringConverter()));
        //cena.setCellFactory(col -> new EditingIntegerCell<>());

        rabat.setOnEditCommit((TableColumn.CellEditEvent<Produkty, Double> t) -> {

            if (t.getNewValue().toString().matches(r3)) {
                double rabat = t.getNewValue();
                rabat = Math.round(rabat * 100.0) / 100.0;
                System.out.println(t.getNewValue());
                ((Produkty) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setRabat(rabat);
                Produkty p = t.getTableView().getItems().get(t.getTablePosition().getRow());
                p.setRabat(rabat);
                em.getTransaction().begin();
                StoredProcedureQuery query = em.createNamedStoredProcedureQuery("PRODUKTY_UPD");
                query.setParameter("P_ID_PRODUKTU", p.getId_produktu());
                query.setParameter("P_ID_PRODUCENTA", p.getId_producenta());
                query.setParameter("P_ID_KATEGORII", p.getId_kategorii());
                query.setParameter("P_NAZWA_PRODUKTU", p.getNazwa_produktu());
                query.setParameter("P_CENA_PRODUKTU", p.getCena_produktu());
                query.setParameter("P_ILOSC_SZTUK", p.getIlosc_sztuk());
                query.setParameter("P_RABAT", p.getRabat());
                query.execute();
                em.getTransaction().commit();
                alert.setText("");


            } else alert.setText("Błędne dane! ");
            pokazWszystkich();

        });




   }






}
