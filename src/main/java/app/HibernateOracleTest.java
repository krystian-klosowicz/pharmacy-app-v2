package app;


import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HibernateOracleTest {



    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager em  = emf.createEntityManager();
        em.getTransaction().begin();

//       ObservableList<Sprzedaz> sprzedazLista=FXCollections.observableArrayList();
//        TypedQuery<Sprzedaz> query = em.createQuery(
//                "select s from Sprzedaz s WHERE s.id_klienta='"+id_klienta+"'", Sprzedaz.class);
//        sprzedazLista= FXCollections.<Sprzedaz> observableArrayList(query
//                .getResultList());

        em.clear();
        em.close();







        em.getTransaction().commit();







    }
}



