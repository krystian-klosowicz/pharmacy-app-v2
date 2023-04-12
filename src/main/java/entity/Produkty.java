package entity;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQuery(
    name = "PRODUKTY_INS",
        procedureName = "PRODUKTY_INS",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_PRODUCENTA"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_KATEGORII"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_NAZWA_PRODUKTU"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class, name = "P_CENA_PRODUKTU"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ILOSC_SZTUK"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_RABAT") })
@NamedStoredProcedureQuery(
        name = "PRODUKTY_UPD",
        procedureName = "PRODUKTY_UPD",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_PRODUKTU"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_PRODUCENTA"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_KATEGORII"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_NAZWA_PRODUKTU"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class, name = "P_CENA_PRODUKTU"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ILOSC_SZTUK"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class, name = "P_RABAT") })
@NamedStoredProcedureQuery(
        name = "PRODUKTY_SELECT_ID_MAX",
        procedureName = "PRODUKTY_SELECT_ID_MAX",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Produkty.class, name = "my_cursor") })
@NamedStoredProcedureQuery(
        name = "PRODUKTY_SELECT_ALL",
        procedureName = "PRODUKTY_SEL_ALL",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Produkty.class, name = "my_cursor") })
@NamedStoredProcedureQuery(
        name = "PRODUKTY_DEL",
        procedureName = "PRODUKTY_DEL",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_PRODUKTU")})




public class Produkty {
    private int id_produktu;
    private int id_producenta;
    private int id_kategorii;
    private String nazwa_produktu;
    private double cena_produktu;
    private int ilosc_sztuk;
    private double rabat;

    public Produkty() {
    }

    public Produkty(int id_producenta, int id_kategorii, String nazwa_produktu, double cena_produktu, int ilosc_sztuk, double rabat) {
        this.id_producenta = id_producenta;
        this.id_kategorii = id_kategorii;
        this.nazwa_produktu = nazwa_produktu;
        this.cena_produktu = cena_produktu;
        this.ilosc_sztuk = ilosc_sztuk;
        this.rabat = rabat;
    }

    public Produkty(int id_produktu, int id_producenta, int id_kategorii, String nazwa_produktu, double cena_produktu, int ilosc_sztuk, double rabat) {
        this.id_produktu = id_produktu;
        this.id_producenta = id_producenta;
        this.id_kategorii = id_kategorii;
        this.nazwa_produktu = nazwa_produktu;
        this.cena_produktu = cena_produktu;
        this.ilosc_sztuk = ilosc_sztuk;
        this.rabat = rabat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produkty_generator")
    @SequenceGenerator(name = "produkty_generator", sequenceName = "PRODUKTY_SEQ", allocationSize = 1)

    public int getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(int id_produktu) {
        this.id_produktu = id_produktu;
    }

    public int getId_producenta() {
        return id_producenta;
    }

    public void setId_producenta(int id_producenta) {
        this.id_producenta = id_producenta;
    }

    public int getId_kategorii() {
        return id_kategorii;
    }

    public void setId_kategorii(int id_kategorii) {
        this.id_kategorii = id_kategorii;
    }

    public String getNazwa_produktu() {
        return nazwa_produktu;
    }

    public void setNazwa_produktu(String nazwa_produktu) {
        this.nazwa_produktu = nazwa_produktu;
    }

    public double getCena_produktu() {
        return cena_produktu;
    }

    public void setCena_produktu(double cena_produktu) {
        this.cena_produktu = cena_produktu;
    }

    public int getIlosc_sztuk() {
        return ilosc_sztuk;
    }

    public void setIlosc_sztuk(int ilosc_sztuk) {
        this.ilosc_sztuk = ilosc_sztuk;
    }

    public double getRabat() {
        return rabat;
    }

    public void setRabat(double rabat) {
        this.rabat = rabat;
    }
}