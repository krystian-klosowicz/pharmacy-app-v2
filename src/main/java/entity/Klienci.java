package entity;

import javax.persistence.*;
@Entity
@NamedStoredProcedureQuery(
        name = "KLIENCI_INS",
        procedureName = "KLIENCI_INS",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IMIE"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_NAZWISKO"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_PESEL"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_ADRES"),})

@NamedStoredProcedureQuery(
        name = "KLIENCI_UPD",
        procedureName = "KLIENCI_UPD",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_KLIENTA"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IMIE"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_NAZWISKO"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_PESEL"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_ADRES"),})

@NamedStoredProcedureQuery(
        name = "KLIENCI_DEL",
        procedureName = "KLIENCI_DEL",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_KLIENTA")})
@NamedStoredProcedureQuery(
        name = "KLIENCI_SEL",
        procedureName = "KLIENCI_SEL",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Klienci.class, name = "my_cursor") })
@NamedStoredProcedureQuery(
        name = "KLIENCI_SEL_ID_BY_FULL_NAME",
        procedureName = "KLIENCI_SEL_ID_BY_FULL_NAME",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "K_FULL_NAME"),
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Klienci.class, name = "my_cursor") })




public class Klienci {
    private int id_klienta;
    private String imie;
    private String nazwisko;
    private String pesel;
    private String adres;

    public Klienci() {
    }

    public Klienci(String imie, String nazwisko, String pesel, String adres) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.adres = adres;
    }

    public Klienci(int id_klienta, String imie, String nazwisko, String pesel, String adres) {
        this.id_klienta = id_klienta;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.adres = adres;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "klienci_generator")
    @SequenceGenerator(name = "klienci_generator", sequenceName = "KLIENCI_SEQ", allocationSize = 1)
    public int getId_klienta() {
        return id_klienta;
    }

    public void setId_klienta(int id_klienta) {
        this.id_klienta = id_klienta;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}