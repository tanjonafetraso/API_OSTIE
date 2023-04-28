/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesImpl;

/*
Requête SQL pour l'empreinte Digitale
 */
public abstract class EmpreinteDAOImpl {

    public static final String TABLE_NAME = "empreintes";
    public static final String COL_ID = "id_empreinte";
    public static final String COL_DOIGT = "doigt";
    public static final String COL_IMAGE = "empreinte";
    public static final String COL_QUALITY = "quality";
    public static final String COL_IMAGE_MINITIE = "empreinte_minutie";
    public static final String COL_AFF_ID = "aff_id";
    public static final String SIZE = "select count(*) from empreintes";
    public static final String INSERT = "INSERT INTO " + TABLE_NAME + "(" + COL_DOIGT + ", " + COL_IMAGE + ", " + COL_AFF_ID + ","+COL_QUALITY+") VALUES(?,?,?,?)";
    public static final String FIND_IMAGE_BY_PERSONNE_ID_DOIGT = "SELECT "+COL_QUALITY+"," + COL_IMAGE + " as blobImg FROM " + TABLE_NAME + " INNER JOIN user_aff_id ON empreintes.aff_id=user_aff_id.id  WHERE user_aff_id.aff_id=?";
    public static final String GET_DOIGT = "select distinct doigt from empreintes inner join user_aff_id  on user_aff_id.id=empreintes.aff_id where user_aff_id.aff_id=?";
    public static final String UPDATE_ADD_EMPREINTE = "UPDATE " + TABLE_NAME + " SET " + COL_IMAGE + "=? , "+COL_QUALITY+"=? WHERE aff_id=? and doigt=?";

}
