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
public class Inscription {

    private String codeAgent;
    private String affId;
    private String date;
    private int idEmpreinte;

    public Inscription(String codeAgent, String affId, int idEmpreinte) {
        this.codeAgent = codeAgent;
        this.affId = affId;
        this.date = date;
        this.idEmpreinte = idEmpreinte;
    }

    public Inscription(String codeAgent, String affId, String date) {
        this.codeAgent = codeAgent;
        this.affId = affId;
        this.date = date;
    }

    public int getIdEmpreinte() {
        return idEmpreinte;
    }

    public void setIdEmpreinte(int idEmpreinte) {
        this.idEmpreinte = idEmpreinte;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Inscription(String codeAgent, String affId) {
        this.codeAgent = codeAgent;
        this.affId = affId;
    }

    public String getCodeAgent() {
        return codeAgent;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getAffId() {
        return affId;
    }

    public void setAffId(String affId) {
        this.affId = affId;
    }
    
}
