package entity;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQuery(
        name = "PRODUCENCI_SEL",
        procedureName = "PRODUCENCI_SEL",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Kategorie.class, name = "my_cursor") })
@NamedStoredProcedureQuery(
        name = "PRODUCENCI_SEL_ID_BY_NAME",
        procedureName = "PRODUCENCI_SEL_ID_BY_NAME",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_NAZWA_PRODUCENTA"),
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Kategorie.class, name = "my_cursor") })

@NamedStoredProcedureQuery(
        name = "PRODUCENCI_INS",
        procedureName = "PRODUCENCI_INS",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_NIP"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_NAZWA_PRODUCENTA"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_ADRES"),
        })
@NamedStoredProcedureQuery(
        name = "PRODUCENCI_UPD",
        procedureName = "PRODUCENCI_UPD",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_PRODUCENTA"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_NIP"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_NAZWA_PRODUCENTA"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_ADRES"),
        })


@NamedStoredProcedureQuery(
        name = "PRODUCENCI_DEL",
        procedureName = "PRODUCENCI_DEL",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_PRODUCENTA")})


public class Producenci {
    private int id_producenta;
    private String nip;
    private String nazwa_producenta;
    private String adres;

    public Producenci() {
    }

    public Producenci(String nip, String nazwa_producenta, String adres) {
        this.nip = nip;
        this.nazwa_producenta = nazwa_producenta;
        this.adres = adres;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producent_generator")
    @SequenceGenerator(name = "producent_generator", sequenceName = "PRODUCENCI_SEQ", allocationSize = 1)
    public int getId_producenta() {
        return id_producenta;
    }

    public void setId_producenta(int id_producenta) {
        this.id_producenta = id_producenta;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNazwa_producenta() {
        return nazwa_producenta;
    }

    public void setNazwa_producenta(String nazwa_producenta) {
        this.nazwa_producenta = nazwa_producenta;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
