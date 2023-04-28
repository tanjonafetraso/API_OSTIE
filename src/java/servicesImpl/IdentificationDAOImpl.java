/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesImpl;

/**
 *
 * @author eqima
 */
public class IdentificationDAOImpl {

    public static final String TABLE_NAME = "identification";
    public static final String COL_CODE_AGENT = "Code_agent";
    public static final String COL_AFF_ID = "aff_id";
    public static final String COL_RESULTAT = "resultat";

    public static final String INSERT = "INSERT INTO " + TABLE_NAME + "(" + COL_CODE_AGENT + "," + COL_AFF_ID + "," + COL_RESULTAT + ") VALUES(?,?,?)";
    public static final String SELECTID = "SELECT * FROM identification WHERE  date(DATE)>=? AND date(DATE)<=?";
    public static final String SELECTID1 = "SELECT * FROM identification WHERE  date(DATE)=?";
}
