/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Blob;

/**
 *
 * @author Eqima
 */
public class ViewCSV {

    private String aff_id;
    private String doigt;
    private byte[] empreinte;
    private int qualite;
    private String code_agent;
    private String date;
    private String resultat;

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getAff_id() {
        return aff_id;
    }

    public void setAff_id(String aff_id) {
        this.aff_id = aff_id;
    }

    public String getDoigt() {
        return doigt;
    }

    public void setDoigt(String doigt) {
        this.doigt = doigt;
    }

    public byte[] getEmpreinte() {
        return empreinte;
    }

    public void setEmpreinte(byte[] empreinte) {
        this.empreinte = empreinte;
    }

    public int getQualite() {
        return qualite;
    }

    public void setQualite(int qualite) {
        this.qualite = qualite;
    }

    public String getCode_agent() {
        return code_agent;
    }

    public void setCode_agent(String code_agent) {
        this.code_agent = code_agent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ViewCSV(String aff_id, String doigt, byte[] empreinte, int qualite, String code_agent, String date) {
        this.aff_id = aff_id;
        this.doigt = doigt;
        this.empreinte = empreinte;
        this.qualite = qualite;
        this.code_agent = code_agent;
        this.date = date;
    }

    public ViewCSV(String aff_id, String code_agent, String date, String resultat) {
        this.aff_id = aff_id;
        this.code_agent = code_agent;
        this.date = date;
        this.resultat = resultat;
    }
}
