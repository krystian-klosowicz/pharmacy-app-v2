package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedStoredProcedureQuery(
        name = "SPRZEDAZ_INS",
        procedureName = "SPRZEDAZ_INS",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class, name = "P_SUMA_SPRZEDAZY"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "P_DATA_SPRZEDAZY"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_KLIENTA"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_RABATU"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_TYP_PLATNOSCI")})
@NamedStoredProcedureQuery(
        name = "SPRZEDAZ_SELECT_ID_MAX",
        procedureName = "SPRZEDAZ_SELECT_ID_MAX",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Sprzedaz.class, name = "my_cursor") })
public class Sprzedaz {
    private int id_sprzedazy;
    private double suma_sprzedazy;
    private String data_sprzedazy;
    private String typ_platnosci;
    private int id_klienta;
    private int id_rabatu;

    public Sprzedaz(int id_sprzedazy, double suma_sprzedazy, String data_sprzedazy, String typ_platnosci, int id_klienta, int id_rabatu) {
        this.id_sprzedazy = id_sprzedazy;
        this.suma_sprzedazy = suma_sprzedazy;
        this.data_sprzedazy = data_sprzedazy;
        this.typ_platnosci = typ_platnosci;
        this.id_klienta = id_klienta;
        this.id_rabatu = id_rabatu;
    }

    public Sprzedaz(){
    }


    public Sprzedaz(double suma_sprzedazy, String data_sprzedazy, String typ_platnosci, int id_klienta, int id_rabatu){
        this.suma_sprzedazy = suma_sprzedazy;
        this.data_sprzedazy = data_sprzedazy;
        this.typ_platnosci = typ_platnosci;
        this.id_klienta = id_klienta;
        this.id_rabatu = id_rabatu;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sprzedaz_generator")
    @SequenceGenerator(name = "sprzedaz_generator", sequenceName = "SPRZEDAZ_SEQ", allocationSize = 1)
    public int getId_sprzedazy() {
        return id_sprzedazy;
    }

    public void setId_sprzedazy(int id_sprzedazy) {
        this.id_sprzedazy = id_sprzedazy;
    }

    public double getSuma_sprzedazy() {
        return suma_sprzedazy;
    }

    public void setSuma_sprzedazy(double suma_sprzedazy) {
        this.suma_sprzedazy = suma_sprzedazy;
    }

    public String getData_sprzedazy() {
        return data_sprzedazy;
    }

    public void setData_sprzedazy(String data_sprzedazy) {
        this.data_sprzedazy = data_sprzedazy;
    }

    public int getId_klienta() {
        return id_klienta;
    }

    public void setId_klienta(int id_klienta) {
        this.id_klienta = id_klienta;
    }

    public int getId_rabatu() {
        return id_rabatu;
    }

    public void setId_rabatu(int id_rabatu) {
        this.id_rabatu = id_rabatu;
    }

    public String getTyp_platnosci() {
        return typ_platnosci;
    }

    public void setTyp_platnosci(String typ_platnosci) {
        this.typ_platnosci = typ_platnosci;
    }
}