package lk.ijse.aquarium.model;

import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.Dilivary;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DilivaryModel {
    public static boolean saveDilivary(Dilivary dilivary) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Dilivary VALUES (?, ?, ? )";
        return CrudUtil.execute(sql, dilivary.getId(), dilivary.getTime(), dilivary.getAddress());


    }


    public static boolean updateDilivary(Dilivary dilivary,String id) throws SQLException, ClassNotFoundException {
        String sql = " UPDATE dilivary SET time = ? , address = ? WHERE Did = ? " ;
        return CrudUtil.execute(sql, dilivary.getTime(), dilivary.getAddress(),id);
    }

    public static Dilivary searchDilivary(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT  * FROM dilivary WHERE Did = ?";
        ResultSet result = CrudUtil.execute(sql, id);

        if(result.next()) {
            return new Dilivary(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3)

            );
        }
        return null;
    }

    public static boolean DeleteDilivary(Dilivary dilivary,String id) throws SQLException, ClassNotFoundException {
        String sql = " DELETE FROM dilivary  WHERE Did = ? " ;
        return CrudUtil.execute(sql,id);
    }
    public static String getCurrentItemID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM dilivary ORDER BY CAST(SUBSTRING(Did, 2) AS UNSIGNED) DESC LIMIT 1");
        while (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public static ArrayList<Dilivary> getDilivaryData() throws SQLException, ClassNotFoundException {
        ArrayList<Dilivary> dilivaryData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("select * from dilivary");
        while (resultSet.next()){

            dilivaryData.add(new Dilivary(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)));

        }
        return dilivaryData;
    }

}
