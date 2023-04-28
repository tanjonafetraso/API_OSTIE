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
public class InscriptionDAOImpl {

    public static final String TABLE_NAME = "Inscription";
    public static final String COL_CODE_AGENT = "Code_agent";
    public static final String COL_AFF_ID = "aff_id";
    public static final String COL_ID_EMPREINTE = "id_empreinte";
   
    public static final String INSERT = "INSERT INTO " + TABLE_NAME + "(" + COL_CODE_AGENT + "," + COL_AFF_ID + ","+COL_ID_EMPREINTE+") VALUES(?,?,?)";
    public static final String SELECT = "SELECT * FROM view_csv WHERE  date(DATE)>=? AND date(DATE)<=?" ;
    public static final String SELECT1 = "SELECT * FROM view_csv WHERE  date(DATE)=?" ;

}
