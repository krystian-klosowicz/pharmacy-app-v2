package Controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.persistence.*;
import java.io.IOException;

public class LoginController {
    @FXML
    private ImageView exitImage;
    @FXML
    private TextField LoginField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private Button LoginButton;
    @FXML
    private Text alert;

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");


    @FXML
    private void exit(){
        Stage stage = (Stage) exitImage.getScene().getWindow();
        stage.close();
    }

    public void clear(){
        LoginField.setText("");
        PasswordField.setText("");
        alert.setText("");
    }


    public void login() throws IOException {

        if (LoginField.getText() != null && PasswordField.getText() != null ) {
            EntityManager em = factory.createEntityManager();
            em.getTransaction().begin();

            StoredProcedureQuery query1 = em.createNamedStoredProcedureQuery("LOGIN_CHECK");
            query1.setParameter("P_LOGIN", LoginField.getText());
            query1.setParameter("P_HASLO", PasswordField.getText());
            query1.execute();
            int stan = query1.getResultList().size();
            em.getTransaction().commit();
            em.close();
            alert.setVisible(false);


            if (stan == 1) {
                //Pobranie Stage obecnego i zamkniecie
                Stage current_stage = (Stage) LoginButton.getScene().getWindow();
                current_stage.close();
                //nowy stage, otwieranie Home.fxml
                Stage home_stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Home.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 750, 500);
                home_stage.setTitle("Home.fxml");
                home_stage.setScene(scene);
                home_stage.show();
            } else {
                alert.setVisible(true);
                alert.setText("Podano błędny login lub hasło! ");
            }


        }

    }
}