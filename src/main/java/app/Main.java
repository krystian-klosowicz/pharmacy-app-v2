package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 350);
        stage.setTitle("Login.fxml");
        stage.setScene(scene);
        stage.show();
    }




    //private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    //private static EntityManager em = factory.createEntityManager();




    public static void main(String[] args) {
        System.out.println("hello");


        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("thePersistenceUnit");
        launch();
    }
}
