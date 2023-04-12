package entity;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQuery(
        name = "MAGAZYN_INS",
        procedureName = "MAGAZYN_INS",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_PRODUKTU"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class, name = "P_CENA_HURTOWA"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ILOSC_SZTUK")})
@NamedStoredProcedureQuery(
        name = "MAGAZYN_DEL",
        procedureName = "MAGAZYN_DEL",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "P_ID_PRODUKTU")})
public class Magazyn {
    private int id_magazyn;
    private int id_produktu;
    private double cena_hurtowa;
    private int ilosc_sztuk;

    public Magazyn() {

    }

    public Magazyn(int id_produktu, double cena_hurtowa, int ilosc_sztuk) {
        this.id_produktu = id_produktu;
        this.cena_hurtowa = cena_hurtowa;
        this.ilosc_sztuk = ilosc_sztuk;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "magazyn_generator")
    @SequenceGenerator(name = "magazyn_generator", sequenceName = "MAGAZYN_SEQ", allocationSize = 1)
    public int getId_magazyn() {
        return id_magazyn;
    }

    public void setId_magazyn(int id_magazyn) {
        this.id_magazyn = id_magazyn;
    }

    public int getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(int id_produktu) {
        this.id_produktu = id_produktu;
    }

    public double getCena_hurtowa() {
        return cena_hurtowa;
    }

    public void setCena_hurtowa(double cena_hurtowa) {
        this.cena_hurtowa = cena_hurtowa;
    }

    public int getIlosc_sztuk() {
        return ilosc_sztuk;
    }

    public void setIlosc_sztuk(int ilosc_sztuk) {
        this.ilosc_sztuk = ilosc_sztuk;
    }
}
