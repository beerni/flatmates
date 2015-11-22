package edu.eetac.dsa.flatmates.entity;

/**
 * Created by Admin on 21/11/2015.
 */
public class PuntosGrupo {
    private String loginid;
    private String grupoid;
    private int puntos;

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getGrupoid() {
        return grupoid;
    }

    public void setGrupoid(String grupoid) {
        this.grupoid = grupoid;
    }
}
