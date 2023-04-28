/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.File;
import java.sql.Blob;

/**
 *
 * @author EQIMA
 */
public class Empreinte {

    private int id;
    private String doigt;
    private int collaborateur_id;
    private byte[] dataEmpreinte;
    private int quality;

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public static String getClasseName() {

        return "Empreint";

    }

    public Empreinte() {
    }

    public Empreinte(String doigt, int collaborateur_id) {
        this.doigt = doigt;
        this.collaborateur_id = collaborateur_id;
    }

    public Empreinte(String doigt, int collaborateur_id, byte[] fingerPrintTemplate, int quality) {
        this.doigt = doigt;
        this.collaborateur_id = collaborateur_id;
        this.dataEmpreinte = fingerPrintTemplate;
        this.quality = quality;
    }

    public Empreinte(int id, String doigt, int collaborateur_id) {
        this.id = id;
        this.doigt = doigt;
        this.collaborateur_id = collaborateur_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getDataEmpreinte() {
        return dataEmpreinte;
    }

    public void setDataEmpreinte(byte[] dataEmpreinte) {
        this.dataEmpreinte = dataEmpreinte;
    }

    public String getDoigt() {
        return doigt;
    }

    public void setDoigt(String doigt) {
        this.doigt = doigt;
    }

    public int getCollaborateur_id() {
        return collaborateur_id;
    }

    public void setCollaborateur_id(int collaborateur_id) {
        this.collaborateur_id = collaborateur_id;
    }

    public Empreinte(byte[] dataEmpreinte, int quality) {
        this.dataEmpreinte = dataEmpreinte;
        this.quality = quality;
    }

}
