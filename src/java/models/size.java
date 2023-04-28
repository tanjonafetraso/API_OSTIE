/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

public class size {

        int sizeUtilisateur;
        int sizeEmpreintes;

    public int getSizeUtilisateur() {
        return sizeUtilisateur;
    }

    public void setSizeUtilisateur(int sizeUtilisateur) {
        this.sizeUtilisateur = sizeUtilisateur;
    }

    public int getSizeEmpreintes() {
        return sizeEmpreintes;
    }

    public void setSizeEmpreintes(int sizeEmpreintes) {
        this.sizeEmpreintes = sizeEmpreintes;
    }

        public size(int sizeUtilisateur, int sizeEmpreintes) {
            this.sizeUtilisateur = sizeUtilisateur;
            this.sizeEmpreintes = sizeEmpreintes;
        }
    }