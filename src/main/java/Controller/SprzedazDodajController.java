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
import javax.persistence.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SprzedazDodajController implements Initializable {
    @FXML
    private ImageView exitImage;
    @FXML
    private ComboBox klientCombo;
    @FXML
    private ComboBox platnoscTyp;
    @FXML
    private ComboBox rabatCombo;
    @FXML
    private Label klientLabel;
    @FXML
    private Label rabatLabel;
    @FXML
    private Label platnoscLabel;
    //TABELA PRODUKTY
    @FXML
    private TableView<Produkty> produktyTable;
    @FXML
    private TableColumn<Produkty, Integer> produktyTable_idKategorii;
    @FXML
    private TableColumn<Produkty, Integer> produktyTable_idProducenta;
    @FXML
    private TableColumn<Produkty, Integer> produktyTable_idProduktu;
    @FXML
    private TableColumn<Produkty, String> produktyTable_nazwa;
    @FXML
    private TableColumn<Produkty, Double> produktyTable_cena;
    @FXML
    private TableColumn<Produkty, Integer> produktyTable_ilośc;
    @FXML
    private TableColumn<Produkty, Double> produktyTable_rabat;
    //TABELA WYBRANE PRODUKTY
    @FXML
    private TableView<Produkty> wybraneTable;
    @FXML
    private TableColumn<Produkty, Integer> wybraneTable_idKategorii;
    @FXML
    private TableColumn<Produkty, Integer> wybraneTable_idProducenta;
    @FXML
    private TableColumn<Produkty, Integer> wybraneTable_idProduktu;
    @FXML
    private TableColumn<Produkty, String> wybraneTable_nazwa;
    @FXML
    private TableColumn<Produkty, Double> wybraneTable_cena;
    @FXML
    private TableColumn<Produkty, Integer> wybraneTable_ilośc;
    @FXML
    private Text alert;
    @FXML
    private TextField wybrana_nazwaField;
    @FXML
    private TextField iloscField;
    @FXML
    private Label iloscLabel;
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager em;
    private ObservableList<Produkty> produktyLista=FXCollections.observableArrayList();
    private ObservableList<Produkty> wybrane_produktyLista=FXCollections.observableArrayList();
    private Produkty selectedProduct;

    //Metody
    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }
    public void pokazProdukty()
    {
        EntityManager em= factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Produkty> query = em.createQuery(
                "select p from Produkty p", Produkty.class);


        produktyLista= FXCollections.<Produkty> observableArrayList(query
                .getResultList());

        produktyTable.setItems(produktyLista);
        em.getTransaction().commit();
        em.close();
    }

    public void selectProduct()
    {
       selectedProduct = produktyTable.getSelectionModel().getSelectedItem();
        wybrana_nazwaField.setText(produktyTable.getSelectionModel().getSelectedItem().getNazwa_produktu());
    }

    public void addProduct()
    {

        if(produktyTable.getSelectionModel().getSelectedItem()!=null)
        {
            //Jeżeli produkty jest wybrany to sprawdzamy dostepność sztuk danego produktu
            String r1 = "[0-9]{1,}";
//            System.out.println("Wpisana ilosc produktow: "+iloscField.getText());
//            System.out.println("Dostepna ilosc produktow: "+selectedProduct.getIlosc_sztuk());
            if(iloscField.getText().matches(r1) && selectedProduct.getIlosc_sztuk()>=Integer.parseInt(iloscField.getText()) && selectedProduct.getIlosc_sztuk()>0 && Integer.parseInt(iloscField.getText())>0)
            {
                int ilosc = Integer.parseInt(iloscField.getText());
                int stan = selectedProduct.getIlosc_sztuk();
                Produkty new_prod = new Produkty(selectedProduct.getId_produktu(),selectedProduct.getId_producenta(),selectedProduct.getId_kategorii(),selectedProduct.getNazwa_produktu(),selectedProduct.getCena_produktu(),selectedProduct.getIlosc_sztuk(),selectedProduct.getRabat());
                new_prod.setIlosc_sztuk(ilosc);
                wybrane_produktyLista.addAll(new_prod);
                selectedProduct.setIlosc_sztuk(stan-ilosc);
                wybraneTable.setItems(wybrane_produktyLista);
                produktyTable.setItems(produktyLista);
                //odswiezenie tabel
                wybraneTable.refresh();
                produktyTable.refresh();
                iloscLabel.setTextFill(Color.BLACK);
            }else iloscLabel.setTextFill(Color.RED);



        }
        else System.out.println("Nie wybrano");
    }



    public void confirmFaktura()
    {
        //Sprawdzenie czy klient,typ płatnosci oraz rabat został zaznaczony w combobox
        if(klientCombo.getSelectionModel().getSelectedItem()==null)klientLabel.setTextFill(Color.RED);
        else klientLabel.setTextFill(Color.BLACK);
        if(platnoscTyp.getSelectionModel().getSelectedItem()==null)platnoscLabel.setTextFill(Color.RED);
        else platnoscLabel.setTextFill(Color.BLACK);
        if(rabatCombo.getSelectionModel().getSelectedItem()==null)rabatLabel.setTextFill(Color.RED);
        else rabatLabel.setTextFill(Color.BLACK);

       //tu dopiero sprawdzenie wszystkich wararunkow i dodanie
        if(klientCombo.getSelectionModel().getSelectedItem()!=null && !wybrane_produktyLista.isEmpty() && platnoscTyp.getSelectionModel().getSelectedItem()!=null && rabatCombo.getSelectionModel().getSelectedItem()!=null)
        {
            em = factory.createEntityManager();
            em.getTransaction().begin();

            //Tutaj liczymy sume za wszystkie produkty włącznie z rabatem na dany produkt
            double suma_produkty=0;
            for(int i=0;i<wybrane_produktyLista.size();i++){
                double rabat_na_dany_produkt = 1-wybrane_produktyLista.get(i).getRabat()/100;
                double cena = wybrane_produktyLista.get(i).getCena_produktu();
                double ilosc = wybrane_produktyLista.get(i).getIlosc_sztuk();
                suma_produkty+=ilosc*rabat_na_dany_produkt*cena;
            }
            System.out.println("Sumarycznie: "+ suma_produkty);


            //Tutaj pobieramy id_klienta do sprzedazy
            String pelna_nazwa= klientCombo.getSelectionModel().getSelectedItem().toString();
            StoredProcedureQuery query1 = em.createNamedStoredProcedureQuery("KLIENCI_SEL_ID_BY_FULL_NAME");
            query1.setParameter("K_FULL_NAME",pelna_nazwa);
            query1.execute();
            List<Number> list1= new ArrayList<>();
            list1 = query1.getResultList();
            Integer id_klienta = Integer.parseInt(list1.get(0).toString());

            //Aktualna data
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());

            //Pobieramy id_rabatu
            StoredProcedureQuery query2 = em.createNamedStoredProcedureQuery("RABAT_SEL_ID_BY_RABAT");
            double rabat_sprzedaz = 1-Double.parseDouble(rabatCombo.getSelectionModel().getSelectedItem().toString())/100;
            query2.setParameter("R_RABAT", Double.parseDouble(rabatCombo.getSelectionModel().getSelectedItem().toString()));
            query2.execute();
            List<Number> list2= new ArrayList<>();
            list2 = query2.getResultList();
            Integer id_rabatu = Integer.parseInt(list2.get(0).toString());
            //System.out.println(suma_produkty+" "+date+" "+id_klienta+" "+id_rabatu+" "+platnoscTyp.getSelectionModel().getSelectedItem().toString());

            //Dodanie sprzedazy
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("SPRZEDAZ_INS");
            query.setParameter("P_SUMA_SPRZEDAZY",suma_produkty*rabat_sprzedaz);
            query.setParameter("P_DATA_SPRZEDAZY",date);
            query.setParameter("P_ID_KLIENTA",id_klienta);
            query.setParameter("P_ID_RABATU",id_rabatu);
            query.setParameter("P_TYP_PLATNOSCI",platnoscTyp.getSelectionModel().getSelectedItem().toString());
            query.execute();

            //Teraz dodanie poszczegolnych elementow sprzedaz_produkty
            List<Number> list3 = new ArrayList<>();
            StoredProcedureQuery query3= em.createNamedStoredProcedureQuery("SPRZEDAZ_SELECT_ID_MAX");
            query3.execute();
            list3 = query3.getResultList();
            int id_sprzedaz_max = Integer.parseInt(list3.get(0).toString());


//            System.out.println("-----------------------------------------------------------------------------------------------------");
//            System.out.println("Produkty na liscie wybrane produkty: ");
            for(int i=0;i<wybrane_produktyLista.size();i++){
//                System.out.println("Iteracja: "+ i );
                double rabat_na_dany_produkt = 1-wybrane_produktyLista.get(i).getRabat()/100;
                double cena = wybrane_produktyLista.get(i).getCena_produktu();
//                System.out.println("P_ID_SPRZEDAZY: "+id_sprzedaz_max+" P_ID_PRODUKTU:"+wybrane_produktyLista.get(i).getId_produktu()+" P_ILOSC_SZTUL:" + wybrane_produktyLista.get(i).getIlosc_sztuk());
                StoredProcedureQuery query4 = em.createNamedStoredProcedureQuery("SPRZEDAZ_PRODUKTY_INS");
                query4.setParameter("P_ID_SPRZEDAZY",id_sprzedaz_max);
                query4.setParameter("P_ID_PRODUKTU",wybrane_produktyLista.get(i).getId_produktu());
                query4.setParameter("P_ILOSC_SZTUK",wybrane_produktyLista.get(i).getIlosc_sztuk());
                query4.setParameter("P_CENA_SPRZEDAZY",cena*rabat_na_dany_produkt);
                query4.execute();



            }
//            System.out.println("-----------------------------------------------------------------------------------------------------");
            //ZAKTUALIZOWAINE STANU PRODUKTOW
//            System.out.println("AKTUALIZOWANE PRODUKTY: ");

            for(int i=0;i<produktyLista.size();i++)
            {
//                System.out.println("P_ID_SPRZEDAZY: "+id_sprzedaz_max+" P_ID_PRODUKTU:"+produktyLista.get(i).getId_produktu()+" P_ILOSC_SZTUL:" + produktyLista.get(i).getIlosc_sztuk());
                StoredProcedureQuery query5 = em.createNamedStoredProcedureQuery("PRODUKTY_UPD");
                query5.setParameter("P_ID_PRODUKTU",produktyLista.get(i).getId_produktu());
                query5.setParameter("P_ID_PRODUCENTA",produktyLista.get(i).getId_producenta());
                query5.setParameter("P_ID_KATEGORII",produktyLista.get(i).getId_kategorii());
                query5.setParameter("P_NAZWA_PRODUKTU",produktyLista.get(i).getNazwa_produktu());
                query5.setParameter("P_CENA_PRODUKTU",produktyLista.get(i).getCena_produktu());
                query5.setParameter("P_ILOSC_SZTUK",produktyLista.get(i).getIlosc_sztuk());
                query5.setParameter("P_RABAT",produktyLista.get(i).getRabat());
                query5.execute();

            }
            em.getTransaction().commit();
            em.close();
            pokazProdukty();
            wybrane_produktyLista.clear();
            alert.setText("Dodano fakture! ");
        }else alert.setText("Błąd! ");

    }

    public void initialize(URL url, ResourceBundle rb) {
        //
        //  -----------------------------------------------------------------------------------------------------------------------
        EntityManager em= factory.createEntityManager();
        em.getTransaction().begin();
        List<String> list2 = new ArrayList<>();
        list2.add("Karta");
        list2.add("Gotówka");
        ObservableList<String> lista_platnosci = FXCollections.observableArrayList(list2);
        platnoscTyp.setItems(lista_platnosci);
        //wczytanie rabatow do combo
        List<Double> lista_rabaty =  new ArrayList<>();
        StoredProcedureQuery query2 = em.createNamedStoredProcedureQuery("RABAT_SEL");
        query2.execute();
        lista_rabaty = query2.getResultList();
        ObservableList<Double> rabaty = FXCollections.observableArrayList(lista_rabaty);
        rabatCombo.setItems(rabaty);

        //  wczytanie klientow do combo
        List<String> lista_klienci= new ArrayList<>();
        StoredProcedureQuery query1 = em.createNamedStoredProcedureQuery("KLIENCI_SEL");
        query1.execute();
        lista_klienci = query1.getResultList();
        ObservableList<String> klienci = FXCollections.observableArrayList(lista_klienci);
        klientCombo.setItems(klienci);
        em.getTransaction().commit();


//        //  wczytanie produktów
        produktyTable_idProduktu.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_produktu"));
        produktyTable_idProducenta.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_producenta"));
        produktyTable_idKategorii.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_kategorii"));
        produktyTable_nazwa.setCellValueFactory(new PropertyValueFactory<Produkty, String>("nazwa_produktu"));
        produktyTable_cena.setCellValueFactory(new PropertyValueFactory<Produkty, Double>("cena_produktu"));
        produktyTable_ilośc.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("ilosc_sztuk"));
        produktyTable_rabat.setCellValueFactory(new PropertyValueFactory<Produkty, Double>("rabat"));
        pokazProdukty();
        produktyTable.setEditable(true);
//
//        // tabela wybrane produkty
        wybraneTable_idProduktu.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_produktu"));
        wybraneTable_idProducenta.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_producenta"));
        wybraneTable_idKategorii.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("id_kategorii"));
        wybraneTable_nazwa.setCellValueFactory(new PropertyValueFactory<Produkty, String>("nazwa_produktu"));
        wybraneTable_cena.setCellValueFactory(new PropertyValueFactory<Produkty, Double>("cena_produktu"));
        wybraneTable_ilośc.setCellValueFactory(new PropertyValueFactory<Produkty, Integer>("ilosc_sztuk"));

    }





}
