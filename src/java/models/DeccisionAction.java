/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author eqima
 */
public class DeccisionAction {

    private String deccisionAction;
    private String doigtLibre;
    private String sessionUser;

    public DeccisionAction(String deccisionAction, String DoigtLibre, String sessionu) {
        this.deccisionAction = deccisionAction;
        this.doigtLibre = DoigtLibre;
        this.sessionUser = sessionu;
    }

    public String getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(String sessionUser) {
        this.sessionUser = sessionUser;
    }

    public String getDeccisionAction() {
        return deccisionAction;
    }

    public void setDeccisionAction(String deccisionAction) {
        this.deccisionAction = deccisionAction;
    }

    public String getDoigtLibre() {
        return doigtLibre;
    }

    public void setDoigtLibre(String DoigtLibre) {
        this.doigtLibre = DoigtLibre;
    }

}
