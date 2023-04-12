package entity;
import javax.persistence.*;

@Entity
@NamedStoredProcedureQuery(
        name = "KATEGORIE_SEL",
        procedureName = "KATEGORIE_SEL",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Kategorie.class, name = "my_cursor") })
@NamedStoredProcedureQuery(
        name = "KATEGORIE_SEL_ID_BY_NAME",
        procedureName = "KATEGORIE_SEL_ID_BY_NAME",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "K_NAZWA_KATEGORII"),
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Kategorie.class, name = "my_cursor") })
public class Kategorie {
    private int id_kategorii;
    private String nazwa_kategorii;
    private String opis;

    public Kategorie() {
    }

    public Kategorie(String nazwa_kategorii, String opis) {
        this.nazwa_kategorii = nazwa_kategorii;
        this.opis = opis;
    }
    public Kategorie(int id_kategorii, String nazwa_kategorii, String opis) {
        this.id_kategorii = id_kategorii;
        this.nazwa_kategorii = nazwa_kategorii;
        this.opis = opis;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kategorie_generator")
    @SequenceGenerator(name = "kategorie_generator", sequenceName = "KATEGORIE_SEQ", allocationSize = 1)

    public int getId_kategorii() {
        return id_kategorii;
    }

    public void setId_kategorii(int id_kategorii) {
        this.id_kategorii = id_kategorii;
    }

    public String getNazwa_kategorii() {
        return nazwa_kategorii;
    }

    public void setNazwa_kategorii(String nazwa_kategorii) {
        this.nazwa_kategorii = nazwa_kategorii;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
