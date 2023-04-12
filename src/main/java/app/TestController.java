//package app;
//
//import java.util.List;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import entity.Klienci;
//import services.KlientService;
//import services.KlientServiceImpl;
//
//
//
//public class TestController
//{
//    private KlientService klienciService = new KlientServiceImpl();
//    private ObservableList<Klienci> klienciLista=FXCollections.observableArrayList();
//
//    public void dodajKlienta(Klienci klient) {
//        klienciService.dodajKlienta(klient);
//    }
//
//    public ObservableList<Klienci> getKlienciLista() {
//        if(klienciLista.isEmpty())
//            klienciLista.clear();
//            klienciLista = FXCollections.observableArrayList((List<Klienci>) klienciService.listaKlienci());
//
//        return klienciLista;
//    }
//
//    public void usunKlienta(Integer id){
//        klienciService.usunKlienta(id);
//    }
//
//    public void zaktualizujKlienta(Klienci klient){
//        klienciService.zaktualizujKlienta(klient);
//    }
//}