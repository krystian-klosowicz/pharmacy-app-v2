package entity;

import javax.persistence.*;
@NamedStoredProcedureQuery(
        name = "RABAT_SEL",
        procedureName = "RABAT_SEL",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Rabat.class, name = "my_cursor") })
@NamedStoredProcedureQuery(
        name = "RABAT_SEL_ID_BY_RABAT",
        procedureName = "RABAT_SEL_ID_BY_RABAT",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class, name = "R_RABAT"),
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Rabat.class, name = "my_cursor") })
@Entity
public class Rabat {
    private int id_rabatu;
    private double rabat;

    public Rabat() {
    }

    public Rabat(double rabat) {
        this.rabat = rabat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rabat_generator")
    @SequenceGenerator(name = "rabat_generator", sequenceName = "MAGAZYN_SEQ", allocationSize = 1)
    public int getId_rabatu() {
        return id_rabatu;
    }

    public void setId_rabatu(int id_rabatu) {
        this.id_rabatu = id_rabatu;
    }

    public double getRabat() {
        return rabat;
    }

    public void setRabat(double rabat) {
        this.rabat = rabat;
    }
}
