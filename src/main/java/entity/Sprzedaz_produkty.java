package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedStoredProcedureQuery(
        name = "SPRZEDAZ_PRODUKTY_INS",
        procedureName = "SPRZEDAZ_PRODUKTY_INS",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_SPRZEDAZY"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_PRODUKTU"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ILOSC_SZTUK"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class, name = "P_CENA_SPRZEDAZY")})
public class Sprzedaz_produkty {
    private int id_sprzedaz_produkty;
    private int id_sprzedazy;
    private int id_produktu;
    private int ilosc_sztuk;
    private double cena_sprzedazy;

    public Sprzedaz_produkty() {
    }

    public Sprzedaz_produkty(int id_sprzedazy, int id_produktu, int ilosc_sztuk, double cena_sprzedazy) {
        this.id_sprzedazy = id_sprzedazy;
        this.id_produktu = id_produktu;
        this.ilosc_sztuk = ilosc_sztuk;
        this.cena_sprzedazy = cena_sprzedazy;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sprzedaz_produkty_generator")
    @SequenceGenerator(name = "sprzedaz_produkty_generator", sequenceName = "SPRZEDAZ_PRODUKTY_SEQ", allocationSize = 1)
    public int getId_sprzedaz_produkty() {
        return id_sprzedaz_produkty;
    }

    public void setId_sprzedaz_produkty(int id_sprzedaz_produkty) {
        this.id_sprzedaz_produkty = id_sprzedaz_produkty;
    }

    public int getId_sprzedazy() {
        return id_sprzedazy;
    }

    public void setId_sprzedazy(int id_sprzedazy) {
        this.id_sprzedazy = id_sprzedazy;
    }

    public int getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(int id_produktu) {
        this.id_produktu = id_produktu;
    }

    public int getIlosc_sztuk() {
        return ilosc_sztuk;
    }

    public void setIlosc_sztuk(int ilosc_sztuk) {
        this.ilosc_sztuk = ilosc_sztuk;
    }

    public double getCena_sprzedazy() {
        return cena_sprzedazy;
    }

    public void setCena_sprzedazy(double cena_sprzedazy) {
        this.cena_sprzedazy = cena_sprzedazy;
    }
}
