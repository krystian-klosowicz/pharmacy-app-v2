package Controller;


import app.Main;
import entity.Kategorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class KategorieController implements Initializable {
    @FXML
    private ImageView backImage;
    @FXML
    private ImageView exitImage;
    @FXML
    private TableColumn<Kategorie, Integer> idKategorii;
    @FXML
    private Pane mainPane;
    @FXML
    private TableColumn<Kategorie, String> nazwa;
    @FXML
    private TableColumn<Kategorie, String> opis;
    @FXML
    private TableView<Kategorie> table;



private ObservableList<Kategorie> kategorieLista=FXCollections.observableArrayList();
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager em;


    //  Metody

    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void back() throws IOException {
        Stage stage = (Stage) backImage.getScene().getWindow();
        FXMLLoader home = new FXMLLoader(Main.class.getResource("/fxml/Home.fxml"));
        Scene sceneHome = new Scene(home.load(), 750, 500);
        stage.setScene(sceneHome);
        stage.setTitle("Home.fxml");
    }


    public void initialize(URL url, ResourceBundle rb)
    {
        idKategorii.setCellValueFactory(new PropertyValueFactory<Kategorie, Integer>("id_kategorii"));
        nazwa.setCellValueFactory(new PropertyValueFactory<Kategorie, String>("nazwa_kategorii"));
        opis.setCellValueFactory(new PropertyValueFactory<Kategorie, String>("opis"));
        EntityManager em= factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Kategorie>query = em.createQuery(
                "select k from Kategorie k", Kategorie.class);


        kategorieLista= FXCollections.<Kategorie> observableArrayList(query
                .getResultList());

        table.setItems(kategorieLista);
        em.getTransaction().commit();
        em.close();

    }
}
