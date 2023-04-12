package entity;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQuery(
        name = "LOGIN_CHECK",
        procedureName = "LOGIN_CHECK",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Login.class, name = "my_cursor"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_LOGIN"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_HASLO")})


public class Login {
    private int id;
    private String login;
    private String haslo;

    public Login(){

    }

    public Login(String login, String haslo) {
        this.login = login;
        this.haslo = haslo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "login_generator")
    @SequenceGenerator(name = "login_generator", sequenceName = "LOGIN_SEQ", allocationSize = 1)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
}