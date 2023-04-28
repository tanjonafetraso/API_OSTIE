/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import database.MysqlConnection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import models.Identification;
import models.Inscription;
import models.ViewCSV;
import servicesImpl.IdentificationDAOImpl;
import static servicesImpl.InscriptionDAOImpl.SELECT;
import static servicesImpl.InscriptionDAOImpl.SELECT1;

/**
 *
 * @author eqima
 */
public class IdentificationDAO extends IdentificationDAOImpl {

    public void create(Identification obj) throws IOException, Exception {
        MysqlConnection mysql = new MysqlConnection();
        try {
            PreparedStatement statement = mysql.getInstance().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, obj.getCodeAgent());
            statement.setString(2,obj.getAffId());
            statement.setString(3, obj.getResultat());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
 public ArrayList<ViewCSV> getAllIdentification(String date1, String date2) {
        MysqlConnection mysql = new MysqlConnection();
        PreparedStatement statement = null;
        ArrayList<ViewCSV> ListeInscription = new ArrayList<>();
        try {
            if (date2.equals("")) {
                statement = mysql.getInstance().prepareStatement(SELECTID1, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, date1);
            } else {
                statement = mysql.getInstance().prepareStatement(SELECTID, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, date1);
                statement.setString(2, date2);
            }

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                ListeInscription.add(new ViewCSV(rs.getString(4), rs.getString(3), rs.getString(2), rs.getString(5)));
              

            }
            return ListeInscription;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public void create(Inscription inscrit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
