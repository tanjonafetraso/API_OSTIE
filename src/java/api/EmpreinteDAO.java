/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import database.MysqlConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Empreinte;
import servicesImpl.EmpreinteDAOImpl;
import static servicesImpl.EmpreinteDAOImpl.FIND_IMAGE_BY_PERSONNE_ID_DOIGT;
import static servicesImpl.EmpreinteDAOImpl.UPDATE_ADD_EMPREINTE;

/**
 *
 * @author EQIMA
 */
public class EmpreinteDAO extends EmpreinteDAOImpl {

    private static EmpreinteDAO instance;

    /**
     * Get l'instanciation de la classe
     *
     * @return
     */
    public static EmpreinteDAO getInstance() {
        if (instance == null) {
            instance = new EmpreinteDAO();
        }
        return instance;
    }

    public String updateAddImage(String code_agent, String doigt, byte[] FingerTemplate, int quality) {
        String resultat = "";
        MysqlConnection mysql = new MysqlConnection();
        try {
            PreparedStatement statement = mysql.getInstance().prepareStatement(UPDATE_ADD_EMPREINTE);
            statement.setBytes(1, FingerTemplate);
            statement.setInt(2, quality);
            statement.setString(3, code_agent);
            statement.setString(4, doigt);

            statement.executeUpdate();
            resultat = "Succes";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
            resultat = "erreur";
        }

        return resultat;
    }

    /**
     * Inserer l'empreinte digitale dans la BDD
     *
     * @param obj
     * @return
     * @throws IOException
     * @throws Exception
     */
    public Empreinte create(Empreinte obj) throws IOException, Exception {
        MysqlConnection mysql = new MysqlConnection();
        try {
            PreparedStatement statement = mysql.getInstance().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, obj.getDoigt());
            statement.setBytes(2, obj.getDataEmpreinte());
            statement.setInt(3, obj.getCollaborateur_id());
            statement.setInt(4, obj.getQuality());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                obj.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    /**
     * Get Empreintes dans la DBB where id=?
     *
     * @param personne_id
     * @return
     */
    public List<Empreinte> getImageEmpreinteBDD(String personne_id) {

        List<Empreinte> image = new ArrayList<>();
        MysqlConnection mysql = new MysqlConnection();
        try {
            PreparedStatement statement = mysql.getInstance().prepareStatement(FIND_IMAGE_BY_PERSONNE_ID_DOIGT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, personne_id);
            ResultSet rs = statement.executeQuery();
            int i = 1;

            while (rs.next()) {

                image.add(new Empreinte(rs.getBytes(2), rs.getInt(1)));
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return image;
    }

    public String getDoigtEnregistrer(String personne_id) {
        String doigt = "";
        MysqlConnection mysql = new MysqlConnection();
        try {
            PreparedStatement statement = mysql.getInstance().prepareStatement(GET_DOIGT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, personne_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                doigt += rs.getString(1) + ",";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return doigt;
    }

    /**
     * getsize in BDD
     *
     * @return
     */
    public int getSize() {
        int res = 0;
        MysqlConnection mysql = new MysqlConnection();
        try {
            PreparedStatement statement = mysql.getInstance().prepareStatement(SIZE);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}
