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
public class Aff_IdDAOImpl {

    public static final String TABLE_NAME = "user_aff_id";
    public static final String COL_ID = "id";
    public static final String COL_AFF_ID = "aff_id";

    public static final String INSERT = "INSERT INTO " + TABLE_NAME + "(" + COL_AFF_ID + ") VALUES(?)";
    public static final String FIND = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_AFF_ID + "=?";
    public static final String SIZE = "select count(*) from user_aff_id";
}
